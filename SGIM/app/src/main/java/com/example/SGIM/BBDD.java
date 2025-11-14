/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SGIM;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Alesis
 */
public class BBDD {

    private static String url;
    private static String usuario;
    private static String pass;
    private static final String ruta = "config.properties";

    

    public static Connection getConnection() throws SQLException {
        
        Properties proper = new Properties();

        try {
            File archivoConfig = new File(ruta);
        

            if (!archivoConfig.exists()) {
                try (FileOutputStream crea = new FileOutputStream(archivoConfig)) {
                    proper.setProperty("db.url", "jdbc:mariadb://localhost:3306/SGIM");
                    proper.setProperty("db.user", "usuario");
                    proper.setProperty("db.password", "usuario");
                    proper.store(crea, "Configuración predeterminada de la BBDD");
                    System.out.println("Archivo de configuracion creado con valores predeterminados.");
                } catch (Exception e) {
                    System.out.println("Error al crear el archivo de configuracion: " + e.getMessage());
                }   
            }

            try (FileInputStream lee = new FileInputStream(archivoConfig)) {
                proper.load(lee);
                url = proper.getProperty("db.url");
                usuario = proper.getProperty("db.user");
                pass = proper.getProperty("db.password");

            }

        } catch (IOException e) {
            System.err.println("Error al manejar el archivo de configuracion: " + e.getMessage());
        }

        return DriverManager.getConnection(url, usuario, pass);
    }

}
