/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SGIM;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Alesis
 */
public class ControladorEliminar implements Initializable {

    @FXML
    private Text texto;
    
    @FXML
    private VBox todo;

    private static boolean acepta;

    @FXML
    void aceptar() {

        acepta = true;
        Stage stage = (Stage) texto.getScene().getWindow();
        stage.close();

    }

    public static boolean getAcepta() {
        return ControladorEliminar.acepta;
    }

    @FXML
    void cancelar() {

        acepta = false;

        Stage stage = (Stage) texto.getScene().getWindow();

        stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actualizarFontSize();
    }
    
    private void actualizarFontSize() {
        
        todo.setStyle("-fx-font-size: " + ControladorCofre.Tamaño + "px;");
        
        
    }

}
