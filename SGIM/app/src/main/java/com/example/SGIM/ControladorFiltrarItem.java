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
public class ControladorFiltrarItem implements Initializable {

    @FXML
    private ComboBox<String> desplegable;

    @FXML
    private Text texto;

    @FXML
    private VBox todo;
    
    private static int x = 0;
    
    private static int y = 0;
    
    private static int z = 0;
    
    
    
    private static String query = "";
    private static boolean acepta = false;

    @FXML
    void aplicar() {
        String opcion = desplegable.getSelectionModel().getSelectedItem();

        
        if ("Todos".equals(opcion)) {
            query = "SELECT i.nombre, a.cantidad, i.imagen, i.tipo, a.durabilidad, a.posicion FROM Almacena AS a JOIN Item AS i ON a.id_item = i.id_item WHERE a.x = ? AND a.y = ? AND a.z = ? ORDER BY a.posicion ASC;";
            
            
            
        } else {
            query = "SELECT i.nombre, a.cantidad, i.imagen, i.tipo, a.durabilidad, a.posicion FROM Almacena AS a JOIN Item AS i ON a.id_item = i.id_item WHERE a.x = ? AND a.y = ? AND a.z = ? AND i.tipo = '"+desplegable.getSelectionModel().getSelectedItem()+"' ORDER BY a.posicion ASC;";
        
            
        
        }

        acepta = true;

        
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
        actualizarFontSize();
        desplegable.getItems().addAll("Objeto", "Armadura", "Herramienta", "Todos");
        desplegable.getSelectionModel().select("Objeto");
    }

    private void actualizarFontSize() {
        todo.setStyle("-fx-font-size: " + ControladorCofre.Tamaño + "px;");
    }
    
    public static boolean getAcepta() {
        return acepta;
    }

    public static String getQuery() {
        return query;
    }
    

    public static void setLoca(int x, int y, int z) {
        ControladorFiltrarItem.x = x;
        
        ControladorFiltrarItem.y = y;
        
        ControladorFiltrarItem.z = z;
    }
}
