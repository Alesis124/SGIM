/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SGIM;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Alesis
 */
public class ControladorFiltrarCofre implements Initializable{
    
    @FXML
    private Text texto;
    
    @FXML
    private ComboBox<String> desplegable;
    
    @FXML
    private VBox todo;
    
    private static String query = "SELECT * FROM Cofre";
    
    private static boolean acepta = false;

    @FXML
    void aplicar() {
        
        
        String opcion = desplegable.getSelectionModel().getSelectedItem();
        
        if(opcion.equals("Todos")){
            
            query = "SELECT * FROM Cofre";
            
        }else{
            
            query = "SELECT * FROM Cofre WHERE tipo = '"+desplegable.getSelectionModel().getSelectedItem()+"'";
            
            
            
        
        }
        
        acepta=true;
        
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
        
        acepta=false;
        
        actualizarFontSize();
        
        desplegable.getItems().addAll("Cofre", "Grande", "Ender", "Shulker", "Barril", "Todos");
        
        desplegable.getSelectionModel().select("Cofre");
        
    }
    
    private void actualizarFontSize() {
        
        todo.setStyle("-fx-font-size: " + ControladorCofre.Tamaño + "px;");
        
        
    }
    
    public static boolean getAcepta(){
        return acepta;
    }
    
    
    public static String getQuery(){
        return query;
    }
    
    
}
