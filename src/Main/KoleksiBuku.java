/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Main;

import Model.*;
import com.itextpdf.io.IOException;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;


import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author hp
 */
public class KoleksiBuku extends javax.swing.JPanel {

    /**
     * Creates new form Form_Barang
     */
    
    private DefaultTableModel tableModel;
    private BukuDAO bukuDAO;
    private MahasiswaDAO mahasiswaDAO;
    private PeminjamanDAO peminjamanDAO;

    public KoleksiBuku() throws SQLException {
        initComponents();
        bukuDAO = new BukuDAO(); // Inisialisasi BukuDAO
        mahasiswaDAO = new MahasiswaDAO();
        peminjamanDAO = new PeminjamanDAO();
        tableModel = (DefaultTableModel) bukuTable.getModel();
        loadTableData(); // Memuat data buku ke dalam tabel saat panel ini diinisialisasi
        addDocumentListenerToSearchField(); // Menambahkan DocumentListener ke searchTextField
    }
    
   private void loadTableData() {
         new Thread(() -> {
            List<Buku> listBuku = bukuDAO.getAllBukuUser();
            SwingUtilities.invokeLater(() -> updateTable(listBuku));
        }).start();
    }
    // Metode ini bisa digunakan untuk memuat ulang data tabel setelah operasi tambah, edit, atau hapus
    private void updateTable(List<Buku> listBuku) {
        tableModel.setRowCount(0); // Mengosongkan tabel sebelum dimuat ulang

        for (Buku buku : listBuku) {
            Object[] row = {
                buku.getJudulBuku(),
                buku.getPengarang(),
                buku.getPenerbit(),
                buku.getTahunTerbit()
            };
            tableModel.addRow(row);
        }
    }
    private void addDocumentListenerToSearchField() {
        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchBooks();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchBooks();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchBooks();
            }

            private void searchBooks() {
                String query = searchTextField.getText();
                if (query.isEmpty()|| query.equals("Ketik judul buku untuk pencarian")) {
                    loadTableData();
                } else {
                    new Thread(() -> {
                        try {
                            List<Buku> searchResult = bukuDAO.searchBuku(query);
                            SwingUtilities.invokeLater(() -> updateTable(searchResult));
                        } catch (SQLException ex) {
                            Logger.getLogger(DaftarBuku.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }).start();
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        exportFileChooser = new javax.swing.JFileChooser();
        mainPanel = new javax.swing.JPanel();
        koleksiBuku = new javax.swing.JPanel();
        searchTextField = new javax.swing.JTextField();
        EditButton1 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bukuTable = new javax.swing.JTable();
        pinjamButton = new javax.swing.JButton();
        exportButton = new javax.swing.JButton();
        ajukanPeminjaman = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        nimTextField = new javax.swing.JTextField();
        judulTextField = new javax.swing.JTextField();
        pinjamButton1 = new javax.swing.JButton();
        btn_batal4 = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        kembaliDateChooser = new com.toedter.calendar.JDateChooser();
        tahunDateChooser = new com.toedter.calendar.JDateChooser();
        pinjamDateChooser = new com.toedter.calendar.JDateChooser();
        pengarangTextField = new javax.swing.JTextField();

        setBackground(new java.awt.Color(51, 102, 255));
        setPreferredSize(new java.awt.Dimension(571, 486));
        setLayout(new java.awt.CardLayout());

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));
        mainPanel.setMinimumSize(new java.awt.Dimension(900, 860));
        mainPanel.setPreferredSize(new java.awt.Dimension(650, 580));
        mainPanel.setLayout(new java.awt.CardLayout());

        koleksiBuku.setBackground(new java.awt.Color(255, 255, 255));

        searchTextField.setText("Ketik judul buku untuk pencarian");
        searchTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                searchTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchTextFieldFocusLost(evt);
            }
        });

        EditButton1.setText("Reset");
        EditButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditButton1ActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel20.setText("Koleksi Buku");

        bukuTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Judul Buku", "Pengarang", "Penerbit", "Tahun Terbit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(bukuTable);

        pinjamButton.setText("Pinjam");
        pinjamButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pinjamButtonActionPerformed(evt);
            }
        });

        exportButton.setText("Export (.pdf)");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout koleksiBukuLayout = new javax.swing.GroupLayout(koleksiBuku);
        koleksiBuku.setLayout(koleksiBukuLayout);
        koleksiBukuLayout.setHorizontalGroup(
            koleksiBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, koleksiBukuLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel20)
                .addGap(670, 670, 670))
            .addGroup(koleksiBukuLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(koleksiBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(exportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(koleksiBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(koleksiBukuLayout.createSequentialGroup()
                            .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(EditButton1)
                            .addGap(167, 167, 167)
                            .addComponent(pinjamButton, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        koleksiBukuLayout.setVerticalGroup(
            koleksiBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(koleksiBukuLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(koleksiBukuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pinjamButton)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EditButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exportButton)
                .addGap(0, 350, Short.MAX_VALUE))
        );

        mainPanel.add(koleksiBuku, "card2");

        ajukanPeminjaman.setBackground(new java.awt.Color(255, 255, 255));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel21.setText("Ajukan Peminjaman");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setText("Judul Buku :");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel23.setText("Tanggal Pinjam");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setText("NIM");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setText("Pengarang :");

        nimTextField.setEditable(false);

        judulTextField.setEditable(false);

        pinjamButton1.setBackground(new java.awt.Color(204, 204, 255));
        pinjamButton1.setText("Pinjam");
        pinjamButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pinjamButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pinjamButton1ActionPerformed(evt);
            }
        });

        btn_batal4.setBackground(new java.awt.Color(204, 204, 255));
        btn_batal4.setText("Kembali");
        btn_batal4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_batal4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batal4ActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setText("Tahun Terbit :");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel27.setText("Tanggal Kembali");

        kembaliDateChooser.setBackground(new java.awt.Color(31, 43, 91,0));
        kembaliDateChooser.setForeground(new java.awt.Color(255, 255, 255));
        kembaliDateChooser.setToolTipText("");
        kembaliDateChooser.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        kembaliDateChooser.setOpaque(false);

        tahunDateChooser.setBackground(new java.awt.Color(31, 43, 91,0));
        tahunDateChooser.setForeground(new java.awt.Color(255, 255, 255));
        tahunDateChooser.setToolTipText("");
        tahunDateChooser.setEnabled(false);
        tahunDateChooser.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        tahunDateChooser.setOpaque(false);

        pinjamDateChooser.setBackground(new java.awt.Color(31, 43, 91,0));
        pinjamDateChooser.setForeground(new java.awt.Color(255, 255, 255));
        pinjamDateChooser.setToolTipText("");
        pinjamDateChooser.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        pinjamDateChooser.setOpaque(false);

        pengarangTextField.setEditable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel22)
                            .addComponent(jLabel25)
                            .addComponent(jLabel23)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nimTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                            .addComponent(judulTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                            .addComponent(kembaliDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                            .addComponent(tahunDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                            .addComponent(pinjamDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                            .addComponent(pengarangTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(btn_batal4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pinjamButton1)))
                .addContainerGap(314, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(nimTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(judulTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel25)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel26))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pengarangTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(tahunDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel23))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(pinjamDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kembaliDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel27)))
                .addGap(37, 37, 37)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pinjamButton1)
                    .addComponent(btn_batal4))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ajukanPeminjamanLayout = new javax.swing.GroupLayout(ajukanPeminjaman);
        ajukanPeminjaman.setLayout(ajukanPeminjamanLayout);
        ajukanPeminjamanLayout.setHorizontalGroup(
            ajukanPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ajukanPeminjamanLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel21)
                .addContainerGap(550, Short.MAX_VALUE))
            .addGroup(ajukanPeminjamanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        ajukanPeminjamanLayout.setVerticalGroup(
            ajukanPeminjamanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ajukanPeminjamanLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 313, Short.MAX_VALUE))
        );

        mainPanel.add(ajukanPeminjaman, "card2");

        add(mainPanel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void pinjamButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pinjamButtonActionPerformed
  int selectedRow = bukuTable.getSelectedRow();
    if (selectedRow != -1) {
        try {
            // Mengambil data dari baris yang dipilih
            String judulBuku = bukuTable.getValueAt(selectedRow, 0).toString();
            String pengarang = bukuTable.getValueAt(selectedRow, 1).toString();
            String tahunTerbitStr = bukuTable.getValueAt(selectedRow, 3).toString();

            // Konversi String ke Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date tahunTerbit = dateFormat.parse(tahunTerbitStr);

            // Pindah ke panel ajukanPeminjaman
            mainPanel.removeAll();
            mainPanel.add(ajukanPeminjaman);
            mainPanel.repaint();
            mainPanel.revalidate();

            // Memperbarui form ajukanPeminjaman dengan data yang diambil
            judulTextField.setText(judulBuku);
            pengarangTextField.setText(pengarang);
            tahunDateChooser.setDate(tahunTerbit);

            // Dapatkan informasi NIM dari sesi
            String nim = SessionManager.getInstance().getNim();
            nimTextField.setText(nim);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal tidak valid: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat mengambil data buku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    } else {
        JOptionPane.showMessageDialog(this, "Pilih buku terlebih dahulu", "Peringatan", JOptionPane.WARNING_MESSAGE);
    }

        
    }//GEN-LAST:event_pinjamButtonActionPerformed

    private void clearForm() {
        
    nimTextField.setText("");
    judulTextField.setText("");
    pengarangTextField.setText("");
    tahunDateChooser.setDate(null);
    pinjamDateChooser.setDate(null);
    kembaliDateChooser.setDate(null);
    
    }

    private void pinjamButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pinjamButton1ActionPerformed
    try {
        // Dapatkan informasi id_user yang bersesuaian dengan nim dari tabel mahasiswa
        String nim = nimTextField.getText();
        int idUser = mahasiswaDAO.getIdUserByNim(nim);
        
        // Dapatkan informasi id_buku yang bersesuaian dengan judul buku dari teks field
        String judulBuku = judulTextField.getText();
        int idBuku = bukuDAO.getIdBukuByJudul(judulBuku);
        
        // Ambil tanggal pinjam dan tanggal kembali dari date chooser
        Date tanggalPinjam = pinjamDateChooser.getDate();
        Date tanggalKembali = kembaliDateChooser.getDate();
        
        // Validasi tanggal kembali harus setelah tanggal pinjam
        if (tanggalKembali.before(tanggalPinjam)) {
            JOptionPane.showMessageDialog(this, "Tanggal kembali tidak boleh sebelum tanggal pinjam.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Keluar dari metode jika validasi gagal
        }
        
        // Hitung selisih hari antara tanggal kembali dan tanggal pinjam
        long selisihMillis = tanggalKembali.getTime() - tanggalPinjam.getTime();
        long selisihHari = selisihMillis / (1000 * 60 * 60 * 24);
        
        // Cek apakah selisih hari sesuai dengan aturan maksimal (5 hari)
        if (selisihHari > 5) {
            JOptionPane.showMessageDialog(this, "Tanggal kembali tidak boleh lebih dari 5 hari setelah tanggal pinjam.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Keluar dari metode jika validasi gagal
        }
        
        // Simpan peminjaman ke database
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String tanggalPinjamStr = dateFormat.format(tanggalPinjam);
        String tanggalKembaliStr = dateFormat.format(tanggalKembali);
        
        boolean berhasil = peminjamanDAO.simpanPeminjaman(idUser, idBuku, tanggalPinjamStr, tanggalKembaliStr);
        
        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Data peminjaman berhasil disimpan.");
            clearForm();
            
            mainPanel.removeAll();
            mainPanel.add(koleksiBuku);
            mainPanel.repaint();
            mainPanel.revalidate();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data peminjaman.");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan dalam menyimpan peminjaman: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
    }//GEN-LAST:event_pinjamButton1ActionPerformed

    private void btn_batal4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batal4ActionPerformed
        // TODO add your handling code here:

        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.revalidate();

        mainPanel.add(koleksiBuku);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btn_batal4ActionPerformed

    
    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        // TODO add your handling code here:
        exportFileChooser = new JFileChooser();
        Export export = new Export();
        exportFileChooser.setDialogTitle("Specify a file to save");
        int userSelection = exportFileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = exportFileChooser.getSelectedFile();
            // Tambahkan ekstensi .pdf jika pengguna tidak menambahkannya
                 if (!fileToSave.getAbsolutePath().endsWith(".pdf")) {
                     fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
                 }
                 export.exportTableToPDF(bukuTable, fileToSave.getAbsolutePath());
             }
    }//GEN-LAST:event_exportButtonActionPerformed

    private void searchTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchTextFieldFocusGained
        // TODO add your handling code here:
        if (searchTextField.getText().equals("Ketik judul buku untuk pencarian")) {
            searchTextField.setText("");
        }
    }//GEN-LAST:event_searchTextFieldFocusGained

    private void searchTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchTextFieldFocusLost
        // TODO add your handling code here:
        if (searchTextField.getText().isEmpty()) {
            searchTextField.setText("Ketik judul buku untuk pencarian");
            
        }
    }//GEN-LAST:event_searchTextFieldFocusLost

    private void EditButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButton1ActionPerformed
        // TODO add your handling code here:
         searchTextField.setText("Ketik judul buku untuk pencarian");

    }//GEN-LAST:event_EditButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EditButton1;
    private javax.swing.JPanel ajukanPeminjaman;
    private javax.swing.JButton btn_batal4;
    private javax.swing.JTable bukuTable;
    private javax.swing.JButton exportButton;
    private javax.swing.JFileChooser exportFileChooser;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField judulTextField;
    private com.toedter.calendar.JDateChooser kembaliDateChooser;
    private javax.swing.JPanel koleksiBuku;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField nimTextField;
    private javax.swing.JTextField pengarangTextField;
    private javax.swing.JButton pinjamButton;
    private javax.swing.JButton pinjamButton1;
    private com.toedter.calendar.JDateChooser pinjamDateChooser;
    private javax.swing.JTextField searchTextField;
    private com.toedter.calendar.JDateChooser tahunDateChooser;
    // End of variables declaration//GEN-END:variables
}

