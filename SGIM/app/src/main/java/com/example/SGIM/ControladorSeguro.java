/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SGIM;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Alesis
 */
public class ControladorSeguro implements Initializable{
    
    private static boolean acepta=false;
    
    @FXML
    private Text texto;
    
    @FXML
    private Button btnN;

    @FXML
    private Button btnS;
    
    @FXML
    private VBox todo;

    @FXML
    void Cancelar() {
        
        Stage stage = (Stage) texto.getScene().getWindow();
        stage.close();

    }

    @FXML
    void No() throws IOException {
        
        
        acepta = false;
        
        Stage stage = (Stage) texto.getScene().getWindow();
        stage.close();
        

    }

    @FXML
    void Si() throws IOException {
        
        acepta=true;
        Stage stage = (Stage) texto.getScene().getWindow();
        stage.close();

    }
    
    public void cierra(Stage anterior, Stage ultimo){
        
        btnS.setOnAction(e->{
            
            ultimo.close();
            Stage stage = (Stage) texto.getScene().getWindow();
            stage.close();
            
            try {
                Parent cofre = FXMLLoader.load(getClass().getResource("/ventanas/Cofre.fxml"));
                Scene escena = new Scene(cofre);
                
                anterior.setResizable(false);
                anterior.getIcons().add(new Image("/imagenes/Cofre.png"));
                anterior.setScene(escena);
                anterior.show();
            } catch (IOException ex) {
                Logger.getLogger(ControladorSeguro.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
        btnN.setOnAction(e->{
            
            ultimo.close();
            Stage stage = (Stage) texto.getScene().getWindow();
            stage.close();
            
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        acepta=false;
        actualizarFontSize();
        
    }
    
    private void actualizarFontSize() {
        
        todo.setStyle("-fx-font-size: " + ControladorCofre.Tamaño + "px;");
        
        
    }
    
    public static boolean getAcepta(){
        return acepta;
    }
    
}
