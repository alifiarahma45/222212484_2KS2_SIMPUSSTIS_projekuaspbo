/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author LENOVO
 */
public class Mahasiswa {
    private int idMhs;
    private int idUser;
    private String nim;
    private String nama;
    private String jk;
    private String kelas;

    // Constructor
    public Mahasiswa( String nim, String nama, String jk, String kelas) {
        this.nim = nim;
        this.nama = nama;
        this.jk = jk;
        this.kelas = kelas;
    }

    // Getters and Setters
  
    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getJk() { return jk; }
    public void setJk(String jk) { this.jk = jk; }

    public String getKelas() { return kelas; }
    public void setKelas(String kelas) { this.kelas = kelas; }
}

