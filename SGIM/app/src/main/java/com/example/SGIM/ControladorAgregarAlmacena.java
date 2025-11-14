/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SGIM;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Alesis
 */
public class ControladorAgregarAlmacena implements Initializable {

    @FXML
    private Text texto;

    @FXML
    void aplicar() {

        Stage stage = (Stage) texto.getScene().getWindow();

        stage.close();

    }

    @FXML
    void cancelar() {

        Stage stage = (Stage) texto.getScene().getWindow();

        stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
