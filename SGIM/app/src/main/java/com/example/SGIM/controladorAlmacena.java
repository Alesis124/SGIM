/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SGIM;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author Alesis
 */
public class controladorAlmacena implements Initializable {

    private boolean entra = true;

    private ControladorItem cI;

    private ControladorCargar cCar;

    private static List<Integer> numeros;

    private static List<Integer> nums;

    private List<String> lista = new ArrayList<>();

    private String coords;

    private List<Integer> loca = new ArrayList<>();

    private List<Integer> ids = new ArrayList<>();

    private List<Integer> envia = new ArrayList<>();

    private List<Integer> rep = new ArrayList<>();

    private ToggleGroup grupo;

    @FXML
    private Button btnAgregar;

    @FXML
    private Button btnAyuda;

    @FXML
    private Button btnCargar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnExportar;
    
    @FXML
    private Button btnFiltro;

    @FXML
    private Button btnZoomIn;

    @FXML
    private Button btnZoomOut;

    @FXML
    private VBox carga;

    @FXML
    private ComboBox<String> desplegable;

    @FXML
    private ScrollPane scroll;
    
    @FXML
    private AnchorPane todo;

    @FXML
    private GridPane grid;

    @FXML
    private VBox menuIzq;

    @FXML
    private VBox vboxGeneral;

    @FXML
    void agregar() throws IOException {

        Parent agrega = FXMLLoader.load(getClass().getResource("/ventanas/agregar_almacena.fxml"));

        Scene escena = new Scene(agrega);

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/imagenes/agrega.png"));
        stage.setTitle("Agregar");
        stage.setScene(escena);
        stage.show();

    }

    @FXML
    void ayuda() throws IOException {

        apartadoAyuda();

    }

    @FXML
    void cargar() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/cargar.fxml"));
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
    void editar() throws IOException, SQLException {
        ControladorEditarAlmacena.delPosicion();
        
        RadioButton selectedRadioButton = (RadioButton) grupo.getSelectedToggle();

        if (selectedRadioButton != null) {
            String nombreAlmacena = selectedRadioButton.getText();

            String[] acorta = nombreAlmacena.split("/");

            int x = Integer.parseInt(acorta[0]);
            int y = Integer.parseInt(acorta[1]);
            int z = Integer.parseInt(acorta[2]);
            ArrayList<Integer> idAlmacena = obtenerPosicionPorNombre(x, y, z);

            Connection connection = BBDD.getConnection();
            String query = "SELECT * FROM Almacena";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                int posicion = rs.getInt("posicion");

                if (idAlmacena.contains(posicion)) {

                    ControladorEditarAlmacena.agregarPosicion(posicion);

                }

            }

            connection.commit();
            connection.close();

            Parent editar = FXMLLoader.load(getClass().getResource("/ventanas/editar_almacena.fxml"));

            Scene escena = new Scene(editar);

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.getIcons().add(new Image("/imagenes/editar.png"));
            stage.setTitle("Editar");
            stage.setScene(escena);
            stage.showAndWait();
            
            if(ControladorEditarAlmacena.getAcepta()){
                actualizarVista();
            }

            

        }

    }

    @FXML
    void eliminar() throws IOException, SQLException {

        Parent eliminar = FXMLLoader.load(getClass().getResource("/ventanas/Eliminar.fxml"));

        Scene escena = new Scene(eliminar);

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/imagenes/eliminar.png"));
        stage.setTitle("Eliminar");
        stage.setScene(escena);
        stage.showAndWait();

        RadioButton selectedRadioButton = (RadioButton) grupo.getSelectedToggle();

        if (ControladorEliminar.getAcepta()) {

            String nombreAlmacena = selectedRadioButton.getText();

            String[] acorta = nombreAlmacena.split("/");

            int x = Integer.parseInt(acorta[0]);
            int y = Integer.parseInt(acorta[1]);
            int z = Integer.parseInt(acorta[2]);

            ArrayList<Integer> idAlmacena = obtenerPosicionPorNombre(x, y, z);

            Connection connection = BBDD.getConnection();
            String query = "SELECT * FROM Almacena";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                int posicion = rs.getInt("posicion");
                int id_cofre = rs.getInt("id_cofre");

                if (idAlmacena.contains(posicion)) {

                    String query2 = "DELETE FROM Almacena WHERE posicion = ?";
                    String queryC = "DELETE FROM Cofre WHERE id_cofre = ?";
                    PreparedStatement statement2 = connection.prepareStatement(query2);
                    PreparedStatement statementC = connection.prepareStatement(queryC);
                    statement2.setInt(1, posicion);
                    statementC.setInt(1, id_cofre);
                    statement2.executeUpdate();
                    statementC.executeUpdate();

                }

            }

            connection.commit();
            connection.close();

            btnEliminar.setDisable(true);
            btnEditar.setDisable(true);
            actualizarVista();

        }

    }

    private ArrayList<Integer> obtenerPosicionPorNombre(int x, int y, int z) {

        ArrayList<Integer> listaIds = new ArrayList<>();
        try {
            Connection connection = BBDD.getConnection();
            String lect = "SELECT * FROM Almacena";
            Statement statement2 = connection.createStatement();
            ResultSet lectura = statement2.executeQuery(lect);
            String query = "SELECT posicion FROM Almacena WHERE x = ? AND y = ? AND z = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, x);
            statement.setInt(2, y);
            statement.setInt(3, z);
            ResultSet rs = statement.executeQuery();

            while (lectura.next()) {

                if (rs.next()) {

                    listaIds.add(rs.getInt("posicion"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ControladorCofre.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaIds;
    }

    @FXML
    void filtro() throws IOException {

        Parent Filtrar = FXMLLoader.load(getClass().getResource("/ventanas/Filtrar.fxml"));

        Scene escena = new Scene(Filtrar);

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/imagenes/filtro.png"));
        stage.setTitle("Filtrar");
        stage.setScene(escena);
        stage.show();

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
    void zoom_in() {
        if(ControladorCofre.Tamaño<20){
            ControladorCofre.Tamaño += 1;
            actualizarFontSize();
        }
        
    }

    @FXML
    void zoom_out() {
        if (ControladorCofre.Tamaño > 8) {
            ControladorCofre.Tamaño -= 1;
            actualizarFontSize();
        }
    }

    public void comprueba(ArrayList<Integer> id) {

        desplegable.setDisable(false);
        btnCargar.setDisable(false);
        btnAgregar.setDisable(true);
        btnExportar.setDisable(false);
        btnZoomIn.setDisable(false);
        btnZoomOut.setDisable(false);
        btnAyuda.setDisable(false);

        ArrayList<String> pasar = new ArrayList<>();
        boolean acaba = false;

        nums = id;

        lista.clear();
        int columnas = 4;
        int columnaActual = 0;
        int filaActual = 0;

        grupo = new ToggleGroup();

        try {

            Connection connection = BBDD.getConnection();
            String query = "SELECT * FROM Almacena";
            Statement statement = connection.createStatement();
            ResultSet resultadoAlmacena = statement.executeQuery(query);

            boolean entrar = false;
            while (resultadoAlmacena.next()) {

                int posicion = resultadoAlmacena.getInt("posicion");
                int id_item = resultadoAlmacena.getInt("id_item");
                int id_cofre = resultadoAlmacena.getInt("id_cofre");
                int x = resultadoAlmacena.getInt("x");
                int y = resultadoAlmacena.getInt("y");
                int z = resultadoAlmacena.getInt("z");

                String nombre = x + "/" + y + "/" + z;

                Button btn = new Button(nombre);

                for (int numero : nums) {

                    entrar = true;
                    if (id_cofre == numero && !lista.contains(nombre)) {

                        lista.add(nombre);

                        ids.add(id_item);
                        loca.add(posicion);

                        System.err.println(nums);
                        lectura(columnaActual, filaActual, grupo, nombre, id_item, posicion, btn, numero);

                        columnaActual++;

                        if (columnaActual == columnas) {
                            columnaActual = 0;
                            filaActual++;
                        }

                    }

                    if (lista.contains(nombre) && entrar) {

                    }

                    btn.setOnAction(e -> {
                        coords = nombre;

                        try {
                            FXMLLoader carga = new FXMLLoader(getClass().getResource("/ventanas/Item.fxml"));
                            Parent toot = carga.load();
                            this.cI = carga.getController();

                            Stage antiguo = (Stage) desplegable.getScene().getWindow();

                            Scene escena = new Scene(toot);

                            antiguo.setResizable(false);
                            antiguo.getIcons().add(new Image("/imagenes/Cofre.png"));
                            antiguo.setTitle("SGIM");
                            antiguo.setScene(escena);
                            antiguo.show();

                            try {

                                Connection connectionItem = BBDD.getConnection();
                                String queryItem = "SELECT * FROM Item";
                                Statement statement2 = connectionItem.createStatement();
                                ResultSet resultadoItem = statement2.executeQuery(queryItem);
                                while (resultadoItem.next()) {

                                    int id_itemI = resultadoItem.getInt("id_item");

                                    for (int pos : loca) {

                                        for (int idI : ids) {

                                            if (id_itemI == idI) {

                                                envia.add(id_itemI);

                                            }

                                        }
                                    }

                                }

                                this.cI.comprueba((ArrayList<Integer>) envia, coords, (ArrayList<Integer>) rep, (ArrayList<Integer>) loca, (ArrayList<String>) lista);

                                connectionItem.close();

                            } catch (SQLException ex) {
                                Logger.getLogger(controladorAlmacena.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } catch (IOException ex) {
                            Logger.getLogger(controladorAlmacena.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });
                }

                grid.getRowConstraints().forEach(row -> {
                    row.setMinHeight(120);
                    row.setPrefHeight(120);
                });

            }
            carga.getChildren().clear();

        } catch (SQLException ex) {
            Logger.getLogger(controladorAlmacena.class.getName()).log(Level.SEVERE, null, ex);
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

    }

    public void vuelta(String rec, ArrayList<Integer> id, ArrayList<Integer> repite, ArrayList<Integer> terr, ArrayList<String> info) throws SQLException {

        desplegable.setDisable(false);
        btnCargar.setDisable(false);
        btnAgregar.setDisable(true);
        btnExportar.setDisable(false);
        btnZoomIn.setDisable(false);
        btnZoomOut.setDisable(false);
        btnAyuda.setDisable(false);

        int columnas = 4;
        int columnaActual = 0;
        int filaActual = 0;
        grupo = new ToggleGroup();

        numeros = id;

        rep = repite;
        ids = id;
        loca = terr;
        ArrayList<String> lista = new ArrayList<>();

        Connection connectionItem = BBDD.getConnection();
        String queryItem = "SELECT * FROM Item";
        Statement statement2 = connectionItem.createStatement();
        ResultSet resultadoItem = statement2.executeQuery(queryItem);

        Connection connection = BBDD.getConnection();
        String query = "SELECT * FROM Almacena";
        Statement statement = connection.createStatement();
        ResultSet resultadoAlmacena = statement.executeQuery(query);

        while (resultadoAlmacena.next()) {

            int posicion = resultadoAlmacena.getInt("posicion");
            int id_item = resultadoAlmacena.getInt("id_item");
            int x = resultadoAlmacena.getInt("x");
            int y = resultadoAlmacena.getInt("y");
            int z = resultadoAlmacena.getInt("z");

            String nombre = x + "/" + y + "/" + z;

            Button btn = new Button(nombre);

            if (loca.size() > 1) {
                for (String name : info) {
                    for (int numero : nums) {
                        if (name.equals(nombre) && !lista.contains(nombre)) {
                            lista.add(nombre);

                            lectura(columnaActual, filaActual, grupo, nombre, id_item, posicion, btn, numero);

                            columnaActual++;

                            if (columnaActual == columnas) {
                                columnaActual = 0;
                                filaActual++;
                            }

                            btn.setOnAction(e -> {
                                coords = nombre;

                                try {
                                    FXMLLoader carga = new FXMLLoader(getClass().getResource("/ventanas/Item.fxml"));
                                    Parent toot = carga.load();
                                    this.cI = carga.getController();

                                    Stage antiguo = (Stage) desplegable.getScene().getWindow();

                                    Scene escena = new Scene(toot);

                                    antiguo.setResizable(false);
                                    antiguo.getIcons().add(new Image("/imagenes/Cofre.png"));
                                    antiguo.setTitle("SGIM");
                                    antiguo.setScene(escena);
                                    antiguo.show();

                                    try {

                                        while (resultadoItem.next()) {

                                            int id_itemI = resultadoItem.getInt("id_item");
                                            

                                            for (int idI : ids) {

                                                if (id_itemI == idI) {

                                                    envia.add(id_itemI);

                                                    

                                                }

                                            }

                                        }

                                        this.cI.comprueba((ArrayList<Integer>) id, coords, (ArrayList<Integer>) repite, (ArrayList<Integer>) loca, (ArrayList<String>) info);

                                    } catch (SQLException ex) {
                                        Logger.getLogger(controladorAlmacena.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                } catch (IOException ex) {
                                    Logger.getLogger(controladorAlmacena.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            });

                        }
                    }
                }
                grid.getRowConstraints().forEach(row -> {
                    row.setMinHeight(120);
                    row.setPrefHeight(120);
                });

            } else {

                for (int numero : nums) {
                    if (nombre.equals(rec) && !lista.contains(nombre)) {
                        lista.add(nombre);

                        lectura(columnaActual, filaActual, grupo, nombre, id_item, posicion, btn, numero);

                        columnaActual++;

                        if (columnaActual == columnas) {
                            columnaActual = 0;
                            filaActual++;
                        }

                        btn.setOnAction(e -> {
                            coords = nombre;

                            try {
                                FXMLLoader carga = new FXMLLoader(getClass().getResource("/ventanas/Item.fxml"));
                                Parent toot = carga.load();
                                this.cI = carga.getController();

                                Stage antiguo = (Stage) desplegable.getScene().getWindow();

                                Scene escena = new Scene(toot);
                                antiguo.setResizable(false);
                                antiguo.getIcons().add(new Image("/imagenes/Cofre.png"));
                                antiguo.setTitle("SGIM");
                                antiguo.setScene(escena);
                                antiguo.show();

                                try {

                                    while (resultadoItem.next()) {

                                        int id_itemI = resultadoItem.getInt("id_item");
                                        

                                        for (int idI : ids) {

                                            if (id_itemI == idI) {

                                                envia.add(id_itemI);

                                                

                                            }

                                        }

                                    }

                                    this.cI.comprueba((ArrayList<Integer>) id, coords, (ArrayList<Integer>) repite, (ArrayList<Integer>) loca, (ArrayList<String>) info);

                                } catch (SQLException ex) {
                                    Logger.getLogger(controladorAlmacena.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            } catch (IOException ex) {
                                Logger.getLogger(controladorAlmacena.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                    }
                }

            }
            carga.getChildren().clear();
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

        }
        connection.close();
        connectionItem.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        actualizarFontSize();
        
        btnFiltro.setDisable(true);
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);
        btnCargar.setDisable(true);
        btnAgregar.setDisable(true);
        btnExportar.setDisable(true);
        btnZoomIn.setDisable(true);
        btnZoomOut.setDisable(true);
        btnAyuda.setDisable(true);
        desplegable.setDisable(true);

        carga.getChildren().add(new Label("Cargando..."));

        carga.setAlignment(Pos.CENTER);

        carga.setMouseTransparent(true);

        btnAgregar.setDisable(true);

        desplegable.getItems().addAll("Cofre", "Almacena");

        desplegable.getSelectionModel().select("Almacena");

        desplegable.sceneProperty().addListener((escucha, anterior, nuevo) -> {
            if (nuevo != null) {
                nuevo.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.F1) {
                        apartadoAyuda();
                    }
                });
            }
        });


        desplegable.getSelectionModel().selectedItemProperty().addListener((escucha, anterior, nuevo) -> { //Esto lo que hace es leer cual es el item seleccionado en el combobox en todo momento

            if (desplegable.getSelectionModel().getSelectedItem() != null && desplegable.getSelectionModel().getSelectedItem().equals("Cofre")) {
                Parent cofre;
                try {
                    cofre = FXMLLoader.load(getClass().getResource("/ventanas/Cofre.fxml"));
                    Stage antiguo = (Stage) desplegable.getScene().getWindow();
                    Scene escena = new Scene(cofre);

                    antiguo.setResizable(false);
                    antiguo.setScene(escena);
                    antiguo.getIcons().add(new Image("/imagenes/Cofre.png"));
                    antiguo.setTitle("SGIM");
                    antiguo.show();

                } catch (IOException ex) {
                    Logger.getLogger(controladorAlmacena.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        });

    }

    public void actualizarVista() {
        grid.getChildren().clear();
        menuIzq.getChildren().clear();
        comprueba((ArrayList<Integer>) nums);
        System.out.println(nums);
        
    }

    public void lectura(int ColA, int FilA, ToggleGroup grupin, String nombre, int id_item, int posicion, Button btn, int numero) throws SQLException {

        Connection connectionC = BBDD.getConnection();
        String queryC = "SELECT * FROM Cofre";
        Statement statementC = connectionC.createStatement();
        ResultSet resultadoCofre = statementC.executeQuery(queryC);

        RadioButton opcion = new RadioButton();
        opcion.setText(nombre);
            
            while (resultadoCofre.next()) {
            
                int idC = resultadoCofre.getInt("id_cofre");
                String name = resultadoCofre.getString("nombre");

                if (numero == idC) {
                    Tooltip tooltip = new Tooltip("Nombre: " + name);
                    Tooltip.install(opcion, tooltip);
                }

            }

            menuIzq.getChildren().add(opcion);
            menuIzq.setAlignment(Pos.TOP_LEFT);
            grupo.getToggles().add(opcion);
            VBox vbox = new VBox(10);
            ImageView ico = new ImageView(new Image(getClass().getResourceAsStream("/imagenes/mapa.png")));

            ico.setFitHeight(55);
            ico.setFitWidth(63);

            btn.setGraphic(ico);

            btn.setContentDisplay(ContentDisplay.TOP);

            vbox.getChildren().add(btn);
            vbox.setAlignment(Pos.CENTER);

            grid.add(vbox, ColA, FilA);
            
        
        

        connectionC.close();
        resultadoCofre.close();

    }
    
    private void actualizarFontSize() {
        
        todo.setStyle("-fx-font-size: " + ControladorCofre.Tamaño + "px;");
        
        
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
        BorderPane root = new BorderPane(webView);
        Scene escena = new Scene(root, 800, 600);
        stage.setScene(escena);
        stage.getIcons().add(new Image("/imagenes/Cofre.png"));
        stage.show();
    }
    

}



