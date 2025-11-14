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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 *
 * @author Alesis
 */
public class ControladorEditarAlmacena implements Initializable{
    
    @FXML
    private Text texto;
    
    @FXML
    private TextField xT;

    @FXML
    private TextField yT;

    @FXML
    private TextField zT;
    
    @FXML
    private VBox todo;
    
    private static List<Integer> posicion = new ArrayList<>();
    
    private boolean loHace = true;
    
    private static boolean primera = true;
    
    private List<ValidationSupport> validadores;
    
    private static boolean acepta;

    @FXML
    void aplicar() throws SQLException {
        
        loHace=true;
        
        Connection connection2 = BBDD.getConnection();
        String query2 = "SELECT * FROM Almacena";
        PreparedStatement statement2 = connection2.prepareStatement(query2);
        ResultSet rs = statement2.executeQuery();
        
        try {
            int xtxt = Integer.parseInt(xT.getText());
            int ytxt = Integer.parseInt(yT.getText());
            int ztxt = Integer.parseInt(zT.getText());

            String nombretxt=xT.getText()+"/"+yT.getText()+"/"+zT.getText();

            while(rs.next()){
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int z = rs.getInt("z");

                String nombre = x+"/"+y+"/"+z;

                if(nombretxt.equals(nombre)){

                    loHace=false;

                }


            }




            if(loHace){
                
                for(int idPos : posicion){
                    
                    Connection connection = BBDD.getConnection();
                    String query = "UPDATE Almacena SET x = ?, y = ?, z = ? WHERE posicion = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, xtxt);
                    statement.setInt(2, ytxt);
                    statement.setInt(3, ztxt);
                    statement.setInt(4, idPos);

                    statement.executeUpdate();
                    
                }
                
                

                Stage stage = (Stage) texto.getScene().getWindow();

                acepta=true;
                
                stage.close();
            }else{

                Alert alerta = new Alert (Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Ya existe esa posición");
                alerta.setContentText("Deberas colocar una posicion que no exista");
                alerta.showAndWait();

            }
        }catch (NumberFormatException ex){
            Alert alerta = new Alert (Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Revisa los campos");
            alerta.setContentText("Debes colocar numeros enteros en todos los campos");
            alerta.showAndWait();
        }
        
        
        
        

    }

    @FXML
    void cancelar() {
        
        Stage stage = (Stage) texto.getScene().getWindow();
        
        stage.close();
        acepta=false;

    }
    
    public static void agregarPosicion(int pos){
        
        if(primera){
            posicion=new ArrayList<>();
            primera=false;
            
        }
        
        
        ControladorEditarAlmacena.posicion.add(pos);
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        actualizarFontSize();
        
        Connection connection;
        
        try {
            connection = BBDD.getConnection();
            String query = "SELECT * FROM Almacena";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            
            while(rs.next()){
                int pos = rs.getInt("posicion");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int z = rs.getInt("z");
            
                if(posicion.contains(pos)){
                    xT.setText(""+x);
                    yT.setText(""+y);
                    zT.setText(""+z);
                }
                
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ControladorEditarAlmacena.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        validadores = new ArrayList<>();
        
        
        ValidationSupport vSLocX = new ValidationSupport();
        vSLocX.registerValidator(xT, Validator.createPredicateValidator(
            texto -> {
                try {
                    Integer.parseInt((String) texto);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "El campo debe ser un número entero"
        ));

        ValidationSupport vSLocY = new ValidationSupport();
        vSLocY.registerValidator(yT, Validator.createPredicateValidator(
            texto -> {
                try {
                    Integer.parseInt((String) texto);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "El campo debe ser un número entero"
        ));

        ValidationSupport vSLocZ = new ValidationSupport();
        vSLocZ.registerValidator(zT, Validator.createPredicateValidator(
            texto -> {
                try {
                    Integer.parseInt((String) texto);
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            },
            "El campo debe ser un número entero"
        ));
        
        validadores.addAll(Arrays.asList(vSLocX, vSLocY, vSLocZ));
                
        Platform.runLater(() -> {
            validadores.forEach(ValidationSupport::initInitialDecoration);
        });
            
        
        
        
        
        
    }
    
    public static boolean getAcepta() {
        return ControladorEditarAlmacena.acepta;
    }
    
    private void actualizarFontSize() {
        
        todo.setStyle("-fx-font-size: " + ControladorCofre.Tamaño + "px;");
        
        
    }
    
    public static void delPosicion() {
        if(!posicion.isEmpty()){
            posicion.clear();
        }
        
        
    }
    
}
