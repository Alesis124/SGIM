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
import java.sql.Types;
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
public class ControladorEditarItem implements Initializable{
    
    
    
    @FXML
    private TextField cantidad;

    @FXML
    private ComboBox<String> desplegable;

    @FXML
    private TextField durabilidad;

    @FXML
    private ComboBox<String> nombre;
    
    @FXML
    private VBox todo;

    private static int posicion;
    
    private List<ValidationSupport> validadores;
    
    private List<Integer> ids_items;
    
    private static boolean acepta = false;
    
    @FXML
    void aplicar() throws SQLException {
        
        acepta=true;
        
        boolean validacionCorrecta = validadores.stream().allMatch(v -> v.getValidationResult().getErrors().isEmpty());

        if (!validacionCorrecta) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Corrige los errores antes de aplicar los cambios.");
            alert.showAndWait();
            return;
        }
        
        String nombreSeleccionado = nombre.getSelectionModel().getSelectedItem();
        String tipoSeleccionado = desplegable.getSelectionModel().getSelectedItem();
        
        
        int itemAUsar=0;
        String nombreAUsar = "";
        
        
        Connection connection4 = BBDD.getConnection();
        String query4 = "SELECT tipo FROM Item WHERE nombre = ?";
        PreparedStatement statement6 = connection4.prepareStatement(query4);
        statement6.setString(1, nombreSeleccionado);
        ResultSet rs = statement6.executeQuery();

        if (rs.next()) {
            String tipoItem = rs.getString("tipo");

            if (!tipoItem.equals(tipoSeleccionado)) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "El ítem seleccionado no es compatible con el tipo seleccionado.");
                alert.showAndWait();
                return;
            }
        }
        
        
        Connection connection2 = BBDD.getConnection();
        String query2 = "SELECT * FROM Almacena";
        PreparedStatement statement2 = connection2.prepareStatement(query2);
        ResultSet rs2 = statement2.executeQuery();
        
        
        
        String query3 = "SELECT * FROM Item";
        PreparedStatement statement3 = connection2.prepareStatement(query3);
        ResultSet rs3 = statement3.executeQuery();
        
        while(rs3.next()){
            
            int id_item = rs3.getInt("id_item");
            String nombreI = rs3.getString("nombre");
            
            if (nombreI.equals(nombre.getSelectionModel().getSelectedItem())) {

                nombreAUsar = nombreI;
                itemAUsar = id_item;

            }
            
        }
        
        while(rs2.next()){
            int posi = rs2.getInt("posicion");
            int idIA = rs2.getInt("id_item");
            int x = rs2.getInt("x");
            int y = rs2.getInt("y");
            int z = rs2.getInt("z");
            int id_cofre = rs2.getInt("id_cofre");
            
            
            rs3.beforeFirst();
            while (rs3.next()){
                
                int id_item = rs3.getInt("id_item");
                
                
                String nombreI = rs3.getString("nombre");
                String tipo = rs3.getString("tipo");
                
                
                
                if(id_item==idIA){
                    
                    if(tipo.equals("Objeto")){
                        if(desplegable.getSelectionModel().getSelectedItem().equals("Objeto")){
                            if(posi==posicion){
                                int cant = Integer.parseInt(cantidad.getText());

                                String query = "UPDATE Almacena SET id_cofre = ?, id_item = ?, cantidad = ?, durabilidad = ?, x = ?, y = ?, z = ? WHERE posicion = ?";

                                PreparedStatement statement = connection2.prepareStatement(query);

                                statement.setInt(1, id_cofre);
                                statement.setInt(2, id_item);
                                statement.setInt(3, cant);
                                statement.setNull(4, Types.INTEGER);
                                statement.setInt(5, x);
                                statement.setInt(6, y);
                                statement.setInt(7, z);
                                statement.setInt(8, posi);

                                statement.executeUpdate();
                            }
                        }else{
                            
                            if(posi==posicion){
                                
                                int id_It=0;
                                PreparedStatement statement4 = connection2.prepareStatement(query3);
                                ResultSet rs4 = statement4.executeQuery();
                                
                                
                                while(rs4.next()){
                                    String nom = rs4.getString("nombre");
                                    
                                    if(nom.equals(nombre.getSelectionModel().getSelectedItem())){
                                        
                                        id_It = rs4.getInt("id_item");
                                        
                                    }
                                    
                                }
                                
                                
                                int dura = Integer.parseInt(durabilidad.getText());
                        
                                String query = "UPDATE Almacena SET id_cofre = ?, id_item = ?, cantidad = ?, durabilidad = ?, x = ?, y = ?, z = ? WHERE posicion = ?";

                                PreparedStatement statement = connection2.prepareStatement(query);

                                statement.setInt(1, id_cofre);
                                statement.setInt(2, id_It);
                                statement.setInt(3, 1);
                                statement.setInt(4, dura);
                                statement.setInt(5, x);
                                statement.setInt(6, y);
                                statement.setInt(7, z);
                                statement.setInt(8, posi);

                                statement.executeUpdate();
                            }
                            
                        }
                        
                        
                        
                        
                    }else{
                        
                        if(desplegable.getSelectionModel().getSelectedItem().equals("Armadura")){
                            
                            if(posi==posicion){
                            
                                int dura = Integer.parseInt(durabilidad.getText());

                                String query = "UPDATE Almacena SET id_cofre = ?, id_item = ?, cantidad = ?, durabilidad = ?, x = ?, y = ?, z = ? WHERE posicion = ?";

                                PreparedStatement statement = connection2.prepareStatement(query);

                                statement.setInt(1, id_cofre);
                                statement.setInt(2, id_item);
                                statement.setInt(3, 1);
                                statement.setInt(4, dura);
                                statement.setInt(5, x);
                                statement.setInt(6, y);
                                statement.setInt(7, z);
                                statement.setInt(8, posi);

                                statement.executeUpdate();

                            }
                            
                        }if(desplegable.getSelectionModel().getSelectedItem().equals("Herramienta")){
                            
                            
                            
                            if(posi==posicion){
                                
                                int id_It=0;
                                PreparedStatement statement4 = connection2.prepareStatement(query3);
                                ResultSet rs4 = statement4.executeQuery();
                                
                                while(rs4.next()){
                                    String nom = rs4.getString("nombre");
                                    
                                    if(nom.equals(nombre.getSelectionModel().getSelectedItem())){
                                        
                                        id_It = rs4.getInt("id_item");
                                        
                                    }
                                    
                                }
                            
                                int dura = Integer.parseInt(durabilidad.getText());

                                String query = "UPDATE Almacena SET id_cofre = ?, id_item = ?, cantidad = ?, durabilidad = ?, x = ?, y = ?, z = ? WHERE posicion = ?";

                                PreparedStatement statement = connection2.prepareStatement(query);

                                statement.setInt(1, id_cofre);
                                statement.setInt(2, id_It);
                                statement.setInt(3, 1);
                                statement.setInt(4, dura);
                                statement.setInt(5, x);
                                statement.setInt(6, y);
                                statement.setInt(7, z);
                                statement.setInt(8, posi);

                                statement.executeUpdate();

                            }
                            
                        }
                        if(desplegable.getSelectionModel().getSelectedItem().equals("Objeto")){
                            
                            if(posi==posicion){
                                
                                int id_It=0;
                                PreparedStatement statement4 = connection2.prepareStatement(query3);
                                ResultSet rs4 = statement4.executeQuery();
                                
                                while(rs4.next()){
                                    String nom = rs4.getString("nombre");
                                    
                                    if(nom.equals(nombre.getSelectionModel().getSelectedItem())){
                                        
                                        id_It = rs4.getInt("id_item");
                                        
                                    }
                                    
                                }
                                
                                int cant = Integer.parseInt(cantidad.getText());

                                String query = "UPDATE Almacena SET id_cofre = ?, id_item = ?, cantidad = ?, durabilidad = ?, x = ?, y = ?, z = ? WHERE posicion = ?";

                                PreparedStatement statement = connection2.prepareStatement(query);
                                
                                

                                statement.setInt(1, id_cofre);
                                statement.setInt(2, id_It);
                                statement.setInt(3, cant);
                                statement.setNull(4, Types.INTEGER);
                                statement.setInt(5, x);
                                statement.setInt(6, y);
                                statement.setInt(7, z);
                                statement.setInt(8, posi);

                                statement.executeUpdate();
                            }
                            
                            
                        }
                        
                        
                        
                        
                    }
                    
                }
                
                
                
            }
            
            
            
        }
        
        
        
        
        connection2.commit();
        connection2.close();
        
        Stage stage = (Stage) desplegable.getScene().getWindow();
        stage.close();

    }
    
    

    @FXML
    void cancelar() {
        
        Stage stage = (Stage) desplegable.getScene().getWindow();
        stage.close();

    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if (!nombre.getItems().isEmpty()) {
            nombre.getItems().clear();
        }    
        
        actualizarFontSize();
        
        int id_cofre = 0;
        int capacidadB=0;
        ids_items=new ArrayList<>();
        int total =0;
        
        
        
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
            
            Connection connection3 = BBDD.getConnection();
            String query3 = "SELECT * FROM Item";
            Statement statement3 = connection3.createStatement();
            ResultSet resultadoItem = statement3.executeQuery(query3);

            int x =ControladorItem.getX();
            int y =ControladorItem.getY();
            int z =ControladorItem.getZ();
            
            String nombre = x+"/"+y+"/"+z;
            
            
            while(resultadoAlmacena.next()){
                int xB = resultadoAlmacena.getInt("x");
                int yB = resultadoAlmacena.getInt("y");
                int zB = resultadoAlmacena.getInt("z");
                
                String nombreB = xB+"/"+yB+"/"+zB;
                
                
                if(nombre.equals(nombreB)){
                    id_cofre=resultadoAlmacena.getInt("id_cofre");
                    int idI = resultadoAlmacena.getInt("id_item");
                    int cantidad = resultadoAlmacena.getInt("cantidad");
                    ids_items.add(idI);
                    
                    total= total+(cantidad);
                    
                }
            }
            
            while(resultadoCofre.next()){
                
                int id_cofreB = resultadoCofre.getInt("id_cofre");
                
                
                
                    
                    if(id_cofreB==id_cofre){
                    
                        capacidadB = resultadoCofre.getInt("capacidad");
                        
                        
                    
                    }
                
                
                
                
            }
            
            
            
            
           
            
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ControladorAgregarItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        int calculo = capacidadB-total;
        
        
        
        validadores = new ArrayList<>();
        
        
        
        desplegable.getItems().addAll("Objeto", "Armadura", "Herramienta");
        
        ValidationSupport vSNombre = new ValidationSupport();
        vSNombre.registerValidator(nombre, Validator.createRegexValidator(
            "El nombre debe tener entre 1 y 50 caracteres", ".{1,50}", Severity.ERROR));
        
        ValidationSupport vSCantidad = new ValidationSupport();
        
        ValidationSupport vSDurabilidad = new ValidationSupport();
        
        
        
        
        desplegable.getSelectionModel().selectedItemProperty().addListener((escucha, anterior, nuevo) -> { //Esto lo que hace es leer cual es el item seleccionado en el combobox en todo momento
            
            durabilidad.setDisable(false);
            if(desplegable.getSelectionModel().getSelectedItem() != null && desplegable.getSelectionModel().getSelectedItem().equals("Objeto")){
             validadores.forEach(validador -> validador.setErrorDecorationEnabled(false));
                validadores.clear();
                
                
                    vSCantidad.registerValidator(cantidad, Validator.createPredicateValidator(
                        texto -> {
                            try {
                                int capacidadActual = Integer.parseInt((String) texto);
                                int numerin = capacidadActual;

                                return (capacidadActual >= 1 && capacidadActual <= 64) && numerin <= calculo;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        },
                        "La cantidad debe ser un número entero positivo entre 1 y 64 y entrar dentro de la capacidad del cofre."
                    ));

                
                
            
                
                
                cantidad.setDisable(false);
                
                durabilidad.setDisable(true);
                durabilidad.clear();
                
                validadores.addAll(Arrays.asList(vSNombre, vSCantidad));
                
                validadores.forEach(validador -> validador.setErrorDecorationEnabled(true));
                
                Platform.runLater(() -> {
                    validadores.forEach(ValidationSupport::initInitialDecoration);
                });
            }else{
                
                validadores.forEach(validador -> validador.setErrorDecorationEnabled(false));
                
                validadores.clear();
                vSDurabilidad.registerValidator(durabilidad, Validator.createPredicateValidator(
                    texto -> {
                        try {
                            int numero = Integer.parseInt((String)texto);
                            return numero > 0;
                        } catch (NumberFormatException e) {
                            return false;
                        }
                    },
                    "La durabilidad debe ser un número positivo."
                ));
                
                durabilidad.setDisable(false);
                cantidad.setDisable(true);
                
                cantidad.clear();
                
                validadores.addAll(Arrays.asList(vSNombre, vSDurabilidad));
                
                validadores.forEach(validador -> validador.setErrorDecorationEnabled(true));
                
                Platform.runLater(() -> {
                    validadores.forEach(ValidationSupport::initInitialDecoration);
                });
                
                
            }
            
        });
        
        
        
        try {
            Connection connection2 = BBDD.getConnection();
            String query = "SELECT * FROM Almacena";
            Statement statement = connection2.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            
            String query3 = "SELECT * FROM Item";
            Statement statement3 = connection2.createStatement();
            ResultSet rs3 = statement3.executeQuery(query3);
            
            
            int idItem;
            String nombreI="";
            String tipo="";
            int stackI=1;
            
            while (rs.next()) {
                int posi = rs.getInt("posicion");
                int id_C = rs.getInt("id_cofre");
                int id_I = rs.getInt("id_item");
                int cantidadI = rs.getInt("cantidad");
                int dur = rs.getInt("durabilidad");
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                int z = rs.getInt("z");
                
                
                
                
                
                
                if(ControladorEditarItem.posicion==posi){
                    
                    while (rs3.next()) {
                        idItem = rs3.getInt("id_item");
                        nombre.getItems().add(rs3.getString("nombre"));
                        
                        if(id_I==idItem){
                            nombreI = rs3.getString("nombre");
                            
                            tipo = rs3.getString("tipo");
                        }
                        
                        
                        
                        
                        
                    }
                    
                        
                        
                        if(tipo.equals("Objeto")){
                        
                            
                            nombre.getSelectionModel().select(nombreI);
                            
                            

                            cantidad.setText(""+cantidadI);


                            desplegable.getSelectionModel().select(tipo);




                        }else{

                            nombre.getItems().add(nombreI);

                            durabilidad.setText(""+dur);

                            desplegable.getSelectionModel().select(tipo);



                        }
                    
                        
                        
                    
                    
                    
                    
                    
                    
                }
                
                
            }
            connection2.close();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorEditarCofre.class.getName()).log(Level.SEVERE, null, ex);
        }
        


    }
    
    public static void setPos(int pos){
        
        
        
        ControladorEditarItem.posicion=pos;
        
    }
    
    private void actualizarFontSize() {
        
        todo.setStyle("-fx-font-size: " + ControladorCofre.Tamaño + "px;");
        
        
    }
    
    public static boolean getAcepta(){
        return acepta;
    }
    
}
