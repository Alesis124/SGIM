/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SGIM;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Alesis
 */
public class ControladorModo implements Initializable{
    
    @FXML
    private Text textoCofre;
    
    @FXML
    void Almacena() throws IOException {
        
        
        
        
        
        
        
        Parent cofre = FXMLLoader.load(getClass().getResource("/ventanas/Almacena.fxml"));
        Scene escena = new Scene(cofre);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(escena);
        stage.getIcons().add(new Image("/imagenes/cofre.png"));
        stage.setTitle("SGIM");
        stage.show();
        Stage antiguo = (Stage) textoCofre.getScene().getWindow();
        
        antiguo.close();

    }

    @FXML
    void Cofre() throws IOException {
        
        Parent cofre = FXMLLoader.load(getClass().getResource("/ventanas/Cofre.fxml"));
        Scene escena = new Scene(cofre);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(escena);
        stage.getIcons().add(new Image("/imagenes/cofre.png"));
        stage.setTitle("SGIM");
        stage.show();
        Stage antiguo = (Stage) textoCofre.getScene().getWindow();
        
        antiguo.close();

    }

    @FXML
    void Item() throws IOException {
        
        Parent cofre = FXMLLoader.load(getClass().getResource("/ventanas/Item.fxml"));
        Scene escena = new Scene(cofre);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(escena);
        stage.getIcons().add(new Image("/imagenes/cofre.png"));
        stage.setTitle("SGIM");
        stage.show();
        Stage antiguo = (Stage) textoCofre.getScene().getWindow();
        
        antiguo.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
        
    }
    
    
    
}
