package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Controller.DatabaseConnection;

public class BukuDAO {
    
    private Connection connection;

    public BukuDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public List<Buku> getAllBuku() {
        List<Buku> bukuList = new ArrayList<>();
        String sql = "SELECT judul_buku, pengarang, penerbit, tahun_terbit, stok FROM buku";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Buku buku = new Buku(
                        rs.getString("judul_buku"),
                        rs.getString("pengarang"),
                        rs.getString("penerbit"),
                        rs.getString("tahun_terbit"), // SQLite stores dates as strings by default
                        rs.getInt("stok")
                );
                bukuList.add(buku);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bukuList;
    }

    public List<Buku> getAllBukuUser() {
        List<Buku> bukuList = new ArrayList<>();
        String sql = "SELECT judul_buku, pengarang, penerbit, tahun_terbit FROM buku";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Buku buku = new Buku(
                        rs.getString("judul_buku"),
                        rs.getString("pengarang"),
                        rs.getString("penerbit"),
                        rs.getString("tahun_terbit") // SQLite stores dates as strings by default
                );
                bukuList.add(buku);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bukuList;
    }

    public boolean simpanBuku(Buku buku) {
        String sql = "INSERT INTO buku (judul_buku, pengarang, penerbit, tahun_terbit, stok) VALUES (?, ?, ?, ?, ?)";
        boolean berhasilDisimpan = false;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, buku.getJudulBuku());
            ps.setString(2, buku.getPengarang());
            ps.setString(3, buku.getPenerbit());
            ps.setString(4, buku.getTahunTerbit()); // SQLite stores dates as strings by default
            ps.setInt(5, buku.getStok());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                berhasilDisimpan = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return berhasilDisimpan;
    }

    public List<String> getJudulBukuList() {
        List<String> judulBukuList = new ArrayList<>();
        String sql = "SELECT judul_buku FROM buku";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                judulBukuList.add(rs.getString("judul_buku"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return judulBukuList;
    }

    public Buku getBukuByJudul(String judul_buku) {
        String sql = "SELECT * FROM buku WHERE judul_buku = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, judul_buku);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Buku(
                            rs.getString("judul_buku"),
                            rs.getString("pengarang"),
                            rs.getString("penerbit"),
                            rs.getString("tahun_terbit"), // SQLite stores dates as strings by default
                            rs.getInt("stok")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getIdBukuByJudul(String judulBuku) throws SQLException {
        String sql = "SELECT id_buku FROM buku WHERE judul_buku = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, judulBuku);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_buku");
                }
            }
        }
        throw new SQLException("Buku dengan judul '" + judulBuku + "' tidak ditemukan.");
    }

    public Buku getBukuById(int idBuku) throws SQLException {
        Buku buku = null;
        String sql = "SELECT judul_buku, pengarang, penerbit, tahun_terbit, stok FROM buku WHERE id_buku = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idBuku);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    buku = new Buku(
                            rs.getString("judul_buku"),
                            rs.getString("pengarang"),
                            rs.getString("penerbit"),
                            rs.getString("tahun_terbit"), // SQLite stores dates as strings by default
                            rs.getInt("stok")
                    );
                }
            }
        }
        return buku;
    }

    public void updateBuku(int idBuku, String judulBuku, String pengarang, String penerbit, String tahunTerbit, int stok) throws SQLException {
        String sql = "UPDATE buku SET judul_buku = ?, pengarang = ?, penerbit = ?, tahun_terbit = ?, stok = ? WHERE id_buku = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, judulBuku);
            ps.setString(2, pengarang);
            ps.setString(3, penerbit);
            ps.setString(4, tahunTerbit); // SQLite stores dates as strings by default
            ps.setInt(5, stok);
            ps.setInt(6, idBuku);
            ps.executeUpdate();
        }
        System.out.println("berhasil edit");
    }

    public void hapusBuku(int idBuku) throws SQLException {
        String sql = "DELETE FROM buku WHERE id_buku = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idBuku);
            ps.executeUpdate();
        }
    }

    public List<Buku> searchBuku(String query) throws SQLException {
        List<Buku> searchResult = new ArrayList<>();
        String sql = "SELECT * FROM buku WHERE judul_buku LIKE ? OR pengarang LIKE ? OR penerbit LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + query + "%");
            statement.setString(2, "%" + query + "%");
            statement.setString(3, "%" + query + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Buku buku = new Buku(
                            resultSet.getString("judul_buku"),
                            resultSet.getString("pengarang"),
                            resultSet.getString("penerbit"),
                            resultSet.getString("tahun_terbit"), // SQLite stores dates as strings by default
                            resultSet.getInt("stok")
                    );
                    searchResult.add(buku);
                }
            }
        }
        return searchResult;
    }
    
    public void updateStokBuku(int idBuku, int stokBaru) throws SQLException {
        String sql = "UPDATE buku SET stok = ? WHERE id_buku = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, stokBaru);
            ps.setInt(2, idBuku);

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Stok buku berhasil diperbarui.");
            } else {
                System.out.println("Gagal memperbarui stok buku.");
            }
        }
    }

}
