package Model;

/**
 *
 * @author LENOVO
 */
public class Peminjaman {
    private int idPinjam;
    private int idBuku;
    private int idUser;
    private String tglPinjam; // Ubah tipe data menjadi String
    private String tglKembali; // Ubah tipe data menjadi String
    private String statusPinjam;

    // Constructor
    public Peminjaman(int idPinjam, int idBuku, int idUser, String tglPinjam, String tglKembali, String statusPinjam) {
        this.idPinjam = idPinjam;
        this.idBuku = idBuku;
        this.idUser = idUser;
        this.tglPinjam = tglPinjam;
        this.tglKembali = tglKembali;
        this.statusPinjam = statusPinjam;
    }

    public Peminjaman(int idBuku, int idUser, String tglPinjam, String tglKembali) {
        this.idBuku = idBuku;
        this.idUser = idUser;
        this.tglPinjam = tglPinjam;
        this.tglKembali = tglKembali;
    }

    // Getters and Setters
    public int getIdPinjam() {
        return idPinjam;
    }

    public void setIdPinjam(int idPinjam) {
        this.idPinjam = idPinjam;
    }

    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTglPinjam() {
        return tglPinjam;
    }

    public void setTglPinjam(String tglPinjam) {
        this.tglPinjam = tglPinjam;
    }

    public String getTglKembali() {
        return tglKembali;
    }

    public void setTglKembali(String tglKembali) {
        this.tglKembali = tglKembali;
    }

    public String getStatusPinjam() {
        return statusPinjam;
    }

    public void setStatusPinjam(String statusPinjam) {
        this.statusPinjam = statusPinjam;
    }
}
