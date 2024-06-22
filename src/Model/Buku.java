/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Buku {
    private int idBuku;
    private String judulBuku;
    private String pengarang;
    private String penerbit;
    private String tahunTerbit; // Tipe data diubah menjadi String
    private int stok;

    // Constructor dengan stok
    public Buku(String judulBuku, String pengarang, String penerbit, String tahunTerbit, int stok) {
       
        this.judulBuku = judulBuku;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
        this.stok = stok;
    
    }
    
    // Constructor tanpa stok
    public Buku(String judulBuku, String pengarang, String penerbit, String tahunTerbit) {
       
        this.judulBuku = judulBuku;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
    }

    // Constructor untuk idBuku, judulBuku, pengarang, dan tahunTerbit
    public Buku(int idBuku, String judulBuku, String pengarang, String tahunTerbit) {
        this.idBuku = idBuku;
        this.judulBuku = judulBuku;
        this.pengarang = pengarang;
        this.tahunTerbit = tahunTerbit;
    }
    

    // Getters and Setters untuk semua atribut
    public int getIdBuku() { return idBuku; }
    public void setIdBuku(int idBuku) { this.idBuku = idBuku; }

    public String getJudulBuku() { return judulBuku; }
    public void setJudulBuku(String judulBuku) { this.judulBuku = judulBuku; }

    public String getPengarang() { return pengarang; }
    public void setPengarang(String pengarang) { this.pengarang = pengarang; }

    public String getPenerbit() { return penerbit; }
    public void setPenerbit(String penerbit) { this.penerbit = penerbit; }

    public String getTahunTerbit() { return tahunTerbit; }
    public void setTahunTerbit(String tahunTerbit) { this.tahunTerbit = tahunTerbit; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
}
