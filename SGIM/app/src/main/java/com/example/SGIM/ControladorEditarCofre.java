/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SGIM;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 *
 * @author Alesis
 */
public class ControladorEditarCofre implements Initializable{
    
    
    @FXML
    private TextField capacidadT;

    @FXML
    private ComboBox<String> desplegable;

    @FXML
    private TextField nombreT;
    
    @FXML
    private VBox todo;
    
    private static int id;
    
    private List<ValidationSupport> validadores;
    
    private static boolean acepta = false;
    
    private static String nom="";
    
    

    
    @FXML
    void aplicar() throws SQLException {
        
        Connection connection2 = BBDD.getConnection();
        String query2 = "SELECT * FROM Cofre";
        PreparedStatement statement2 = connection2.prepareStatement(query2);
        ResultSet rs = statement2.executeQuery();
        
        try{
            
            int capacidadtxt = Integer.parseInt(capacidadT.getText());
            String tipotxt = desplegable.getSelectionModel().getSelectedItem();
            nom = nombreT.getText();
            
            if(capacidadtxt>0){
                while(rs.next()){
                    int idC = rs.getInt("id_cofre");


                    if(idC==id){

                        Connection connection = BBDD.getConnection();
                        String query = "UPDATE Cofre SET capacidad = ?, tipo = ?, nombre = ? WHERE id_cofre = ?";
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setInt(1, capacidadtxt);
                        statement.setString(2, tipotxt);
                        statement.setString(3, nom);
                        statement.setInt(4, idC);

                        statement.executeUpdate();

                        Stage stage = (Stage) desplegable.getScene().getWindow();
                        stage.close();
                    }



                    acepta=true;

                }
            }else{
                Alert alerta = new Alert (Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Revisa los campos");
                alerta.setContentText("Debes colocar en capacidad numeros enteros positivos mayor que cero");
                alerta.showAndWait();
            }

            
            
        }catch (NumberFormatException ex){
            Alert alerta = new Alert (Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Revisa los campos");
            alerta.setContentText("Debes colocar en capacidad numeros enteros positivos mayor que cero");
            alerta.showAndWait();
        }
            
        
        
        

    }

    public static void setId(int id_cofre){
        
        ControladorEditarCofre.id=id_cofre;
        
    }
    
    @FXML
    void cancelar() {
        
        Stage stage = (Stage) desplegable.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        acepta=false;
        actualizarFontSize();
                
        validadores = new ArrayList<>();
        
        desplegable.getItems().addAll("Cofre", "Grande", "Ender", "Shulker", "Barril");
        
        try {
            Connection connection = BBDD.getConnection();
            String query = "SELECT * FROM Cofre";
            Statement statement = connection.createStatement();
            ResultSet resultadoCofre = statement.executeQuery(query);
            
            
            
            
            
            while (resultadoCofre.next()) {
                int id_cofre = resultadoCofre.getInt("id_cofre");
                String nombre = resultadoCofre.getString("nombre");
                String tipo = resultadoCofre.getString("tipo");
                int capacidad = resultadoCofre.getInt("capacidad");
                
                if(ControladorEditarCofre.id==id_cofre){
                    
                    nombreT.setText(nombre);
                    
                    capacidadT.setText(""+capacidad);
                    
                    
                    desplegable.getSelectionModel().select(tipo);
                    
                    
                }
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorEditarCofre.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        ValidationSupport vSNombre = new ValidationSupport();
        vSNombre.registerValidator(nombreT, Validator.createRegexValidator(
            "El nombre debe tener entre 1 y 50 caracteres", ".{1,50}", Severity.ERROR));
        
        ValidationSupport vSCapacidad = new ValidationSupport();
        vSCapacidad.registerValidator(capacidadT, Validator.createPredicateValidator(
                    texto -> {
                try {
                    int numero = Integer.parseInt((String)texto);
                    return numero >= 1 && numero <= 120;
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "La capacidad debe ser un número entero positivo entre 1 y 120"
        ));
        
        
        validadores.addAll(Arrays.asList(vSNombre, vSCapacidad));
                
        Platform.runLater(() -> {
            validadores.forEach(ValidationSupport::initInitialDecoration);
        });
        
        
        
    }
    
    private void actualizarFontSize() {
        
        todo.setStyle("-fx-font-size: " + ControladorCofre.Tamaño + "px;");
        
        
    }
    
    public static boolean getAcepta(){
        return acepta;
    }
    
    public static String getNombre(){
        return nom;
    }
    
}
