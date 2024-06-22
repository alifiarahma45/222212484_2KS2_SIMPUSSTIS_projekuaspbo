/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author LENOVO
 */
public class Export {
    public static void exportTableToCSV(JTable table, String filePath) {
    try (FileWriter csvWriter = new FileWriter(filePath)) {
        TableModel model = table.getModel();

        // Menuliskan header row
        for (int i = 0; i < model.getColumnCount(); i++) {
            csvWriter.write(model.getColumnName(i));
            if (i < model.getColumnCount() - 1) {
                csvWriter.write(",");
            }
        }
        csvWriter.write("\n");

        // Menuliskan data rows
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                csvWriter.write(model.getValueAt(i, j).toString());
                if (j < model.getColumnCount() - 1) {
                    csvWriter.write(",");
                }
            }
            csvWriter.write("\n");
        }

        csvWriter.flush();
        JOptionPane.showMessageDialog(null, "Ekspor ke format CSV berhasil!", "Ekspor Berhasil", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Exported table data to " + filePath);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    private PdfFont getCustomFont() throws Exception {
    return PdfFontFactory.createFont();
    }

    
    // Metode untuk mengekspor tabel ke PDF
    public  void exportTableToPDF(JTable table, String filePath) {
    try {
        // Membuat PdfWriter instance
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        TableModel model = table.getModel();

        // Membuat tabel dengan jumlah kolom sesuai dengan tabel
        Table pdfTable = new Table(model.getColumnCount());

        // Menambahkan header tabel
        for (int i = 0; i < model.getColumnCount(); i++) {
            Cell header = new Cell();
            header.add(new Paragraph(model.getColumnName(i))
                    .setFont(getCustomFont())
                    .setFontColor(ColorConstants.WHITE));
            header.setBackgroundColor(ColorConstants.BLUE);
            header.setTextAlignment(TextAlignment.CENTER);
            pdfTable.addCell(header);
        }

        // Menambahkan data ke tabel
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                pdfTable.addCell(new Cell()
                        .add(new Paragraph(model.getValueAt(i, j).toString())
                                .setFont(getCustomFont())));
            }
        }

        document.add(pdfTable);
        document.close();

        JOptionPane.showMessageDialog(null, "Ekspor ke format PDF berhasil!", "Ekspor Berhasil", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("Exported table data to " + filePath);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "File tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (com.itextpdf.io.IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat mengakses file!", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Ekspor ke format PDF gagal!", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
    
    
}
