package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Controller.DatabaseConnection;
import Controller.HashUtil;

public class MahasiswaDAO {

    private Connection connection;

    public MahasiswaDAO() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection(); // Inisialisasi koneksi database
    }

    public boolean simpanMahasiswa(Mahasiswa mahasiswa) {
        String sqlMahasiswa = "INSERT INTO mahasiswa (id_user, nim, nama, jk, kelas) VALUES (?, ?, ?, ?, ?)";
        String sqlUser = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
        boolean berhasilDisimpan = false;

        try {
            // Mulai transaksi
            connection.setAutoCommit(false);

            // Simpan data user (username = nim, password = hash dari nim)
            String hashedPassword = HashUtil.hashPassword(mahasiswa.getNim()); // Hash nim sebagai password
            try (PreparedStatement psUser = connection.prepareStatement(sqlUser, PreparedStatement.RETURN_GENERATED_KEYS)) {
                psUser.setString(1, mahasiswa.getNim());
                psUser.setString(2, hashedPassword);
                psUser.setString(3, "anggota"); // Atur role sesuai kebutuhan

                int rowsAffectedUser = psUser.executeUpdate();

                if (rowsAffectedUser == 1) {
                    // Dapatkan id_user yang di-generate oleh database
                    ResultSet generatedKeys = psUser.getGeneratedKeys();
                    int idUser = -1;
                    if (generatedKeys.next()) {
                        idUser = generatedKeys.getInt(1);
                    }

                    // Simpan data mahasiswa dengan menggunakan id_user yang didapatkan
                    try (PreparedStatement psMahasiswa = connection.prepareStatement(sqlMahasiswa)) {
                        psMahasiswa.setInt(1, idUser);
                        psMahasiswa.setString(2, mahasiswa.getNim());
                        psMahasiswa.setString(3, mahasiswa.getNama());
                        psMahasiswa.setString(4, mahasiswa.getJk());
                        psMahasiswa.setString(5, mahasiswa.getKelas());

                        int rowsAffectedMahasiswa = psMahasiswa.executeUpdate();

                        if (rowsAffectedMahasiswa == 1) {
                            berhasilDisimpan = true;
                        }
                    }
                }
            }

            // Commit transaksi jika berhasil disimpan
            if (berhasilDisimpan) {
                connection.commit();
            } else {
                connection.rollback(); // Rollback jika ada masalah saat menyimpan
            }

        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback jika terjadi exception
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true); // Kembalikan otomatisasi commit ke default
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return berhasilDisimpan;
    }

    public List<Mahasiswa> getAllMahasiswa() {
        List<Mahasiswa> listMahasiswa = new ArrayList<>();
        String sql = "SELECT nim, nama, jk, kelas FROM mahasiswa";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String nim = rs.getString("nim");
                String nama = rs.getString("nama");
                String jk = rs.getString("jk");
                String kelas = rs.getString("kelas");

                Mahasiswa mahasiswa = new Mahasiswa(nim, nama, jk, kelas);
                listMahasiswa.add(mahasiswa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listMahasiswa;
    }

    public int getIdUserByNim(String nim) throws SQLException {
        String sql = "SELECT id_user FROM mahasiswa WHERE nim = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nim);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_user");
                }
            }
        }
        throw new SQLException("Mahasiswa dengan NIM '" + nim + "' tidak ditemukan.");
    }

    public String getNimByIdUser(int id_user) throws SQLException {
        String sql = "SELECT nim FROM mahasiswa WHERE id_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id_user);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nim");
                }
            }
        }
        throw new SQLException("Mahasiswa dengan Id_user '" + id_user + "' tidak ditemukan.");
    }

    public Mahasiswa getMahasiswaByNim(String nim) throws SQLException {
        Mahasiswa mahasiswa = null;
        String sql = "SELECT * FROM mahasiswa WHERE nim = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nim);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nama = rs.getString("nama");
                String jk = rs.getString("jk");
                String kelas = rs.getString("kelas");

                mahasiswa = new Mahasiswa(nim, nama, jk, kelas);
            }
        }
        return mahasiswa;
    }

    public boolean updatePassword(int idUser, String newPassword) {
        String sql = "UPDATE user SET password = ? WHERE id_user = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newPassword);
            ps.setInt(2, idUser);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticate(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String storedHashedPassword = rs.getString("password");
                    String hashedPassword = HashUtil.hashPassword(password);
                    return storedHashedPassword.equals(hashedPassword);
                }
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Mahasiswa> getMahasiswaByQuery(String keyword) throws SQLException {
        List<Mahasiswa> listMahasiswa = new ArrayList<>();
        String sql = "SELECT * FROM mahasiswa WHERE nim LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nim = rs.getString("nim");
                    String nama = rs.getString("nama");
                    String jk = rs.getString("jk");
                    String kelas = rs.getString("kelas");

                    Mahasiswa mahasiswa = new Mahasiswa(nim, nama, jk, kelas);
                    listMahasiswa.add(mahasiswa);
                }
            }
        }

        return listMahasiswa;
    }

    public boolean cekNIM(String nim) throws SQLException {
        String sql = "SELECT COUNT(*) FROM mahasiswa WHERE nim = ?";
        boolean nimSudahAda = false; // Inisialisasi dengan false
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nim);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        nimSudahAda = true; // Set nilai nimSudahAda menjadi true jika NIM sudah terdaftar
                    }
                }
            }
            
        } catch (SQLException ex) {
            // Tangkap SQLException dan throw kembali untuk ditangani di luar metode ini
            throw new SQLException("Terjadi kesalahan saat memeriksa NIM: " + ex.getMessage());
        }
        
        return nimSudahAda; // Kembalikan nilai nimSudahAda
    }
}
