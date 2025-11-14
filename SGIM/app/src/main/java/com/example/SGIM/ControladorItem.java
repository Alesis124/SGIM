/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SGIM;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author Alesis
 */
public class ControladorItem implements Initializable {

    private List<Integer> ids;

    private List<Integer> env;

    private String lugar;

    private ControladorCargar cCar;

    private List<Integer> repite;

    private List<Integer> deb;

    private List<Integer> terreno;

    private List<String> infor;

    private controladorAlmacena cAl;

    private List<Integer> Lcantidad = new ArrayList<>();

    private List<Integer> Ldurabilidad = new ArrayList<>();

    private List<Integer> LPosi = new ArrayList<>();
    
    private String tipoSelct = "";
    
    private List<Integer> selid = new ArrayList<>();
    
    private boolean eliminaciones=true;

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
    private Button btnZoomIn;

    @FXML
    private Button btnZoomOut;

    @FXML
    private VBox carga;

    @FXML
    private ComboBox<String> desplegable;

    @FXML
    private GridPane grid;

    @FXML
    private VBox menuIzq;

    @FXML
    private AnchorPane todo;
    
    @FXML
    private Button btnFiltro;

    private String mapaL;

    private static int locX;

    private static int locY;

    private static int locZ;

    private static int idCof;

    private static Stage util;

    private static Scene Escen;

    private ToggleGroup grupo;

    private static int columnas;

    private static int columnaActual;

    private static int filaActual;

    private boolean depl = true;

    @FXML
    void agregar() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ventanas/agregar_item.fxml"));
        Parent agrega = loader.load();

        ControladorAgregarItem cAgr = loader.getController();
        cAgr.setControladorItem(this);

        Scene escena = new Scene(agrega);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/imagenes/agrega.png"));
        stage.setTitle("Agregar");
        stage.setScene(escena);
        stage.showAndWait();

        actualizarVista();

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

        List<Integer> posiciones = new ArrayList<>();

        Connection connection = BBDD.getConnection();
        String query = "SELECT * FROM Almacena";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet rs = statement.executeQuery();

        RadioButton selectedRadioButton = (RadioButton) grupo.getSelectedToggle();

        if (selectedRadioButton != null) {
            int num = 0;
            int contador = 0;

            for (Toggle toggle : grupo.getToggles()) {
                RadioButton boton = (RadioButton) toggle;
                contador++;

                if (boton.isSelected()) {

                    num = contador;

                }
            }

            while (rs.next()) {

                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int z = rs.getInt("z");
                int cantidubi = rs.getInt("cantidad");
                int posi = rs.getInt("posicion");
                

                String nombreL = x + "/" + y + "/" + z;

                
                if (lugar.equals(nombreL) && cantidubi != 0 && selid.contains(posi)) {
                    int posicion = rs.getInt("posicion");
                    posiciones.add(posicion);
                }

            }

            

            ControladorEditarItem.setPos(posiciones.get(num - 1));

            Parent editar = FXMLLoader.load(getClass().getResource("/ventanas/editar_item.fxml"));

            Scene escena = new Scene(editar);

            Stage stage = new Stage();
            stage.setResizable(false);
            stage.getIcons().add(new Image("/imagenes/editar.png"));
            stage.setTitle("Editar");
            stage.setScene(escena);
            stage.showAndWait();

            if (ControladorEditarItem.getAcepta()) {
                actualizarVista();
            }

        }

    }

    @FXML
    void eliminar() throws IOException {

        Parent eliminar = FXMLLoader.load(getClass().getResource("/ventanas/Eliminar.fxml"));

        Scene escena = new Scene(eliminar);

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/imagenes/eliminar.png"));
        stage.setTitle("Eliminar");
        stage.setScene(escena);
        stage.showAndWait();

        if (ControladorEliminar.getAcepta()) {

            List<Integer> posiciones = new ArrayList<>();

            try {
                Connection connection = BBDD.getConnection();
                String query = "SELECT * FROM Almacena";
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet rs = statement.executeQuery();

                int num = 0;
                int contador = 0;

                for (Toggle toggle : grupo.getToggles()) {
                    RadioButton boton = (RadioButton) toggle;
                    contador++;

                    if (boton.isSelected()) {

                        num = contador;

                    }
                }

                while (rs.next()) {

                    int x = rs.getInt("x");
                    int y = rs.getInt("y");
                    int z = rs.getInt("z");
                    int posi = rs.getInt("posicion");

                    String nombreL = x + "/" + y + "/" + z;

                    if (lugar.equals(nombreL) && selid.contains(posi)) {

                        posiciones.add(posi);

                    }

                }
                

                String query2 = "DELETE FROM Almacena WHERE posicion = ?";
                PreparedStatement statement2 = connection.prepareStatement(query2);
                if(eliminaciones){
                    statement2.setInt(1, posiciones.get(num));
                }else{
                    statement2.setInt(1, posiciones.get(num-1));
                }
                
                statement2.executeUpdate();

                connection.commit();
                connection.close();
                
                btnEliminar.setDisable(true);
                btnEditar.setDisable(true);

                actualizarVista();
            } catch (SQLException ex) {
                Logger.getLogger(ControladorItem.class.getName()).log(Level.SEVERE, null, ex);
                eliminaciones=true;
            }

        }

    }

    private int obtenerIdItemPorNombre(String nombreItem) {
        int id_item = -1;
        try {
            Connection connection = BBDD.getConnection();
            String query = "SELECT id_item FROM Item WHERE nombre = ? LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nombreItem);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                id_item = rs.getInt("id_item");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorCofre.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id_item;
    }

    @FXML
    void filtro() throws IOException, SQLException {
        Parent filtrar = FXMLLoader.load(getClass().getResource("/ventanas/FiltrarItem.fxml"));

        Scene escena = new Scene(filtrar);

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(new Image("/imagenes/filtro.png"));
        stage.setTitle("Filtrar");
        stage.setScene(escena);
        stage.showAndWait();
        

        if (ControladorFiltrarItem.getAcepta()) {
            
            columnas = 4;
            columnaActual = 0;
            filaActual = 0;

            menuIzq.getChildren().clear();
            grid.getChildren().clear();
            grupo.getToggles().clear();

            Connection connection = BBDD.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {

                String query = ControladorFiltrarItem.getQuery();

                ControladorFiltrarItem.setLoca(getX(), getY(), getZ());

                ps = connection.prepareStatement(query);

                ps.setInt(1, getX());
                ps.setInt(2, getY());
                ps.setInt(3, getZ());

                rs = ps.executeQuery();

                if (rs.next()) {

                    do {

                        String nombre = rs.getString("nombre");
                        int cantidad = rs.getInt("cantidad");
                        String imagen = rs.getString("imagen");
                        tipoSelct = rs.getString("tipo");
                        int durabilidad = rs.getInt("durabilidad");
                        int posi = rs.getInt("posicion");
                        selid.add(posi);

                        byte[] imageBytes = Base64.getDecoder().decode(imagen);

                        ByteArrayInputStream Bytes = new ByteArrayInputStream(imageBytes);
                        Image imagenItem = new Image(Bytes);

                        RadioButton opcion = new RadioButton();
                        opcion.setText(nombre);
                        grupo.getToggles().add(opcion);
                        menuIzq.getChildren().add(opcion);
                        menuIzq.setAlignment(Pos.TOP_LEFT);

                        if (imagen != null && !imagen.isEmpty()) {
                            if (tipoSelct.equals("Objeto")) {

                                VBox vbox = new VBox(10);
                                ImageView ico = new ImageView(imagenItem);

                                ico.setFitHeight(55);
                                ico.setFitWidth(53);

                                String total = "" + cantidad;

                                Label cant = new Label(total);

                                VBox vbox2 = new VBox();
                                VBox vbox3 = new VBox();

                                vbox2.getChildren().add(ico);
                                vbox3.getChildren().add(cant);

                                vbox2.setAlignment(Pos.CENTER);
                                vbox3.setAlignment(Pos.CENTER);

                                vbox.getChildren().addAll(vbox2, vbox3, new Label(nombre));
                                vbox.setAlignment(Pos.CENTER);

                                Tooltip tooltip = new Tooltip("Nombre: " + nombre + "\nTipo: " + tipoSelct + "\nCantidad: " + cantidad);
                                Tooltip.install(vbox, tooltip);

                                grid.add(vbox, columnaActual, filaActual);
                                columnaActual++;

                                if (columnaActual == columnas) {
                                    columnaActual = 0;
                                    filaActual++;
                                }

                            } else {

                                ProgressBar barraProgreso = new ProgressBar();

                                barraProgreso.setPrefWidth(100);
                                barraProgreso.setPrefHeight(10);

                                double progreso = (durabilidad - 0) / (2031.0 - 0.0);

                                barraProgreso.setProgress(progreso);

                                //Cambio de color con css
                                if (progreso > 0.5) {

                                    barraProgreso.setStyle("-fx-accent: green;");
                                } else if (progreso > 0.25) {

                                    barraProgreso.setStyle("-fx-accent: yellow;");
                                } else {

                                    barraProgreso.setStyle("-fx-accent: red;");
                                }

                                VBox vbox = new VBox(10);
                                ImageView ico = new ImageView(imagenItem);

                                ico.setFitHeight(55);
                                ico.setFitWidth(53);

                                vbox.getChildren().addAll(ico, new Label(nombre), barraProgreso);
                                vbox.setAlignment(Pos.CENTER);

                                Tooltip tooltip = new Tooltip("Nombre: " + nombre + "\nTipo: " + tipoSelct + "\nDurabilidad: " + durabilidad);
                                Tooltip.install(vbox, tooltip);

                                grid.add(vbox, columnaActual, filaActual);
                                columnaActual++;

                                if (columnaActual == columnas) {
                                    columnaActual = 0;
                                    filaActual++;
                                }

                            }

                        } else {

                        }

                    } while (rs.next());
                } else {
                    System.out.println("No se encontraron resultados en el ResultSet.");
                }

            } finally {
                // Cerrar recursos
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    connection.close();
                }
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
    void zoom_in() {
        if (ControladorCofre.Tamaño < 20) {
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

    public void comprueba(ArrayList<Integer> id, String coords, ArrayList<Integer> rep, ArrayList<Integer> terr, ArrayList<String> info) {

        
        desplegable.setDisable(false);
        btnCargar.setDisable(false);
        btnAgregar.setDisable(false);
        btnExportar.setDisable(false);
        btnZoomIn.setDisable(false);
        btnZoomOut.setDisable(false);
        btnAyuda.setDisable(false);

        selid.clear();
        this.ids = id; // todos los ids item de 

        infor = info;

        ArrayList<Integer> listaPosicion = new ArrayList<>();

        this.lugar = coords;

        terreno = terr;

        this.repite = new ArrayList<>();

        deb = new ArrayList<>();

        util = (Stage) desplegable.getScene().getWindow();

        Escen = desplegable.getScene();

        grupo = new ToggleGroup();

        columnas = 4;
        columnaActual = 0;
        filaActual = 0;

        env = new ArrayList<>();

        try {
            Connection connection = BBDD.getConnection();
            String query = "SELECT * FROM Item";
            Statement statement = connection.createStatement();
            ResultSet resultadoItem = statement.executeQuery(query);

            Connection connection2 = BBDD.getConnection();
            String query2 = "SELECT * FROM Almacena";
            Statement statement2 = connection2.createStatement();
            ResultSet resultadoAlmacena = statement2.executeQuery(query2);

            while (resultadoAlmacena.next()) {
                int id_item = resultadoAlmacena.getInt("id_item");
                int cantidadA = resultadoAlmacena.getInt("cantidad");
                int durabilidadA = resultadoAlmacena.getInt("durabilidad");

                listaPosicion.add(id_item);
                Lcantidad.add(cantidadA);
                Ldurabilidad.add(durabilidadA);

            }

            resultadoAlmacena.beforeFirst();

            while (resultadoAlmacena.next()) {

                int posicionA = resultadoAlmacena.getInt("posicion");
                int id_item = resultadoAlmacena.getInt("id_item");
                int id_cofre = resultadoAlmacena.getInt("id_cofre");
                int cantidadA = resultadoAlmacena.getInt("cantidad");
                int durabilidadA = resultadoAlmacena.getInt("durabilidad");
                int x = resultadoAlmacena.getInt("x");
                int y = resultadoAlmacena.getInt("y");
                int z = resultadoAlmacena.getInt("z");

                String nombreL = x + "/" + y + "/" + z;

                if (nombreL.equals(coords)) {

                    mapaL = nombreL;
                    locX = x;
                    locY = y;
                    locZ = z;
                    idCof = id_cofre;

                    LPosi.add(posicionA);
                    
                    selid.add(posicionA);

                }

                resultadoItem.beforeFirst();
                while (resultadoItem.next()) {

                    int id_item2 = resultadoItem.getInt("id_item");
                    String nombre = resultadoItem.getString("nombre");
                    String tipo = resultadoItem.getString("tipo");
                    String imagenes = resultadoItem.getString("imagen");

                    if (imagenes != null && !imagenes.isEmpty()) {

                        try {

                            byte[] imageBytes = Base64.getDecoder().decode(imagenes);

                            ByteArrayInputStream Bytes = new ByteArrayInputStream(imageBytes);

                            Image imagenItem = new Image(Bytes);
                            GridPane.setVgrow(grid, Priority.ALWAYS);
                            for (int posi : LPosi) {

                                for (int idI : listaPosicion) {

                                    if ((idI == id_item) && (id_item == id_item2) && !deb.contains(posicionA) && posi == posicionA && !(cantidadA == 0)) {

                                        deb.add(posicionA);

                                        env.add(id_item);

                                        if (tipo.equals("Objeto")) {

                                            RadioButton opcion = new RadioButton();

                                            opcion.setText(nombre);
                                            menuIzq.getChildren().add(opcion);

                                            menuIzq.setAlignment(Pos.TOP_LEFT);
                                            grupo.getToggles().add(opcion);
                                            VBox vbox = new VBox(10);
                                            ImageView ico = new ImageView(imagenItem);

                                            ico.setFitHeight(55);
                                            ico.setFitWidth(53);

                                            String total = "" + cantidadA;

                                            Label cant = new Label(total);

                                            VBox vbox2 = new VBox();
                                            VBox vbox3 = new VBox();

                                            vbox2.getChildren().add(ico);
                                            vbox3.getChildren().add(cant);

                                            vbox2.setAlignment(Pos.CENTER);
                                            vbox3.setAlignment(Pos.CENTER);

                                            vbox.getChildren().addAll(vbox2, vbox3, new Label(nombre));
                                            vbox.setAlignment(Pos.CENTER);

                                            Tooltip tooltip = new Tooltip("Nombre: " + nombre + "\nTipo: " + tipo + "\nCantidad: " + cantidadA);
                                            Tooltip.install(vbox, tooltip);

                                            grid.add(vbox, columnaActual, filaActual);
                                            columnaActual++;

                                            if (columnaActual == columnas) {
                                                columnaActual = 0;
                                                filaActual++;
                                            }

                                        }
                                        if (tipo.equals("Armadura")) {

                                            RadioButton opcion = new RadioButton();

                                            opcion.setText(nombre);
                                            menuIzq.getChildren().add(opcion);

                                            menuIzq.setAlignment(Pos.TOP_LEFT);
                                            grupo.getToggles().add(opcion);

                                            ProgressBar barraProgreso = new ProgressBar();

                                            barraProgreso.setPrefWidth(100);
                                            barraProgreso.setPrefHeight(10);

                                            double progreso = (durabilidadA - 0) / (2031.0 - 0.0);

                                            barraProgreso.setProgress(progreso);

                                            //Cambio de color con css
                                            if (progreso > 0.5) {

                                                barraProgreso.setStyle("-fx-accent: green;");
                                            } else if (progreso > 0.25) {

                                                barraProgreso.setStyle("-fx-accent: yellow;");
                                            } else {

                                                barraProgreso.setStyle("-fx-accent: red;");
                                            }

                                            VBox vbox = new VBox(10);
                                            ImageView ico = new ImageView(imagenItem);

                                            ico.setFitHeight(55);
                                            ico.setFitWidth(53);

                                            vbox.getChildren().addAll(ico, new Label(nombre), barraProgreso);
                                            vbox.setAlignment(Pos.CENTER);

                                            Tooltip tooltip = new Tooltip("Nombre: " + nombre + "\nTipo: " + tipo + "\nDurabilidad: " + durabilidadA);
                                            Tooltip.install(vbox, tooltip);

                                            grid.add(vbox, columnaActual, filaActual);
                                            columnaActual++;

                                            if (columnaActual == columnas) {
                                                columnaActual = 0;
                                                filaActual++;
                                            }

                                        }

                                        if (tipo.equals("Herramienta")) {

                                            RadioButton opcion = new RadioButton();

                                            opcion.setText(nombre);
                                            menuIzq.getChildren().add(opcion);

                                            menuIzq.setAlignment(Pos.TOP_LEFT);
                                            grupo.getToggles().add(opcion);

                                            ProgressBar barraProgreso = new ProgressBar();

                                            barraProgreso.setPrefWidth(100);
                                            barraProgreso.setPrefHeight(10);

                                            double progreso = (durabilidadA - 0) / (2031.0 - 0.0);

                                            barraProgreso.setProgress(progreso);

                                            if (progreso > 0.5) {
                                                barraProgreso.setStyle("-fx-accent: green;");
                                            } else if (progreso > 0.25) {
                                                barraProgreso.setStyle("-fx-accent: yellow;");
                                            } else {
                                                barraProgreso.setStyle("-fx-accent: red;");
                                            }

                                            VBox vbox = new VBox(10);
                                            ImageView ico = new ImageView(imagenItem);

                                            ico.setFitHeight(55);
                                            ico.setFitWidth(53);

                                            vbox.getChildren().addAll(ico, new Label(nombre), barraProgreso);
                                            vbox.setAlignment(Pos.CENTER);

                                            Tooltip tooltip = new Tooltip("Nombre: " + nombre + "\nTipo: " + tipo + "\nDurabilidad: " + durabilidadA);
                                            Tooltip.install(vbox, tooltip);

                                            grid.add(vbox, columnaActual, filaActual);
                                            columnaActual++;

                                            if (columnaActual == columnas) {
                                                columnaActual = 0;
                                                filaActual++;
                                            }

                                        }

                                    }

                                }

                            }

                            grid.getRowConstraints().forEach(row -> {
                                row.setMinHeight(120);
                                row.setPrefHeight(120);
                            });

                            Bytes.close();
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                    } else {
                        for (int posi : LPosi) {
                            for (int idI : listaPosicion) {

                                if (idI == id_item2 && !deb.contains(posicionA) && posi == posicionA && !(cantidadA == 0)) {

                                    deb.add(posicionA);

                                    env.add(id_item2);

                                    if (tipo.equals("Objeto")) {

                                        RadioButton opcion = new RadioButton();

                                        opcion.setText(nombre);
                                        menuIzq.getChildren().add(opcion);

                                        menuIzq.setAlignment(Pos.TOP_LEFT);
                                        grupo.getToggles().add(opcion);
                                        VBox vbox = new VBox(10);
                                        ImageView ico = new ImageView(new Image(getClass().getResourceAsStream("/imagenes/default.png")));

                                        ico.setFitHeight(55);
                                        ico.setFitWidth(53);

                                        String total = "" + cantidadA;

                                        Label cant = new Label(total);

                                        VBox vbox2 = new VBox();
                                        VBox vbox3 = new VBox();

                                        vbox2.getChildren().add(ico);
                                        vbox3.getChildren().add(cant);

                                        vbox2.setAlignment(Pos.CENTER);
                                        vbox3.setAlignment(Pos.CENTER);

                                        vbox.getChildren().addAll(vbox2, vbox3, new Label(nombre));
                                        vbox.setAlignment(Pos.CENTER);

                                        Tooltip tooltip = new Tooltip("Nombre: " + nombre + "\nTipo: " + tipo + "\nCantidad: " + cantidadA);
                                        Tooltip.install(vbox, tooltip);

                                        grid.add(vbox, columnaActual, filaActual);

                                        columnaActual++;

                                        if (columnaActual == columnas) {
                                            columnaActual = 0;
                                            filaActual++;
                                        }
                                    }
                                    if (tipo.equals("Armadura")) {

                                        RadioButton opcion = new RadioButton();

                                        opcion.setText(nombre);
                                        menuIzq.getChildren().add(opcion);

                                        menuIzq.setAlignment(Pos.TOP_LEFT);
                                        grupo.getToggles().add(opcion);

                                        ProgressBar barraProgreso = new ProgressBar();

                                        barraProgreso.setPrefWidth(100);
                                        barraProgreso.setPrefHeight(10);

                                        double progreso = (durabilidadA - 0) / (2031.0 - 0.0);

                                        barraProgreso.setProgress(progreso);

                                        //Cambio de color con css
                                        if (progreso > 0.5) {

                                            barraProgreso.setStyle("-fx-accent: green;");
                                        } else if (progreso > 0.25) {

                                            barraProgreso.setStyle("-fx-accent: yellow;");
                                        } else {

                                            barraProgreso.setStyle("-fx-accent: red;");
                                        }

                                        VBox vbox = new VBox(10);
                                        ImageView ico = new ImageView(new Image(getClass().getResourceAsStream("/imagenes/default.png")));

                                        ico.setFitHeight(55);
                                        ico.setFitWidth(53);

                                        vbox.getChildren().addAll(ico, new Label(nombre), barraProgreso);
                                        vbox.setAlignment(Pos.CENTER);

                                        Tooltip tooltip = new Tooltip("Nombre: " + nombre + "\nTipo: " + tipo + "\nDurabilidad: " + durabilidadA);
                                        Tooltip.install(vbox, tooltip);

                                        grid.add(vbox, columnaActual, filaActual);
                                        columnaActual++;

                                        if (columnaActual == columnas) {
                                            columnaActual = 0;
                                            filaActual++;
                                        }

                                    }

                                    if (tipo.equals("Herramienta")) {

                                        RadioButton opcion = new RadioButton();

                                        opcion.setText(nombre);
                                        menuIzq.getChildren().add(opcion);

                                        menuIzq.setAlignment(Pos.TOP_LEFT);
                                        grupo.getToggles().add(opcion);

                                        ProgressBar barraProgreso = new ProgressBar();

                                        barraProgreso.setPrefWidth(100);
                                        barraProgreso.setPrefHeight(10);

                                        double progreso = (durabilidadA - 0) / (2031.0 - 0.0);

                                        barraProgreso.setProgress(progreso);

                                        if (progreso > 0.5) {
                                            barraProgreso.setStyle("-fx-accent: green;");
                                        } else if (progreso > 0.25) {
                                            barraProgreso.setStyle("-fx-accent: yellow;");
                                        } else {
                                            barraProgreso.setStyle("-fx-accent: red;");
                                        }

                                        VBox vbox = new VBox(10);
                                        ImageView ico = new ImageView(new Image(getClass().getResourceAsStream("/imagenes/default.png")));

                                        ico.setFitHeight(55);
                                        ico.setFitWidth(53);

                                        vbox.getChildren().addAll(ico, new Label(nombre), barraProgreso);
                                        vbox.setAlignment(Pos.CENTER);

                                        Tooltip tooltip = new Tooltip("Nombre: " + nombre + "\nTipo: " + tipo + "\nDurabilidad: " + durabilidadA);
                                        Tooltip.install(vbox, tooltip);

                                        grid.add(vbox, columnaActual, filaActual);
                                        columnaActual++;

                                        if (columnaActual == columnas) {
                                            columnaActual = 0;
                                            filaActual++;
                                        }

                                    }

                                }
                            }

                        }

                        grid.getRowConstraints().forEach(row -> {
                            row.setMinHeight(120);
                            row.setPrefHeight(120);
                        });

                    }

                }
            }

            if (depl) {
                desplegable.getItems().addAll("Cofre", "Almacena", "Items");

                desplegable.getSelectionModel().select("Items");
            }

            Stage antiguo = (Stage) desplegable.getScene().getWindow();

            desplegable.getSelectionModel().selectedItemProperty().addListener((escucha, anterior, nuevo) -> { //Esto lo que hace es leer cual es el item que esta seleccionado en el combobox en todo momento

                if (desplegable.getSelectionModel().getSelectedItem() != null && desplegable.getSelectionModel().getSelectedItem().equals("Cofre")) {

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
                if (desplegable.getSelectionModel().getSelectedItem() != null && desplegable.getSelectionModel().getSelectedItem().equals("Almacena")) {

                    try {
                        FXMLLoader carga = new FXMLLoader(getClass().getResource("/ventanas/Almacena.fxml"));
                        Parent toot = carga.load();
                        this.cAl = carga.getController();

                        Scene escena = new Scene(toot);

                        antiguo.setResizable(false);
                        antiguo.setScene(escena);
                        antiguo.getIcons().add(new Image("/imagenes/Cofre.png"));
                        antiguo.setTitle("SGIM");
                        antiguo.show();

                        this.cAl.vuelta(coords, (ArrayList<Integer>) ids, (ArrayList<Integer>) repite, (ArrayList<Integer>) terr, (ArrayList<String>) info);

                    } catch (IOException ex) {
                        Logger.getLogger(ControladorCofre.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ControladorItem.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            });

        } catch (SQLException ex) {
            Logger.getLogger(ControladorItem.class.getName()).log(Level.SEVERE, null, ex);
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

        carga.getChildren().clear();

    }

    public static int getX() {

        return ControladorItem.locX;

    }

    public static int getY() {

        return ControladorItem.locY;

    }

    public static int getZ() {

        return ControladorItem.locZ;

    }

    public static int getIdCofre() {

        return ControladorItem.idCof;

    }

    public static Stage getStage() {

        return ControladorItem.util;

    }

    public static Scene getScene() {

        return Escen;

    }

    public void actualizarVista() {
        try {

            grid.getChildren().clear();
            menuIzq.getChildren().clear();

            ArrayList<Integer> deb = new ArrayList<>();

            String coords = locX + "/" + locY + "/" + locZ;

            depl = false;
            comprueba((ArrayList<Integer>) ids, coords, (ArrayList<Integer>) repite, (ArrayList<Integer>) terreno, (ArrayList<String>) infor);
        } catch (Exception e) {
            Logger.getLogger(ControladorItem.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        eliminaciones=true;
        actualizarFontSize();
        carga.getChildren().add(new Label("Cargando..."));

        carga.setAlignment(Pos.CENTER);

        carga.setMouseTransparent(true);

        //btnFiltro.setDisable(true);
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);
        btnCargar.setDisable(true);
        btnAgregar.setDisable(true);
        btnExportar.setDisable(true);
        btnZoomIn.setDisable(true);
        btnZoomOut.setDisable(true);
        btnAyuda.setDisable(true);
        desplegable.setDisable(true);
        GridPane.setVgrow(grid, Priority.ALWAYS);
        
        desplegable.sceneProperty().addListener((escucha, anterior, nuevo) -> {
            if (nuevo != null) {
                nuevo.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.F1) {
                        apartadoAyuda();
                    }
                });
            }
        });

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
