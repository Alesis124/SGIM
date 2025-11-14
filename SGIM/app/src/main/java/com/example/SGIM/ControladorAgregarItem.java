/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SGIM;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 *
 * @author Alesis
 */
public class ControladorAgregarItem implements Initializable {

    @FXML
    private TextField capacidad;

    @FXML
    private ComboBox<String> desplegable;

    @FXML
    private TextField durabilidad;

    @FXML
    private ComboBox<String> nombre;

    @FXML
    private TextField stack;
    
    @FXML
    private VBox todo;

    private String imagen64;

    private ControladorItem cI;

    private List<ValidationSupport> validadores;

    private List<Integer> ids_items;

    public void setControladorItem(ControladorItem controladorItem) {
        this.cI = controladorItem;
    }

    private boolean entraS = false;

    private boolean entraC = false;

    private int id_Item;

    @FXML
    void aplicar() throws SQLException, IOException {

        boolean validacionCorrecta = validadores.stream().allMatch(v -> v.getValidationResult().getErrors().isEmpty()); //Detecta todos los errores y si existen errores no deja agregarlo

        
        boolean entro = false;

        if (!validacionCorrecta) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Corrige los errores antes de aplicar los cambios.");
            alert.showAndWait();
            return;
        }

        try {

            if (!nombre.getItems().isEmpty()) {

                

                String nom = nombre.getSelectionModel().getSelectedItem();

                

                Connection lee = BBDD.getConnection();
                String lectura = "SELECT * FROM Item";
                Statement statementC = lee.createStatement();
                ResultSet resultadoI = statementC.executeQuery(lectura);

                //id_Item = resultadoI.getInt("id_item");
                while (resultadoI.next()) {

                    String name = resultadoI.getString("nombre");

                    if (name.equals(nom)) {
                        
                        entro=true;

                        id_Item = resultadoI.getInt("id_item");

                        String tipo = resultadoI.getString("tipo");
                        int x = ControladorItem.getX();
                        int y = ControladorItem.getY();
                        int z = ControladorItem.getZ();

                        if (tipo.equals("Objeto")) {
                            int cap = Integer.parseInt(capacidad.getText());
                            int cant = Integer.parseInt(stack.getText());
                            int repite = Integer.parseInt(stack.getText());
                            if (cap < 0 || cant < 0) {
                                throw new NumberFormatException();
                            }
                            System.out.println(capacidad.getText().isEmpty());
                            if (!capacidad.getText().isEmpty()) {
                                for (int i = 0; i < repite; i++) {

                                    Connection connection = BBDD.getConnection();
                                    String parte2 = "INSERT INTO Almacena (id_cofre, id_item, cantidad, durabilidad, x, y, z) VALUES (?,?,?,?,?,?,?)";

                                    PreparedStatement insertar = connection.prepareStatement(parte2);
                                    insertar.setInt(1, ControladorItem.getIdCofre());
                                    insertar.setInt(2, id_Item);
                                    insertar.setInt(3, cap);
                                    insertar.setNull(4, Types.INTEGER);
                                    insertar.setInt(5, x);
                                    insertar.setInt(6, y);
                                    insertar.setInt(7, z);

                                    insertar.executeUpdate();
                                    connection.commit();
                                    connection.close();

                                }

                            } else {
                                Alert alerta = new Alert(Alert.AlertType.ERROR);
                                alerta.setTitle("Error");
                                alerta.setHeaderText("Comprueba los campos");
                                alerta.setContentText("Revisa los campos disponibles para saber si estan todos ellos rellenados");
                                alerta.showAndWait();
                            }
                        }

                        if (tipo.equals("Armadura")) {

                            if (name.equals(nom)) {
                                

                                int dura = Integer.parseInt(durabilidad.getText());

                                if (dura < 0) {
                                    throw new NumberFormatException();
                                }
                                
                                if(!durabilidad.getText().isEmpty()){
                                    
                                    Connection connection = BBDD.getConnection();
                                    String parte2 = "INSERT INTO Almacena (id_cofre, id_item, cantidad, durabilidad, x, y, z) VALUES (?,?,?,?,?,?,?)";

                                    PreparedStatement insertar = connection.prepareStatement(parte2);
                                    insertar.setInt(1, ControladorItem.getIdCofre());
                                    insertar.setInt(2, id_Item);
                                    insertar.setInt(3, 1);
                                    insertar.setInt(4, dura);
                                    insertar.setInt(5, x);
                                    insertar.setInt(6, y);
                                    insertar.setInt(7, z);

                                    insertar.executeUpdate();
                                    connection.commit();
                                    connection.close();
                                    
                                }else{
                                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                                    alerta.setTitle("Error");
                                    alerta.setHeaderText("Comprueba los campos");
                                    alerta.setContentText("Revisa los campos disponibles para saber si estan todos ellos rellenados");
                                    alerta.showAndWait();
                                }

                                

                            }

                        }
                        
                        if(tipo.equals("Herramienta")){
                            
                            
                            if (name.equals(nom)) {
                                int dura = Integer.parseInt(durabilidad.getText());
                                
                                if (dura < 0) {
                                    throw new NumberFormatException();
                                }
                                if(!durabilidad.getText().isEmpty()){
                                    
                                    Connection connection = BBDD.getConnection();
                                    String parte2 = "INSERT INTO Almacena (id_cofre, id_item, cantidad, durabilidad, x, y, z) VALUES (?,?,?,?,?,?,?)";

                                    PreparedStatement insertar = connection.prepareStatement(parte2);
                                    insertar.setInt(1, ControladorItem.getIdCofre());
                                    insertar.setInt(2, id_Item);
                                    insertar.setInt(3, 1);
                                    insertar.setInt(4, dura);
                                    insertar.setInt(5, x);
                                    insertar.setInt(6, y);
                                    insertar.setInt(7, z);

                                    insertar.executeUpdate();
                                    connection.commit();
                                    connection.close();
                                    
                                }else{
                                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                                    alerta.setTitle("Error");
                                    alerta.setHeaderText("Comprueba los campos");
                                    alerta.setContentText("Revisa los campos disponibles para saber si estan todos ellos rellenados");
                                    alerta.showAndWait();
                                }
                                
                            }

                                
                                
                                
                            
                        }

                    } else {
                        entro=false;
                    }

                }
                
                

                lee.close();

                int x = ControladorItem.getX();
                int y = ControladorItem.getY();
                int z = ControladorItem.getZ();

                int id_cofre = ControladorItem.getIdCofre();

                Stage stage = (Stage) desplegable.getScene().getWindow();
                stage.close();
            } else {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Comprueba los campos");
                alerta.setContentText("Revisa los campos disponibles para saber si estan todos ellos rellenados");
                alerta.showAndWait();
            }
            
            /*if (!entro) {
                System.err.println("JL");
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText("Comprueba los campos");
                alerta.setContentText("Revisa los campos disponibles para saber si estan todos ellos rellenados");
                alerta.showAndWait();
            }*/

            
        } catch (NumberFormatException ex) {
            System.err.println(capacidad.getText());
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Comprueba el campo capacidad, durabilidad (en el caso de que sea una herramienta o armadura) o el de stack");
            alerta.setContentText("El campo capacidad, durabilidad o el de stack deberia tener numeros enteros positivos");
            alerta.showAndWait();
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
        
        int id_cofre = 0;
        int capacidadB = 0;
        ids_items = new ArrayList<>();
        int total = 0;

        Connection connection;
        try {
            connection = BBDD.getConnection();
            String query = "SELECT * FROM Cofre";
            Statement statement = connection.createStatement();
            ResultSet resultadoCofre = statement.executeQuery(query);

            Connection connection2 = BBDD.getConnection();
            String query2 = "SELECT * FROM Almacena";
            Statement statement2 = connection2.createStatement();
            ResultSet resultadoAlmacena = statement2.executeQuery(query2);
            
            String query3 = "SELECT * FROM Item";
            Statement statement3 = connection2.createStatement();
            ResultSet resultadoItem = statement3.executeQuery(query3);
            
            while(resultadoItem.next()){
                nombre.getItems().add(resultadoItem.getString("nombre"));
                
            }

            int x = ControladorItem.getX();
            int y = ControladorItem.getY();
            int z = ControladorItem.getZ();

            String nombre = x + "/" + y + "/" + z;

            while (resultadoAlmacena.next()) {
                int xB = resultadoAlmacena.getInt("x");
                int yB = resultadoAlmacena.getInt("y");
                int zB = resultadoAlmacena.getInt("z");

                String nombreB = xB + "/" + yB + "/" + zB;

                if (nombre.equals(nombreB)) {
                    id_cofre = resultadoAlmacena.getInt("id_cofre");
                    int idI = resultadoAlmacena.getInt("id_item");
                    ids_items.add(idI);

                }
            }

            while (resultadoCofre.next()) {

                int id_cofreB = resultadoCofre.getInt("id_cofre");

                if (id_cofreB == id_cofre) {

                    capacidadB = resultadoCofre.getInt("capacidad");

                }

            }

            while (resultadoAlmacena.next()) {
                int id_itemB = resultadoAlmacena.getInt("id_item");
                for (int id_item : ids_items) {
                    if (id_itemB == id_item) {

                        int cantidad = resultadoAlmacena.getInt("cantidad");

                        total = total + (cantidad);

                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ControladorAgregarItem.class.getName()).log(Level.SEVERE, null, ex);
        }

        int calculo = capacidadB - total;

        validadores = new ArrayList<>();

        desplegable.getItems().addAll("Objeto", "Armadura", "Herramienta");

        desplegable.getSelectionModel().select("Objeto");
        durabilidad.setDisable(true);

        ValidationSupport vSNombre = new ValidationSupport();
        vSNombre.registerValidator(nombre, Validator.createRegexValidator(
                "El nombre debe tener entre 1 y 50 caracteres", ".{1,50}", Severity.ERROR));

        ValidationSupport vSCantidad = new ValidationSupport();

        ValidationSupport vSStack = new ValidationSupport();

        ValidationSupport vSDurabilidad = new ValidationSupport();

        if (desplegable.getSelectionModel().getSelectedItem().equals("Objeto")) {
            
            if (!entraS) {
                
                vSCantidad.registerValidator(capacidad, Validator.createPredicateValidator(
                        
                        texto -> {
                            
                            try {
                                int capacidadActual = Integer.parseInt((String) texto);
                                int stackValue = Integer.parseInt(stack.getText());
                                int numerin = stackValue * capacidadActual;

                                return (capacidadActual >= 1 && capacidadActual <= 64) && numerin <= calculo;
                            } catch (NumberFormatException e) {
                                
                                return false;
                            }
                        },
                        "La cantidad debe ser un número entero positivo entre 1 y 64 y entrar dentro de la capacidad del cofre."
                ));
                capacidad.textProperty().addListener((observable, oldValue, newValue) -> {
                    vSStack.registerValidator(stack, Validator.createPredicateValidator(
                            texto -> {
                                try {

                                    int numero = Integer.parseInt((String) texto);
                                    int capacidadActual = Integer.parseInt(newValue);
                                    int numerin = numero * capacidadActual;

                                    return (numero > 0 && numero <= 20) && numerin < calculo;
                                } catch (NumberFormatException e) {
                                    return false;
                                }
                            },
                            "El stack debe ser un número entero positivo entre el 1 y el 20 ademas de entrar dentro de la capacidad del cofre"
                    ));
                });
                entraC = true;
            }

            if (!entraS) {
                stack.textProperty().addListener((observable, oldValue, newValue) -> {

                    vSCantidad.registerValidator(capacidad, Validator.createPredicateValidator(
                            texto -> {
                                try {
                                    int capacidadActual = Integer.parseInt((String) texto);
                                    int stackValue = Integer.parseInt(newValue);
                                    int numerin = stackValue * capacidadActual;

                                    return (capacidadActual >= 1 && capacidadActual <= 64) && numerin <= calculo;
                                } catch (NumberFormatException e) {
                                    return false;
                                }
                            },
                            "La cantidad debe ser un número entero positivo entre 1 y 64 y entrar dentro de la capacidad del cofre."
                    ));
                });
                vSStack.registerValidator(stack, Validator.createPredicateValidator(
                        texto -> {
                            try {

                                int numero = Integer.parseInt((String) texto);
                                int capacidadActual = Integer.parseInt(capacidad.getText());
                                int numerin = numero * capacidadActual;

                                return (numero > 0 && numero <= 20) && numerin < calculo;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        },
                        "El stack debe ser un número entero positivo entre el 1 y el 20 ademas de entrar dentro de la capacidad del cofre"
                ));
                entraS = true;
            }

            if (entraC && entraS) {
                stack.textProperty().addListener((observable, oldValue, newValue) -> {

                    vSCantidad.registerValidator(capacidad, Validator.createPredicateValidator(
                            texto -> {
                                try {
                                    int capacidadActual = Integer.parseInt((String) texto);
                                    int stackValue = Integer.parseInt(newValue);
                                    int numerin = stackValue * capacidadActual;

                                    return (capacidadActual >= 1 && capacidadActual <= 64) && numerin <= calculo;
                                } catch (NumberFormatException e) {
                                    
                                    return false;
                                }
                            },
                            "La cantidad debe ser un número entero positivo entre 1 y 64 y entrar dentro de la capacidad del cofre."
                    ));
                });

                capacidad.textProperty().addListener((observable, oldValue, newValue) -> {
                    vSStack.registerValidator(stack, Validator.createPredicateValidator(
                            texto -> {
                                try {

                                    int numero = Integer.parseInt((String) texto);
                                    int capacidadActual = Integer.parseInt(newValue);
                                    int numerin = numero * capacidadActual;

                                    return (numero > 0 && numero <= 20) && numerin < calculo;
                                } catch (NumberFormatException e) {
                                    return false;
                                }
                            },
                            "El stack debe ser un número entero positivo entre el 1 y el 20 ademas de entrar dentro de la capacidad del cofre"
                    ));
                });
            }

            validadores.addAll(Arrays.asList(vSNombre, vSCantidad, vSStack));

            Platform.runLater(() -> {
                validadores.forEach(ValidationSupport::initInitialDecoration);
            });
        }else{
            
            validadores.forEach(validador -> validador.setErrorDecorationEnabled(false));

                validadores.clear();
                vSDurabilidad.registerValidator(durabilidad, Validator.createPredicateValidator(
                        texto -> {
                            try {
                                int numero = Integer.parseInt((String) texto);
                                return numero > 0;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        },
                        "La durabilidad debe ser un número positivo."
                ));

                durabilidad.setDisable(false);
                stack.setDisable(true);
                capacidad.setDisable(true);

                stack.clear();
                capacidad.clear();

                validadores.addAll(Arrays.asList(vSNombre, vSDurabilidad));

                validadores.forEach(validador -> validador.setErrorDecorationEnabled(true));

                Platform.runLater(() -> {
                    validadores.forEach(ValidationSupport::initInitialDecoration);
                });
            
        }

        desplegable.getSelectionModel().selectedItemProperty().addListener((escucha, anterior, nuevo) -> { //Esto lo que hace es leer cual es el item seleccionado en el combobox en todo momento

            durabilidad.setDisable(false);
            if (desplegable.getSelectionModel().getSelectedItem().equals("Objeto")) {
                validadores.forEach(validador -> validador.setErrorDecorationEnabled(false));
                validadores.clear();

                vSCantidad.registerValidator(capacidad, Validator.createPredicateValidator(
                        texto -> {
                            try {
                                int numero = Integer.parseInt((String) texto);
                                return numero >= 1 && numero <= 64;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        },
                        "La cantidad debe ser un número entero positivo entre 1 y 64"
                ));

                vSStack.registerValidator(stack, Validator.createPredicateValidator(
                        texto -> {
                            try {
                                int numero = Integer.parseInt((String) texto);
                                return numero > 0 && numero <= 20;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        },
                        "El stack debe ser un número entero positivo entre el 1 y el 20"
                ));

                stack.setDisable(false);
                capacidad.setDisable(false);

                durabilidad.setDisable(true);
                durabilidad.clear();

                validadores.addAll(Arrays.asList(vSNombre, vSCantidad, vSStack));

                validadores.forEach(validador -> validador.setErrorDecorationEnabled(true));

                Platform.runLater(() -> {
                    validadores.forEach(ValidationSupport::initInitialDecoration);
                });
            } else {
                validadores.forEach(validador -> validador.setErrorDecorationEnabled(false));

                validadores.clear();
                vSDurabilidad.registerValidator(durabilidad, Validator.createPredicateValidator(
                        texto -> {
                            try {
                                int numero = Integer.parseInt((String) texto);
                                return numero > 0;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        },
                        "La durabilidad debe ser un número positivo."
                ));

                durabilidad.setDisable(false);
                stack.setDisable(true);
                capacidad.setDisable(true);

                stack.clear();
                capacidad.clear();

                validadores.addAll(Arrays.asList(vSNombre, vSDurabilidad));

                validadores.forEach(validador -> validador.setErrorDecorationEnabled(true));

                Platform.runLater(() -> {
                    validadores.forEach(ValidationSupport::initInitialDecoration);
                });
            }

        });

    }
    
    private void actualizarFontSize() {
        
        todo.setStyle("-fx-font-size: " + ControladorCofre.Tamaño + "px;");
        
        
    }

}
