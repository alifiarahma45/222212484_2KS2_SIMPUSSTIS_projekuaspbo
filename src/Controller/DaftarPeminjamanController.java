package Controller;

import Model.Buku;
import Model.BukuDAO;
import Model.MahasiswaDAO;
import Model.PeminjamanDAO;
import Main.DaftarPeminjaman;
import Model.Export;
import Model.Peminjaman;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DaftarPeminjamanController {
    private DaftarPeminjaman view;
    private PeminjamanDAO peminjamanDAO;
    private MahasiswaDAO mahasiswaDAO;
    private BukuDAO bukuDAO;

    public DaftarPeminjamanController(DaftarPeminjaman view) throws SQLException {
        this.view = view;
        peminjamanDAO = new PeminjamanDAO();
        bukuDAO = new BukuDAO();
        mahasiswaDAO = new MahasiswaDAO();
        loadTableData();
        addDocumentListenerToSearchField();
    }

    public MahasiswaDAO getMahasiswaDAO() {
        return mahasiswaDAO;
    }

    public BukuDAO getBukuDAO() {
        return bukuDAO;
    }

    private void addDocumentListenerToSearchField() {
        view.getSearchTextField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchPeminjaman();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchPeminjaman();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchPeminjaman();
            }

            private void searchPeminjaman() {
                String query = view.getSearchTextField().getText().trim();
                if (query.isEmpty() || query.equals("Ketik NIM untuk pencarian")) {
                    loadTableData();
                } else {
                    new Thread(() -> {
                        try {
                            List<Peminjaman> searchResult = peminjamanDAO.getPeminjamanByNim(query);
                            SwingUtilities.invokeLater(() -> view.updateTable(searchResult));
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                }
            }
        });
    }

    private void loadTableData() {
        new Thread(() -> {
            try {
                List<Peminjaman> listPeminjaman = peminjamanDAO.getAllPeminjaman();
                SwingUtilities.invokeLater(() -> view.updateTable(listPeminjaman));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }).start();
    }
    
    public void updateButtonAction() {
        //menyimpan variabel ke metode update selanjutnya
    int selectedRow = view.getPeminjamanTable().getSelectedRow();

    if (selectedRow != -1) {
        try {
            String nim = view.getPeminjamanTable().getValueAt(selectedRow, 0).toString();
            String judulBuku = view.getPeminjamanTable().getValueAt(selectedRow, 1).toString();
            int stok = Integer.parseInt(view.getPeminjamanTable().getValueAt(selectedRow, 2).toString());
            String tglPinjam = view.getPeminjamanTable().getValueAt(selectedRow, 3).toString();
            String tglKembali = view.getPeminjamanTable().getValueAt(selectedRow, 4).toString();
            String statusPinjam = view.getPeminjamanTable().getValueAt(selectedRow, 5).toString();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            view.getNimTextField().setText(nim);
            view.getJudulTextField().setText(judulBuku);
            view.getStokTextField().setText(String.valueOf(stok));
            view.getTglPinjamDateChooser().setDate(dateFormat.parse(tglPinjam));
            view.getTglKembaliDateChooser().setDate(dateFormat.parse(tglKembali));
            view.getStatusComboBox().setSelectedItem(statusPinjam);

            view.showUpdatePeminjamanPanel();
        } catch (ParseException | NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Format tanggal atau data stok tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Terjadi kesalahan: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(view, "Pilih peminjaman terlebih dahulu", "Peringatan", JOptionPane.WARNING_MESSAGE);
    }
}


    public void simpanButtonAction() {
    try {
        String judulBuku = view.getJudulTextField().getText().trim();
        String nim = view.getNimTextField().getText().trim();
        String statusBaru = (String) view.getStatusComboBox().getSelectedItem();

        // Ambil tanggal pinjam dan tanggal kembali dari komponen date chooser
        Date datePinjam = view.getTglPinjamDateChooser().getDate();
        Date dateKembali = view.getTglKembaliDateChooser().getDate();

        // Buat objek SimpleDateFormat dengan format yang diinginkan untuk mengonversi ke string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Konversi tanggal pinjam dan tanggal kembali ke dalam format string
        String tglPinjam = dateFormat.format(datePinjam);
        String tglKembali = dateFormat.format(dateKembali);

        // Ambil stok dari input text field
        int stok = Integer.parseInt(view.getStokTextField().getText().trim());

        int idBuku = bukuDAO.getIdBukuByJudul(judulBuku);
        int idUser = mahasiswaDAO.getIdUserByNim(nim);

        // Cek status
        if (statusBaru.equals("disetujui")) {
            // Jika status disetujui, periksa stok
            if (stok <= 0) {
                JOptionPane.showMessageDialog(view, "Stok buku tidak mencukupi untuk status ini.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return; // Hentikan proses jika stok tidak mencukupi
            } else {
                // Update status peminjaman dengan tanggal pinjam, tanggal kembali, dan status baru
                peminjamanDAO.updateStatusPeminjaman(idUser, idBuku, tglPinjam, tglKembali, statusBaru);

                // Kurangi stok buku
                bukuDAO.updateStokBuku(idBuku, stok - 1);
            }
        } else if (statusBaru.equals("selesai")) {
            // Jika status selesai, tambahkan stok buku
            // Update status peminjaman dengan tanggal pinjam, tanggal kembali, dan status baru
            peminjamanDAO.updateStatusPeminjaman(idUser, idBuku, tglPinjam, tglKembali, statusBaru);

            // Tambahkan stok buku
            bukuDAO.updateStokBuku(idBuku, stok + 1);
        } else {
            // Jika status lain, hanya update status peminjaman tanpa mengubah stok
            peminjamanDAO.updateStatusPeminjaman(idUser, idBuku, tglPinjam, tglKembali, statusBaru);
        }

        loadTableData(); // Memuat ulang data tabel setelah pembaruan
        view.showDaftarPeminjamanPanel(); // Kembali ke panel daftar peminjaman
    } catch (SQLException ex) {
        Logger.getLogger(DaftarPeminjaman.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(view, "Gagal memperbarui status peminjaman");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(view, "Format stok tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    public void clearForm() {
        view.getJudulTextField().setText("");
        view.getNimTextField().setText("");
        view.getTglPinjamDateChooser().setDate(null);
        view.getTglKembaliDateChooser().setDate(null);
        view.getStokTextField().setText("");
        view.getStatusComboBox().setSelectedIndex(0);
    }

    public void btnBatalAction() {
        clearForm();
        view.showDaftarPeminjamanPanel();
    }

    public void exportToCSV() {
        view.getExportFileChooser().setDialogTitle("Specify a file to save");
        int userSelection = view.getExportFileChooser().showSaveDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = view.getExportFileChooser().getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".csv")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
            }
            Export.exportTableToCSV(view.getPeminjamanTable(), fileToSave.getAbsolutePath());
        }
    }

    public void exportToPDF() {
        view.getExportFileChooser().setDialogTitle("Specify a file to save");
        int userSelection = view.getExportFileChooser().showSaveDialog(view);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = view.getExportFileChooser().getSelectedFile();
            if (!fileToSave.getAbsolutePath().endsWith(".pdf")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
            }
            Export export = new Export();
            export.exportTableToPDF(view.getPeminjamanTable(), fileToSave.getAbsolutePath());
        }
    }
}

