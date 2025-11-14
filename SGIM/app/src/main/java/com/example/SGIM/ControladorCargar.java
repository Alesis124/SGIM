/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SGIM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Alesis
 */
public class ControladorCargar implements Initializable{
    
    private boolean cierra=false;
    
    private ControladorSeguro cS;
    
    @FXML
    private TextField ruta;
    
    @FXML
    private Button btn;
    
    @FXML
    private VBox todo;

    @FXML
    void aplicar() throws IOException {
        
        if(ruta.getText().isEmpty()||!ruta.getText().contains(".sql")){
            
            
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("No has completado los campos correctamente");
            alerta.setContentText("Debes poner un archivo sql para poder cargar la base de datos");
            alerta.showAndWait();
            
            cierra=false;
        }else{
            cierra=true;
        }
        
            
        
        
        
        
        
        

        
        

    }
    
    public void cierra(Stage antiguo) throws IOException{
        
        
        
        
        
        
        btn.setOnAction(e->{
            try {
                aplicar();
                if(cierra){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/Seguro.fxml"));
                    Parent root = loader.load();
                    this.cS = loader.getController();
                    
                    
                    Scene escenaSe = new Scene(root);
                    Stage stageSe = new Stage();
                    stageSe.setResizable(false);
                    stageSe.getIcons().add(new Image("/imagenes/warning.png"));
                    stageSe.setTitle("¡¿Seguro?!");
                    stageSe.setScene(escenaSe);
                    stageSe.showAndWait();
                    
                    if(ControladorSeguro.getAcepta()){
                        File archivo = new File(ruta.getText());
                        importSQLFile(archivo);
                    }

                    Stage stage = (Stage) ruta.getScene().getWindow();
                    this.cS.cierra(antiguo, stage);
                





                }
            } catch (IOException ex) {
                Logger.getLogger(ControladorCargar.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        
        
        
    }
    
    
    @FXML
    void cancelar(){
        
        Stage stage = (Stage) ruta.getScene().getWindow();
        
        stage.close();
        
        
        
    }

    @FXML
    void seleccionar() {
        
        Stage stage = (Stage) ruta.getScene().getWindow();
        
        FileChooser elije = new FileChooser();
        elije.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos SQL", "*.sql"));
        File archivo = elije.showOpenDialog(stage);
        
        
        if (archivo != null){
            
            ruta.setText(archivo.getAbsolutePath());
            
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actualizarFontSize();
    }
    
    private void actualizarFontSize() {
        
        todo.setStyle("-fx-font-size: " + ControladorCofre.Tamaño + "px;");
        
        
    }
    
    private void importSQLFile(File archivo) {
        try (Connection connection = BBDD.getConnection();
             Statement statement = connection.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader(archivo))) {

            // Eliminar las tablas existentes
            dropExistingTables(connection);

            // Procesar el archivo SQL
            StringBuilder sqlBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--") || line.startsWith("/*")) {
                    continue; // Ignorar líneas vacías y comentarios
                }
                sqlBuilder.append(line);
                if (line.endsWith(";")) {
                    statement.execute(sqlBuilder.toString());
                    sqlBuilder.setLength(0); // Limpiar el acumulador
                }
            }

            System.out.println("Archivo SQL importado correctamente.");

        } catch (Exception ex) {
            System.err.println("Error al importar el archivo SQL: " + ex.getMessage());
        }
    }
    
    private void dropExistingTables(Connection connection) {
    try (Statement statement = connection.createStatement()) {
        // Desactivar las restricciones de claves foráneas
        statement.execute("SET FOREIGN_KEY_CHECKS = 0;");

        // Obtener todas las tablas de la base de datos
        String query = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'SGIM'";
        ResultSet resultSet = statement.executeQuery(query);

        // Crear una lista para almacenar las tablas
        List<String> tables = new ArrayList<>();

        while (resultSet.next()) {
            tables.add(resultSet.getString("TABLE_NAME"));
        }

        // Eliminar cada tabla encontrada
        for (String tableName : tables) {
            try {
                statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
                System.out.println("Tabla eliminada: " + tableName);
            } catch (SQLException ex) {
                System.err.println("Error al eliminar la tabla " + tableName + ": " + ex.getMessage());
            }
        }

        // Reactivar las restricciones de claves foráneas
        statement.execute("SET FOREIGN_KEY_CHECKS = 1;");
    } catch (SQLException ex) {
        System.err.println("Error al eliminar tablas existentes: " + ex.getMessage());
    }
}



    
}
