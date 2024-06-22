/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Controller.DatabaseConnection;

/**
 * Manages user sessions and retrieves user details from the database.
 */
public class SessionManager {
    private static SessionManager instance;
    private String currentUsername;
    private String currentUserNim;
    private int currentUserId;
    private String currentUserRole;

    // Private constructor for Singleton pattern
    private SessionManager() {
        // Initialize if needed
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setCurrentUser(String username, String nim, String role) {
        this.currentUsername = username;
        this.currentUserNim = nim;
        this.currentUserRole = role;
    }

    public String getCurrentUserRole() {
        return currentUserRole;
    }

    public void setUserRole(String role) {
        this.currentUserRole = role;
    }

    public void setNim(String nim) {
        this.currentUserNim = nim;
    }

    public String getNim() {
        return currentUserNim;
    }

    /**
     * Retrieves NIM (student ID) from the database based on the username.
     *
     * @param username The username to look up.
     * @return The NIM if found, otherwise null.
     */
    public String fetchNimByUsername(String username) {
        String nim = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT m.nim " +
                           "FROM user u " +
                           "INNER JOIN mahasiswa m ON u.id_user = m.id_user " +
                           "WHERE u.username = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nim = rs.getString("nim");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nim;
    }

    /**
     * Retrieves the role of the user from the database based on the username.
     *
     * @param username The username to look up.
     * @return The role if found, otherwise null.
     */
    public String fetchRoleByUsername(String username) {
        String role = null;
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();
            String query = "SELECT role FROM user WHERE username = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                role = rs.getString("role");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    /**
     * Retrieves the user ID from the database based on the current user's NIM.
     *
     * @return The user ID if found, otherwise -1.
     * @throws SQLException If an SQL error occurs.
     */
    public int getIdUserByNim() throws SQLException {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        String sql = "SELECT id_user FROM mahasiswa WHERE nim = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, currentUserNim);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_user");
                }
            }
        }
        return -1; // Return -1 if no user ID is found
    }
}
