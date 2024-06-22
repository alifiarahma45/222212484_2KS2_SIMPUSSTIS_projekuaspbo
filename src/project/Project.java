/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package project;

import Controller.LoginController;
import Main.LoginViewer;

/**
 *
 * @author LENOVO
 */
public class Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LoginViewer loginView = new LoginViewer();  // Pastikan nama kelas yang sama
        LoginController loginController = new LoginController( loginView);
    }
}


