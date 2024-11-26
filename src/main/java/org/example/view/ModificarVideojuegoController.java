package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.DAO.DesarrolladorDAO;
import org.example.DAO.VideojuegoDAO;
import org.example.Model.Desarrollador;
import org.example.Model.Videojuego;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observer;
import java.util.ResourceBundle;

public class ModificarVideojuegoController extends Controller implements Initializable{

    @FXML
    TextField tfNombre;
    @FXML
    TextArea tfDescripcion;
    @FXML
    TextField tfPrecio;
    @FXML
    ComboBox cmbDesarrolador;
    @FXML
    Button btnGuardar;

    Videojuego videojuegoCapturado = new Videojuego();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {
       videojuegoCapturado = (Videojuego) input;
        tfNombre.setText(videojuegoCapturado.getNombre());
        tfDescripcion.setText(videojuegoCapturado.getDescripcion());
        Float precio = videojuegoCapturado.getPrecio();
        tfPrecio.setText(precio.toString());

        // Obtén la lista de desarrolladores y conviértela en un ObservableList
        DesarrolladorDAO desarrolladorDAO = new DesarrolladorDAO();
        ArrayList <Desarrollador> desarrolladores = desarrolladorDAO.findAll();
        ArrayList<String> nombresDesarroladores = new ArrayList<>();
        for(Desarrollador desarrolador : desarrolladores){
            nombresDesarroladores.add(desarrolador.getNombre());
        }
        ObservableList <String> string = FXCollections.observableArrayList(nombresDesarroladores);
        // Establece los elementos del ComboBox
        cmbDesarrolador.setValue(videojuegoCapturado.getDesarrollador().getNombre());
        cmbDesarrolador.setItems(string);

    }

    public boolean comprobacionCampos() {

        VideojuegoDAO dao = new VideojuegoDAO();
        ArrayList<String> namesVideojuegos = dao.findAllNames();

        for (String name : namesVideojuegos) {
            if (name.equalsIgnoreCase(tfNombre.getText())) { // Comparación insensible a mayúsculas
                // ALERTA: El nombre del juego ya existe
                System.out.println("Error: El nombre del videojuego ya existe.");
                return false;
            }else {

            }
        }

        if (cmbDesarrolador.getValue() == null || tfNombre.getText().isEmpty() ||
                tfPrecio.getText().isEmpty() || tfDescripcion.getText().isEmpty()) {
            // ALERTA: Campos vacíos
            System.out.println("Error: Hay campos vacíos que deben ser completados.");
            return false;
        }

        return true;
    }
    public void recogerDatos(){
        Videojuego videojuego = new Videojuego();
        videojuego.setNombre(tfNombre.getText());
        videojuego.setDescripcion(tfDescripcion.getText());
        videojuego.setPrecio(Float.parseFloat(tfPrecio.getText()));
        videojuego.setDesarrollador(videojuegoCapturado.getDesarrollador());
        modificarJuego(videojuego);
    }
    @FXML
    public void modificarJuego(Videojuego videojuego) {
        VideojuegoDAO videojuegoDAO = new VideojuegoDAO();
        if (comprobacionCampos()) {
            videojuegoDAO.update(videojuego);
            //alerta videojuego modificado
        }
    }
}
