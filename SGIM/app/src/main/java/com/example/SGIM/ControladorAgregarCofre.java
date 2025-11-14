/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SGIM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 *
 * @author Alesis
 */
public class ControladorAgregarCofre implements Initializable {

    private ToggleGroup grup;

    private ControladorCofre cC;

    private boolean sePuede = true;

    @FXML
    private TextField locX;

    @FXML
    private TextField locY;

    @FXML
    private TextField locZ;

    @FXML
    private ComboBox<String> desplegable;

    @FXML
    private TextField capacidadText;

    @FXML
    private TextField nameText;
    
    @FXML
    private VBox todo;

    private int idCof;

    private List<ValidationSupport> validadores;

    @FXML
    void aplicar() throws SQLException, IOException {

        sePuede = true;

        if (capacidadText.getText().isEmpty() || nameText.getText().isEmpty() || locX.getText().isEmpty() || locY.getText().isEmpty() || locZ.getText().isEmpty()) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Comprueba los campos");
            alerta.setContentText("Revisa los campos para saber si estan todos ellos rellenados");
            alerta.showAndWait();

        } else {

            try {

                int cant = Integer.parseInt(capacidadText.getText());
                int mapx = Integer.parseInt(locX.getText());
                int mapy = Integer.parseInt(locY.getText());
                int mapz = Integer.parseInt(locZ.getText());

                if (cant < 0) {

                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("Error");
                    alerta.setHeaderText("Comprueba el campo capacidad");
                    alerta.setContentText("El campo capacidad debete tener numeros enteros positivos");
                    alerta.showAndWait();

                } else {

                    Connection connection2 = BBDD.getConnection();
                    String query2 = "SELECT * FROM Almacena";
                    Statement statement2 = connection2.createStatement();
                    ResultSet resultadoAlmacena = statement2.executeQuery(query2);

                    String nombreNuevo = locX.getText() + "/" + locY.getText() + "/" + locZ.getText();

                    boolean acaba = false;

                    while (resultadoAlmacena.next() && sePuede) {

                        if (resultadoAlmacena.next()) {
                            int x = resultadoAlmacena.getInt("x");
                            int y = resultadoAlmacena.getInt("y");
                            int z = resultadoAlmacena.getInt("z");
                            String nombreE = x + "/" + y + "/" + z;

                            if (nombreE.equals(nombreNuevo) || !(cant <= 120) || !(nameText.getText().length() >= 1 && nameText.getText().length() <= 50)) {
                                sePuede = false;

                            }
                        }

                    }

                    if (sePuede) {
                        try {

                            Connection connection = BBDD.getConnection();
                            String query = "INSERT INTO Cofre (nombre, tipo, capacidad) VALUES (?,?,?)";
                            String parte2 = "INSERT INTO Almacena (id_cofre, id_item, cantidad, durabilidad, x, y, z) VALUES (?,?,?,?,?,?,?)";

                            PreparedStatement insertar = connection.prepareStatement(query);
                            insertar.setString(1, nameText.getText());
                            insertar.setString(2, desplegable.getSelectionModel().getSelectedItem());
                            insertar.setInt(3, cant);

                            insertar.executeUpdate();
                            connection.commit();
                            connection.close();

                            Connection nueva = BBDD.getConnection();
                            String lectura = "SELECT * FROM Cofre";
                            Statement statementC = nueva.createStatement();
                            ResultSet resultadoC = statementC.executeQuery(lectura);

                            Connection nueva2 = BBDD.getConnection();

                            while (resultadoC.next()) {
                                idCof = resultadoC.getInt("id_cofre");

                            }
                            nueva.close();

                            PreparedStatement insertarL = nueva2.prepareStatement(parte2);
                            insertarL.setInt(1, idCof);
                            insertarL.setNull(2, Types.INTEGER);
                            insertarL.setNull(3, Types.INTEGER);
                            insertarL.setNull(4, Types.INTEGER);
                            insertarL.setInt(5, mapx);
                            insertarL.setInt(6, mapy);
                            insertarL.setInt(7, mapz);

                            insertarL.executeUpdate();
                            nueva2.commit();
                            nueva2.close();

                        } catch (SQLException ex) {
                            Logger.getLogger(ControladorAgregarCofre.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ToggleGroup grupo = ControladorCofre.getGrupo();
                        VBox apartado = ControladorCofre.getIzq();

                        RadioButton opcion = new RadioButton(nameText.getText());

                        grupo.getToggles().add(opcion);
                        apartado.getChildren().add(opcion);

                        Stage stage = (Stage) desplegable.getScene().getWindow();
                        stage.close();

                    } else {
                        System.err.println(capacidadText.getText());
                        Alert alerta = new Alert(Alert.AlertType.ERROR);
                        alerta.setTitle("Error");
                        alerta.setHeaderText("Comprueba los campos x, y, z");
                        alerta.setContentText("Ya ha sido creado un cofre en esa localización, deberas eliminar ese cofre o colocar este cofre nuevo en otra posición");
                        alerta.showAndWait();
                    }

                }

            } catch (NumberFormatException ex) {
                System.err.println(nameText.getText());

                System.err.println(capacidadText.getText());
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Comprueba el campo capacidad, los campos x, y, z o el campo nombre");
                alerta.setContentText("El campo capacidad, x, y, z deberia tener numeros enteros positivos, y en el caso de ser el campo nombre no puede tener mas de 50 caracteners ademas de no tener caracteres");
                alerta.showAndWait();

            }

        }

    }

    @FXML
    void cancelar() {

        Stage stage = (Stage) desplegable.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        actualizarFontSize();

        desplegable.getItems().addAll("Cofre", "Grande", "Ender", "Shulker", "Barril");

        desplegable.getSelectionModel().select("Cofre");

        validadores = new ArrayList<>();

        /*ValidationSupport vSNombre = new ValidationSupport();
        vSNombre.registerValidator(nameText, Validator.createRegexValidator(
            "El nombre debe tener entre 1 y 50 caracteres", ".{1,50}", Severity.ERROR));*/
        ValidationSupport vSNombre = new ValidationSupport();
        vSNombre.registerValidator(nameText, Validator.createPredicateValidator(
                texto -> {
                    try {
                        int numero = texto.toString().length();
                        return numero >= 1 && numero <= 50;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "Debe tener el nombre introducido entre 1 y 50"
        ));

        ValidationSupport vSCapacidad = new ValidationSupport();
        vSCapacidad.registerValidator(capacidadText, Validator.createPredicateValidator(
                texto -> {
                    try {
                        int numero = Integer.parseInt((String) texto);
                        return numero >= 1 && numero <= 120;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "La capacidad debe ser un número entero positivo entre 1 y 120"
        ));

        ValidationSupport vSLocX = new ValidationSupport();
        vSLocX.registerValidator(locX, Validator.createPredicateValidator(
                texto -> {
                    try {
                        Integer.parseInt((String) texto);
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "El campo debe ser un número entero, ademas de ser una localización que no se repita"
        ));

        ValidationSupport vSLocY = new ValidationSupport();
        vSLocY.registerValidator(locY, Validator.createPredicateValidator(
                texto -> {
                    try {
                        Integer.parseInt((String) texto);
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "El campo debe ser un número entero, ademas de ser una localización que no se repita"
        ));

        ValidationSupport vSLocZ = new ValidationSupport();
        vSLocZ.registerValidator(locZ, Validator.createPredicateValidator(
                texto -> {
                    try {
                        Integer.parseInt((String) texto);
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                },
                "El campo debe ser un número entero, ademas de ser una localización que no se repita"
        ));

        validadores.addAll(Arrays.asList(vSNombre, vSCapacidad, vSLocX, vSLocY, vSLocZ));

        Platform.runLater(() -> {
            validadores.forEach(ValidationSupport::initInitialDecoration);
        });

    }

    public void agrupa(Stage antiguo) throws IOException {

        Parent cambio = FXMLLoader.load(getClass().getResource("/ventanas/Almacena.fxml"));

        Scene scene = new Scene(cambio);

        antiguo.setScene(scene);
        antiguo.setResizable(false);
        antiguo.getIcons().add(new Image("/imagenes/agrega.png"));
        antiguo.setTitle("Agregar");
        antiguo.show();

    }
    
    private void actualizarFontSize() {
        
        todo.setStyle("-fx-font-size: " + ControladorCofre.Tamaño + "px;");
        
        
    }

}
