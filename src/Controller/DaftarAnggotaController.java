package Controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
import Main.DaftarAnggota;
import Model.Export;
import Model.Mahasiswa;
import Model.MahasiswaDAO;
import java.io.File;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class DaftarAnggotaController {
    private DaftarAnggota view;
    private MahasiswaDAO mahasiswaDAO;

    public DaftarAnggotaController(DaftarAnggota view) throws SQLException {
        this.view = view;
        mahasiswaDAO = new MahasiswaDAO();
    }

    public void loadTableData() {
        new Thread(() -> {
            List<Mahasiswa> listMahasiswa = mahasiswaDAO.getAllMahasiswa();
            SwingUtilities.invokeLater(() -> view.updateTable(listMahasiswa));
        }).start();
    }

    public void searchMahasiswa() {
        String query = view.getSearchQuery();
        if (query.isEmpty()|| query.equals("Ketik NIM untuk pencarian")) {
            loadTableData();
        } else {
            new Thread(() -> {
                try {
                    List<Mahasiswa> searchResult = mahasiswaDAO.getMahasiswaByQuery(query);
                    SwingUtilities.invokeLater(() -> view.updateTable(searchResult));
                } catch (SQLException ex) {
                    Logger.getLogger(DaftarAnggota.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
        }
    }

    public void saveMahasiswa() {
        String nim = view.getNim();
        String nama = view.getNama();
        String jk = view.getJk();
        String kelas = view.getKelas();

        // Validasi data
        if (nim.isEmpty() || nama.isEmpty() || kelas.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Mohon lengkapi semua kolom!");
            return;
        }
        
        //validasi nim harus 9 digit 
            if (nim == null || !nim.matches("\\d{9}")) {
                JOptionPane.showMessageDialog(view, "NIM harus terdiri dari 9 digit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
        
            // Validasi Nama
            if (nama == null || !nama.matches("^[a-zA-Z\\s]+$")) {
                JOptionPane.showMessageDialog(view, "Nama tidak boleh mengandung angka!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
        
        // Validasi ketersediaan NIM
        try {
        if (mahasiswaDAO.cekNIM(nim)) {
            JOptionPane.showMessageDialog(view, "NIM sudah terdaftar!");
            return;
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(view, "Terjadi kesalahan saat memeriksa NIM: " + ex.getMessage());
        return;
    }


        // Membuat objek Mahasiswa
        Mahasiswa mahasiswa = new Mahasiswa(nim, nama, jk, kelas);

        // Simpan data ke dalam database menggunakan mahasiswaDao
        boolean berhasilDisimpan = mahasiswaDAO.simpanMahasiswa(mahasiswa);

        if (berhasilDisimpan) {
            JOptionPane.showMessageDialog(view, "Data berhasil disimpan!");
            // Reset form atau lakukan tindakan lain setelah penyimpanan berhasil
            view.resetForm();
            view.getMainPanel().removeAll();
            view.getMainPanel().add(view.getDaftarAnggota());
            view.getMainPanel().repaint();
            view.getMainPanel().revalidate();
            loadTableData();
        } else {
            JOptionPane.showMessageDialog(view, "Gagal menyimpan data!");
        }
    }

    public void exportToCSV() {
        JFileChooser exportFileChooser = new JFileChooser();
        exportFileChooser.setDialogTitle("Specify a file to save");
        int userSelection = exportFileChooser.showSaveDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = exportFileChooser.getSelectedFile();
            // Tambahkan ekstensi .csv jika pengguna tidak menambahkannya
            if (!fileToSave.getAbsolutePath().endsWith(".csv")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
            }
            Export.exportTableToCSV(view.getAnggotaTable(), fileToSave.getAbsolutePath());
        }
    }

    public void exportToPDF() {
        JFileChooser exportFileChooser = new JFileChooser();
        exportFileChooser.setDialogTitle("Specify a file to save");
        int userSelection = exportFileChooser.showSaveDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = exportFileChooser.getSelectedFile();
            // Tambahkan ekstensi .pdf jika pengguna tidak menambahkannya
            if (!fileToSave.getAbsolutePath().endsWith(".pdf")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
            }
            Export export = new Export();
            export.exportTableToPDF(view.getAnggotaTable(), fileToSave.getAbsolutePath());
        }
    }
}
