/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SGIM;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Alesis
 */
public class ControladorInforme implements Initializable{

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnRegresar;

    @FXML
    private CheckBox chkNueva;

    @FXML
    private ComboBox<String> desplegable;

    @FXML
    private VBox pagina;

    @FXML
    private ComboBox<Integer> NumIdCofre;

    @FXML
    void Regresar() {
        
        Stage antiguo = (Stage) desplegable.getScene().getWindow();
        try {
            Parent item = FXMLLoader.load(getClass().getResource("/ventanas/Cofre.fxml"));

            Scene escena = new Scene(item);

            antiguo.setResizable(false);
            antiguo.setScene(escena);
            antiguo.getIcons().add(new Image("/imagenes/Cofre.png"));
            antiguo.setTitle("SGIM");
            antiguo.show();
        } catch (IOException ex) {
            Logger.getLogger(ControladorCofre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void creacion() {

        
        pagina.getChildren().clear();
        
        if(desplegable.getSelectionModel().getSelectedItem().contains("Cofre")){
            
            
            lanzarInforme("/reports/SGIMDB.jasper", null);
        }else{
            
            if(NumIdCofre.getSelectionModel().getSelectedItem()!=null){
                Map<String, Object> param = null;
                param = new HashMap<>();
                param.put("idCofre", Integer.valueOf(NumIdCofre.getSelectionModel().getSelectedItem()));


                lanzarInforme("/reports/Items.jasper", param);
            }else{
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Revisa los campos");
                alerta.setContentText("El campo id cofre esta vacio");
                alerta.showAndWait();
            }
            
            
            
            
            
            
        }
           
        
        
        
        
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NumIdCofre.setDisable(true);
        ObservableList<Integer> listaIds = FXCollections.observableArrayList();
        
        try {
            Connection connection2 = BBDD.getConnection();
            String query2 = "SELECT * FROM Cofre";
            Statement statement2 = connection2.createStatement();
            ResultSet resultadoCofre = statement2.executeQuery(query2);
            
            
            
            while (resultadoCofre.next()) {
                int id = resultadoCofre.getInt("id_cofre");
                listaIds.add(id);
            }
        } catch (SQLException ex) {
            System.err.println("Error");
        }
            
        NumIdCofre.getItems().addAll(listaIds);
        desplegable.getItems().addAll("Cofre", "Items");
        desplegable.getSelectionModel().select("Cofre");
        desplegable.getSelectionModel().selectedItemProperty().addListener((escucha, anterior, nuevo) -> {
            if (desplegable.getSelectionModel().getSelectedItem() != null && desplegable.getSelectionModel().getSelectedItem().equals("Cofre")) {
                NumIdCofre.setDisable(true);
            }
            if (desplegable.getSelectionModel().getSelectedItem() != null && desplegable.getSelectionModel().getSelectedItem().equals("Items")) {
                NumIdCofre.setDisable(false);
            }
        });
    
    }
    
    private void lanzarInforme(String rutaInf, Map <String, Object> param){
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream(rutaInf));
            
            try {
                System.out.println(BBDD.getConnection());
                JasperPrint jasperPrint = JasperFillManager.fillReport(report, param, BBDD.getConnection());
                if(!jasperPrint.getPages().isEmpty()){
                    
                    String outputHtmlFile = "informeHTML.html";
                    JasperExportManager.exportReportToHtmlFile(jasperPrint, outputHtmlFile);
                    
                    

                    if(chkNueva.isSelected()){
                        WebView wnuevo = new WebView();
                        wnuevo.getEngine().load(new File(outputHtmlFile).toURI().toString());
                        Scene escena = new Scene(wnuevo);
                        Stage stage = new Stage();
                        stage.setScene(escena);
                        stage.getIcons().add(new Image("/imagenes/Cofre.png"));
                        stage.setTitle("Informe");
                        stage.showAndWait();
                    }else{
                        
                        WebView wnuevo = new WebView();
                        wnuevo.getEngine().load(new File(outputHtmlFile).toURI().toString());
                        pagina.getChildren().add(wnuevo);
                        
                    }
                    
                    
                    
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(ControladorCofre.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (JRException ex) {
            Logger.getLogger(ControladorCofre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
