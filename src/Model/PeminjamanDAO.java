package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Controller.DatabaseConnection;

public class PeminjamanDAO {
    private Connection connection;

    public PeminjamanDAO() throws SQLException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public boolean simpanPeminjaman(int idUser, int idBuku, String tanggalPinjam, String tanggalKembali) {
        String sql = "INSERT INTO peminjaman (id_user, id_buku, tglPinjam, tglKembali, status_pinjam) VALUES (?, ?, ?, ?, 'diproses')";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            ps.setInt(2, idBuku);
            ps.setString(3, tanggalPinjam);
            ps.setString(4, tanggalKembali);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Peminjaman> getAllPeminjaman() throws SQLException {
        List<Peminjaman> listPeminjaman = new ArrayList<>();
        String sql = "SELECT * FROM peminjaman " +
                     "ORDER BY " +
                     "CASE status_pinjam " +
                     "    WHEN 'diproses' THEN 1 " +
                     "    WHEN 'disetujui' THEN 2 " +
                     "    WHEN 'terlambat' THEN 3 " +
                     "    WHEN 'ditolak' THEN 4 " +
                     "    WHEN 'selesai' THEN 5 " +
                     "    ELSE 6 " +
                     "END";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idPeminjaman = rs.getInt("id_pinjam");
                int idBuku = rs.getInt("id_buku");
                int idUser = rs.getInt("id_user");
                String tanggalPinjam = rs.getString("tglPinjam");
                String tanggalKembali = rs.getString("tglKembali");
                String statusPinjam = rs.getString("status_pinjam");

                Peminjaman peminjaman = new Peminjaman(idPeminjaman, idBuku, idUser, tanggalPinjam, tanggalKembali, statusPinjam);
                listPeminjaman.add(peminjaman);
            }
        }

        return listPeminjaman;
    }

    public List<Peminjaman> getPeminjamanByIdUser(int idUser) throws SQLException {
        List<Peminjaman> listRiwayat = new ArrayList<>();
        String sql = "SELECT * FROM peminjaman WHERE id_user = ?" +"ORDER BY " +
                     "CASE status_pinjam " +
                     "    WHEN 'diproses' THEN 1 " +
                     "    WHEN 'disetujui' THEN 2 " +
                     "    WHEN 'terlambat' THEN 3 " +
                     "    WHEN 'ditolak' THEN 4 " +
                     "    WHEN 'selesai' THEN 5 " +
                     "    ELSE 6 " +
                     "END";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idPinjam = rs.getInt("id_pinjam");
                    int idBuku = rs.getInt("id_buku");
                    String tanggalPinjam = rs.getString("tglPinjam");
                    String tanggalKembali = rs.getString("tglKembali");
                    String statusPinjam = rs.getString("status_pinjam");

                    Peminjaman peminjaman = new Peminjaman(idPinjam, idBuku, idUser, tanggalPinjam, tanggalKembali, statusPinjam);
                    listRiwayat.add(peminjaman);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return listRiwayat;
    }
    
   public void updateStatusPeminjaman(int idUser, int idBuku, String tglPinjam, String tglKembali, String statusBaru) throws SQLException {
    String sql = "UPDATE peminjaman SET status_pinjam = ? WHERE id_user = ? AND id_buku = ? AND tglPinjam = ? AND tglKembali = ?";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, statusBaru);
        ps.setInt(2, idUser);
        ps.setInt(3, idBuku);
        ps.setString(4, tglPinjam);
        ps.setString(5, tglKembali);

        int rowsUpdated = ps.executeUpdate();

        if (rowsUpdated > 0) {
            System.out.println("Status peminjaman berhasil diperbarui.");
        } else {
            System.out.println("Gagal memperbarui status peminjaman.");
        }
    }
}


    public List<Peminjaman> getPeminjamanByNim(String keyword) throws SQLException {
        List<Peminjaman> listPeminjaman = new ArrayList<>();
        String sql = "SELECT * FROM peminjaman WHERE id_user IN (SELECT id_user FROM Mahasiswa WHERE nim LIKE ?) " +
                     "ORDER BY " +
                     "CASE status_pinjam " +
                     "    WHEN 'diproses' THEN 1 " +
                     "    WHEN 'disetujui' THEN 2 " +
                     "    WHEN 'terlambat' THEN 3 " +
                     "    WHEN 'ditolak' THEN 4 " +
                     "    WHEN 'selesai' THEN 5 " +
                     "    ELSE 6 " +
                     "END";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idPeminjaman = rs.getInt("id_pinjam");
                    int idBuku = rs.getInt("id_buku");
                    int idUser = rs.getInt("id_user");
                    String tanggalPinjam = rs.getString("tglPinjam");
                    String tanggalKembali = rs.getString("tglKembali");
                    String statusPinjam = rs.getString("status_pinjam");

                    Peminjaman peminjaman = new Peminjaman(idPeminjaman, idBuku, idUser, tanggalPinjam, tanggalKembali, statusPinjam);
                    listPeminjaman.add(peminjaman);
                }
            }
        }

        return listPeminjaman;
    }

    public boolean cancelPeminjaman(int idUser, int idBuku, String tglPinjam, String tglKembali) throws SQLException {
        String sql = "DELETE FROM peminjaman WHERE id_user = ? AND id_buku = ? AND tglPinjam = ? AND tglKembali = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            ps.setInt(2, idBuku);
            ps.setString(3, tglPinjam);
            ps.setString(4, tglKembali);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
