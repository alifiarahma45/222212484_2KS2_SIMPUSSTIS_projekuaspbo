/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

/**
 *
 * @author LENOVO
 */
import Main.LoginViewer;
import Main.menuUtama;
import Model.MahasiswaDAO;
import Model.SessionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class LoginController {
    private Connection connection;
    private LoginViewer loginView;
    private MahasiswaDAO mahasiswaDAO;

    public LoginController(LoginViewer loginView) {
        this.loginView = loginView;
        this.loginView.setVisible(true);
        this.loginView.getSubmitButton().addActionListener(e -> {
            try {
                authenticateUser();
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
       
    }
    
    private  void authenticateUser() throws SQLException {
    String username = loginView.getUsernameTextField().getText();
    String password = new String(loginView.getPasswordField().getPassword());
    mahasiswaDAO = new MahasiswaDAO();
    
    

    if (mahasiswaDAO.authenticate(username, password)) {
        String nim = SessionManager.getInstance().fetchNimByUsername(username); 
        String role = SessionManager.getInstance().fetchRoleByUsername(username);// Ambil dan simpan NIM dari SessionManager
        if (nim != null) {
            JOptionPane.showMessageDialog(loginView, "Login successful!");
            SessionManager.getInstance().setCurrentUser(username,nim,role); // Set NIM di SessionManager
            
                menuUtama menu = new menuUtama();
                loginView.dispose(); // Close the login view

                menu.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(loginView, "Failed to retrieve NIM for username.");
        }
    } else {
        JOptionPane.showMessageDialog(loginView, "Invalid username or password!");
    }
}

}

