/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SGIM;

import java.io.File;
import java.io.FileWriter;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
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
public class ControladorCofre implements Initializable {

    private List<Integer> numeros;

    private controladorAlmacena cAl;

    private ControladorCargar cCar;

    public static double Tamaño = 12;

    @FXML
    private Button btnAgrega;

    @FXML
    private Button btnBarril;

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnCofre;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnEnder;

    @FXML
    private Button btnExportar;

    @FXML
    private Button btnGrande;
    
    @FXML
    private Button btnInforme;

    @FXML
    private Button btnShulker;

    @FXML
    private ComboBox<String> desplegable;

    @FXML
    private GridPane grid;

    @FXML
    private VBox menuIzq;

    @FXML
    private AnchorPane todo;

    private static VBox Izq;

    @FXML
    void agregar() throws IOException {

        Parent agrega = FXMLLoader.load(getClass().getResource("/ventanas/agregar_cofre.fxml"));

        Scene escena = new Scene(agrega);

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/imagenes/agrega.png"));
        stage.setTitle("SGIM");
        stage.setScene(escena);
        stage.show();

    }

    @FXML
    void ayuda() throws IOException {

        apartadoAyuda();

    }

    @FXML
    void cargar() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/Cargar.fxml"));
        Parent root = loader.load();
        this.cCar = loader.getController();

        Stage antiguo = (Stage) desplegable.getScene().getWindow();
        this.cCar.cierra(antiguo);

        Scene escena = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/imagenes/cargar.png"));
        stage.setTitle("Cargar");
        stage.setScene(escena);
        stage.show();

    }

    @FXML
    void CajaShulker() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/Almacena.fxml"));

        Parent root = loader.load();

        cAl = loader.getController();

        Stage antiguo = (Stage) desplegable.getScene().getWindow();

        Scene escena = new Scene(root);

        antiguo.setResizable(false);
        antiguo.getIcons().add(new Image("/imagenes/Cofre.png"));
        antiguo.setTitle("SGIM");
        antiguo.setScene(escena);
        antiguo.show();

        try {

            Connection connection = BBDD.getConnection();
            String query = "SELECT * FROM Cofre";
            Statement statement = connection.createStatement();
            ResultSet resultadoCofre = statement.executeQuery(query);

            numeros = new ArrayList<>();

            while (resultadoCofre.next()) {
                int id_cofre = resultadoCofre.getInt("id_cofre");
                String tipo = resultadoCofre.getString("tipo");

                if (tipo.equals("Shulker")) {

                    numeros.add(id_cofre);
                }

            }

            this.cAl.comprueba((ArrayList<Integer>) numeros);

        } catch (SQLException ex) {
            System.err.println("Error");
        }

    }

    @FXML
    void CofreBarril() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/Almacena.fxml"));

        Parent root = loader.load();

        cAl = loader.getController();

        Stage antiguo = (Stage) desplegable.getScene().getWindow();

        Scene escena = new Scene(root);

        antiguo.setResizable(false);
        antiguo.getIcons().add(new Image("/imagenes/Cofre.png"));
        antiguo.setTitle("SGIM");
        antiguo.setScene(escena);
        antiguo.show();

        try {

            Connection connection = BBDD.getConnection();
            String query = "SELECT * FROM Cofre";
            Statement statement = connection.createStatement();
            ResultSet resultadoCofre = statement.executeQuery(query);

            numeros = new ArrayList<>();

            while (resultadoCofre.next()) {
                int id_cofre = resultadoCofre.getInt("id_cofre");
                String tipo = resultadoCofre.getString("tipo");

                if (tipo.equals("Barril")) {

                    numeros.add(id_cofre);
                }

            }

            this.cAl.comprueba((ArrayList<Integer>) numeros);

        } catch (SQLException ex) {
            System.err.println("Error");
        }

    }

    @FXML
    void CofreEnder() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/Almacena.fxml"));

        Parent root = loader.load();

        cAl = loader.getController();

        Stage antiguo = (Stage) desplegable.getScene().getWindow();

        Scene escena = new Scene(root);
        antiguo.setResizable(false);
        antiguo.getIcons().add(new Image("/imagenes/Cofre.png"));
        antiguo.setTitle("SGIM");
        antiguo.setScene(escena);
        antiguo.show();

        try {

            Connection connection = BBDD.getConnection();
            String query = "SELECT * FROM Cofre";
            Statement statement = connection.createStatement();
            ResultSet resultadoCofre = statement.executeQuery(query);

            numeros = new ArrayList<>();

            while (resultadoCofre.next()) {
                int id_cofre = resultadoCofre.getInt("id_cofre");
                String tipo = resultadoCofre.getString("tipo");

                if (tipo.equals("Ender")) {

                    numeros.add(id_cofre);

                }

            }

            this.cAl.comprueba((ArrayList<Integer>) numeros);

        } catch (SQLException ex) {
            System.err.println("Error");
        }

    }

    @FXML
    void CofreGrande() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/Almacena.fxml"));

        Parent root = loader.load();

        cAl = loader.getController();

        Stage antiguo = (Stage) desplegable.getScene().getWindow();

        Scene escena = new Scene(root);
        antiguo.setResizable(false);
        antiguo.getIcons().add(new Image("/imagenes/Cofre.png"));
        antiguo.setTitle("SGIM");
        antiguo.setScene(escena);
        antiguo.show();

        try {

            Connection connection = BBDD.getConnection();
            String query = "SELECT * FROM Cofre";
            Statement statement = connection.createStatement();
            ResultSet resultadoCofre = statement.executeQuery(query);

            numeros = new ArrayList<>();

            while (resultadoCofre.next()) {
                int id_cofre = resultadoCofre.getInt("id_cofre");
                String tipo = resultadoCofre.getString("tipo");

                if (tipo.equals("Grande")) {

                    numeros.add(id_cofre);

                }

            }

            this.cAl.comprueba((ArrayList<Integer>) numeros);

        } catch (SQLException ex) {
            System.err.println("Error");
        }

    }

    @FXML
    void CofrePequeño() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/Almacena.fxml"));

        Parent root = loader.load();

        cAl = loader.getController();

        Stage antiguo = (Stage) desplegable.getScene().getWindow();

        Scene escena = new Scene(root);
        antiguo.setResizable(false);
        antiguo.getIcons().add(new Image("/imagenes/Cofre.png"));
        antiguo.setTitle("SGIM");
        antiguo.setScene(escena);
        antiguo.show();

        try {

            Connection connection = BBDD.getConnection();
            String query = "SELECT * FROM Cofre";
            Statement statement = connection.createStatement();
            ResultSet resultadoCofre = statement.executeQuery(query);

            numeros = new ArrayList<>();

            while (resultadoCofre.next()) {
                int id_cofre = resultadoCofre.getInt("id_cofre");
                String tipo = resultadoCofre.getString("tipo");

                if (tipo.equals("Cofre")) {

                    numeros.add(id_cofre);

                }

            }

            this.cAl.comprueba((ArrayList<Integer>) numeros);

        } catch (SQLException ex) {
            System.err.println("Error");
        }

    }

    @FXML
    void editar() throws IOException, SQLException {

        RadioButton selectedRadioButton = (RadioButton) grupo.getSelectedToggle(); //me ayuda a saber cual es el radiobutton seleccionado

        if (selectedRadioButton != null) {
            String nombreCofre = selectedRadioButton.getText();
            int idCofre = obtenerIdCofrePorNombre(nombreCofre);

            ControladorEditarCofre.setId(idCofre);

            Parent editar = FXMLLoader.load(getClass().getResource("/ventanas/editar_cofre.fxml"));

            Scene escena = new Scene(editar);

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.getIcons().add(new Image("/imagenes/editar.png"));

            stage.setScene(escena);
            stage.showAndWait();
            if (ControladorEditarCofre.getAcepta()) {

                menuIzq.getChildren().clear();

                Connection connection = BBDD.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultadoCofre = statement.executeQuery(ControladorFiltrarCofre.getQuery());
                
                while (resultadoCofre.next()) {
                    String nombre = resultadoCofre.getString("nombre");
                    String tipo = resultadoCofre.getString("tipo");

                    RadioButton opcion = new RadioButton();

                    opcion.setText(nombre);

                    grupo.getToggles().add(opcion);

                    Tooltip tooltip = new Tooltip("Tipo: " + tipo);
                    Tooltip.install(opcion, tooltip);

                    menuIzq.getChildren().add(opcion);
                    

                }
                
            }

        }

    }

    @FXML
    void eliminar() throws IOException, SQLException {

        RadioButton selectedRadioButton = (RadioButton) grupo.getSelectedToggle();

        if (selectedRadioButton != null) {
            String nombreCofre = selectedRadioButton.getText();
            int idCofre = obtenerIdCofrePorNombre(nombreCofre);

            Parent eliminar = FXMLLoader.load(getClass().getResource("/ventanas/Eliminar.fxml"));
            Scene escena = new Scene(eliminar);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.getIcons().add(new Image("/imagenes/eliminar.png"));
            stage.setTitle("Eliminar");
            stage.setScene(escena);
            stage.showAndWait();

            if (ControladorEliminar.getAcepta()) {

                eliminarCofre(idCofre);

                menuIzq.getChildren().clear();

                Connection connection2 = BBDD.getConnection();
                String query2 = "SELECT * FROM Cofre";
                Statement statement2 = connection2.createStatement();
                ResultSet resultadoCofre = statement2.executeQuery(query2);

                numeros = new ArrayList<>();

                while (resultadoCofre.next()) {
                    String nombre = resultadoCofre.getString("nombre");

                    RadioButton opcion = new RadioButton();

                    opcion.setText(nombre);

                    grupo.getToggles().add(opcion);

                    menuIzq.getChildren().add(opcion);

                }
                btnEliminar.setDisable(true);
                btnEditar.setDisable(true);
            }
        }
    }

    private int obtenerIdCofrePorNombre(String nombreCofre) {
        int idCofre = -1;
        try {
            Connection connection = BBDD.getConnection();
            String query = "SELECT id_cofre FROM Cofre WHERE nombre = ? LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nombreCofre);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                idCofre = rs.getInt("id_cofre");

            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCofre.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idCofre;
    }

    private void eliminarCofre(int idCofre) {
        try {

            Connection connection = BBDD.getConnection();
            String query = "DELETE FROM Cofre WHERE id_cofre = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, idCofre);
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ControladorCofre.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void filtro() throws IOException, SQLException {

        Parent Filtrar = FXMLLoader.load(getClass().getResource("/ventanas/Filtrar.fxml"));

        Scene escena = new Scene(Filtrar);

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/imagenes/filtro.png"));
        stage.setTitle("Filtrar");
        stage.setScene(escena);
        stage.showAndWait();

        if (ControladorFiltrarCofre.getAcepta()) {

            menuIzq.getChildren().clear();

            Connection connection = BBDD.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultadoCofre = statement.executeQuery(ControladorFiltrarCofre.getQuery());

            while (resultadoCofre.next()) {
                String nombre = resultadoCofre.getString("nombre");
                String tipo = resultadoCofre.getString("tipo");

                RadioButton opcion = new RadioButton();

                opcion.setText(nombre);

                grupo.getToggles().add(opcion);

                Tooltip tooltip = new Tooltip("Tipo: " + tipo);
                Tooltip.install(opcion, tooltip);

                menuIzq.getChildren().add(opcion);

            }

        }

    }

    @FXML
    void guardar() throws SQLException {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccionar Directorio");
        File selectedDirectory = directoryChooser.showDialog(desplegable.getScene().getWindow());
        File file = new File(selectedDirectory.getAbsolutePath() + "/exportacion_BD.sql");

        if (selectedDirectory != null) {
            exportDatabaseToSQLFile(selectedDirectory.getAbsolutePath() + "/exportacion_BD.sql");
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("INFORMACIÓN");
            alerta.setHeaderText("Perfecto!");
            alerta.setContentText("Se ha guardado perfectamente");
            alerta.showAndWait();
        }

        // Ruta fija para guardar el archivo
    }
    
    @FXML
    void informe() {
        
        
        

        Stage antiguo = (Stage) desplegable.getScene().getWindow();
        try {
            Parent item = FXMLLoader.load(getClass().getResource("/ventanas/informe.fxml"));

            Scene escena = new Scene(item);

            antiguo.setResizable(false);
            antiguo.setScene(escena);
            antiguo.getIcons().add(new Image("/imagenes/Cofre.png"));
            antiguo.setTitle("Generador Informes");
            antiguo.show();
        } catch (IOException ex) {
            Logger.getLogger(ControladorCofre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void zoom_in() {
        if (Tamaño < 20) {
            Tamaño += 1;
            actualizarFontSize();
        }

    }

    @FXML
    void zoom_out() {
        if (Tamaño > 8) {
            Tamaño -= 1;
            actualizarFontSize();
        }
    }

    private static ToggleGroup grupo;

    public static ToggleGroup getGrupo() {
        return ControladorCofre.grupo;
    }

    public static VBox getIzq() {

        return ControladorCofre.Izq;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        actualizarFontSize();

        btnAgrega.setDisable(false);
        btnCargar.setDisable(false);
        btnExportar.setDisable(false);
        btnCofre.setDisable(false);
        btnGrande.setDisable(false);
        btnEnder.setDisable(false);
        btnShulker.setDisable(false);
        btnBarril.setDisable(false);

        Izq = menuIzq;

        ControladorCofre.grupo = new ToggleGroup();
        
        desplegable.sceneProperty().addListener((escucha, anterior, nuevo) -> {
            if (nuevo != null) {
                nuevo.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.F1) {
                        apartadoAyuda();
                    }
                });
            }
        });

        try {
            Connection connection = BBDD.getConnection();
            String query = "SELECT * FROM Cofre";
            Statement statement = connection.createStatement();
            ResultSet resultadoCofre = statement.executeQuery(query);

            menuIzq.setAlignment(Pos.TOP_LEFT);
            numeros = new ArrayList<>();

            while (resultadoCofre.next()) {
                String nombre = resultadoCofre.getString("nombre");
                String tipo = resultadoCofre.getString("tipo");

                RadioButton opcion = new RadioButton();

                opcion.setText(nombre);

                grupo.getToggles().add(opcion);

                Tooltip tooltip = new Tooltip("Tipo: " + tipo);
                Tooltip.install(opcion, tooltip);

                menuIzq.getChildren().add(opcion);

            }
        } catch (SQLException ex) {

            Label intento = new Label("Error al cargar");
            System.err.println(ex.getMessage());
            Button refresh = new Button("Recargar");

            menuIzq.getChildren().clear();

            menuIzq.setAlignment(Pos.CENTER);

            menuIzq.getChildren().add(intento);
            menuIzq.getChildren().add(refresh);

            btnAgrega.setDisable(true);
            btnCargar.setDisable(true);
            btnExportar.setDisable(true);
            btnCofre.setDisable(true);
            btnGrande.setDisable(true);
            btnEnder.setDisable(true);
            btnShulker.setDisable(true);
            btnBarril.setDisable(true);

            refresh.setOnAction(eh -> {

                actualizarVista();

            });

        }

        if (grupo.getSelectedToggle() != null) {

            btnEliminar.setDisable(false);
            btnEditar.setDisable(false);

        } else {

            btnEliminar.setDisable(true);
            btnEditar.setDisable(true);

        }

        grupo.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> { //Esto hace que este escuchando en todo momento el togglegroup y saber cada cambio

            if (grupo.getSelectedToggle() != null) {

                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);

            } else {

                btnEliminar.setDisable(true);
                btnEditar.setDisable(true);
            }
        });
        desplegable.getItems().addAll("Cofre");

        desplegable.getSelectionModel().select("Cofre");
    }

    public List<Integer> getNumeros() {
        return numeros;
    }

    public void actualizarVista() {

        menuIzq.getChildren().clear();

        btnAgrega.setDisable(false);
        btnCargar.setDisable(false);
        btnExportar.setDisable(false);
        btnCofre.setDisable(false);
        btnGrande.setDisable(false);
        btnEnder.setDisable(false);
        btnShulker.setDisable(false);
        btnBarril.setDisable(false);

        Izq = menuIzq;

        ControladorCofre.grupo = new ToggleGroup();

        try {
            Connection connection = BBDD.getConnection();
            String query = "SELECT * FROM Cofre";
            Statement statement = connection.createStatement();
            ResultSet resultadoCofre = statement.executeQuery(query);

            menuIzq.setAlignment(Pos.TOP_LEFT);
            numeros = new ArrayList<>();

            while (resultadoCofre.next()) {
                String nombre = resultadoCofre.getString("nombre");
                String tipo = resultadoCofre.getString("tipo");

                RadioButton opcion = new RadioButton();

                opcion.setText(nombre);

                grupo.getToggles().add(opcion);

                Tooltip tooltip = new Tooltip("Tipo: " + tipo);
                Tooltip.install(opcion, tooltip);

                menuIzq.getChildren().add(opcion);

            }

        } catch (SQLException ex) {
            Label intento = new Label("Error al cargar");
            Button refresh = new Button("Recargar");

            menuIzq.getChildren().clear();

            menuIzq.setAlignment(Pos.CENTER);

            menuIzq.getChildren().add(intento);
            menuIzq.getChildren().add(refresh);

            btnAgrega.setDisable(true);
            btnCargar.setDisable(true);
            btnExportar.setDisable(true);
            btnCofre.setDisable(true);
            btnGrande.setDisable(true);
            btnEnder.setDisable(true);
            btnShulker.setDisable(true);
            btnBarril.setDisable(true);

            refresh.setOnAction(eh -> {

                actualizarVista();

            });

        }

        grupo.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> { //Esto hace que este escuchando en todo momento el togglegroup y saber cada cambio

            if (grupo.getSelectedToggle() != null) {

                btnEliminar.setDisable(false);
                btnEditar.setDisable(false);

            } else {

                btnEliminar.setDisable(true);
                btnEditar.setDisable(true);
            }
        });

    }

    private void actualizarFontSize() {

        todo.setStyle("-fx-font-size: " + Tamaño + "px;");

    }

    private void exportDatabaseToSQLFile(String exportFilePath) {

        try (Connection connection = BBDD.getConnection(); Statement statement = connection.createStatement(); FileWriter writer = new FileWriter(exportFilePath)) {

            // Encabezados iniciales
            writer.write("-- phpMyAdmin SQL Dump\n");
            writer.write("-- version 5.2.1\n");
            writer.write("-- https://www.phpmyadmin.net/\n");
            writer.write("--\n");
            writer.write("-- Servidor: localhost\n");
            writer.write("-- Tiempo de generación: " + java.time.LocalDateTime.now() + "\n");
            writer.write("-- Versión del servidor: " + connection.getMetaData().getDatabaseProductVersion() + "\n");
            writer.write("-- Versión de Java: " + System.getProperty("java.version") + "\n\n");
            writer.write("SET SQL_MODE = \"NO_AUTO_VALUE_ON_ZERO\";\n");
            writer.write("START TRANSACTION;\n");
            writer.write("SET time_zone = \"+00:00\";\n\n");
            writer.write("/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;\n");
            writer.write("/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;\n");
            writer.write("/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;\n");
            writer.write("/*!40101 SET NAMES utf8mb4 */;\n\n");

            // Consultar todas las tablas de la base de datos
            String queryTables = "SHOW TABLES";
            ResultSet tablesResult = statement.executeQuery(queryTables);

            while (tablesResult.next()) {
                String tableName = tablesResult.getString(1);

                // Obtener la estructura de la tabla
                ResultSet structureResult = statement.executeQuery("SHOW CREATE TABLE " + tableName);
                if (structureResult.next()) {
                    String createTableSQL = structureResult.getString(2) + ";\n";
                    writer.write("--\n-- Estructura de la tabla `" + tableName + "`\n--\n\n");
                    writer.write("DROP TABLE IF EXISTS `" + tableName + "`;\n");
                    writer.write(createTableSQL + "\n");
                }

                // Exportar los datos de la tabla
                writer.write("--\n-- Volcado de datos de la tabla `" + tableName + "`\n--\n\n");
                ResultSet dataResult = statement.executeQuery("SELECT * FROM " + tableName);
                int columnCount = dataResult.getMetaData().getColumnCount();

                while (dataResult.next()) {
                    StringBuilder row = new StringBuilder("INSERT INTO `" + tableName + "` VALUES (");
                    for (int i = 1; i <= columnCount; i++) {
                        Object value = dataResult.getObject(i);
                        if (value == null) {
                            row.append("NULL");
                        } else if (value instanceof String || value instanceof Date) {
                            row.append("'").append(value.toString().replace("'", "''")).append("'");
                        } else {
                            row.append(value);
                        }
                        if (i < columnCount) {
                            row.append(", ");
                        }
                    }
                    row.append(");\n");
                    writer.write(row.toString());
                }
                writer.write("\n");
            }

            // Finalizar el archivo
            writer.write("/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;\n");
            writer.write("/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;\n");
            writer.write("/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;\n");
            writer.write("COMMIT;\n");

            System.out.println("Exportación completada: " + exportFilePath);

        } catch (Exception ex) {
            System.err.println("Error durante la exportación de la base de datos: " + ex.getMessage());
        }
    }

    public void apartadoAyuda(){
        
        Stage stage = new Stage();
        stage.setTitle("Ayuda");
        
        
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/html/AYUDA.html").toExternalForm()); //para abrir html local
        System.out.println(webView);
        BorderPane root = new BorderPane(webView);
        Scene escena = new Scene(root, 800, 600);
        stage.setScene(escena);
        stage.getIcons().add(new Image("/imagenes/Cofre.png"));
        stage.show();
        
    }
    
}
