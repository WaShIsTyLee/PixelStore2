package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.App;
import org.example.DAO.DesarrolladorDAO;
import org.example.DAO.VideojuegoDAO;
import org.example.Model.Desarrollador;
import org.example.Model.Videojuego;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static org.example.App.stage;

public class ControllerSaveGames extends Controller implements Initializable {

    @FXML
    TextField nombreJuego;
    @FXML
    TextField precioJuego;
    @FXML
    ComboBox<String> comboBoxDesarrollador;
    @FXML
    TextArea DescripcionJuego;
    @FXML
    Button buttonGuardar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DesarrolladorDAO desarrolladorDAO = new DesarrolladorDAO();
        ArrayList<Desarrollador> desarrolladores = desarrolladorDAO.findAll();
        ArrayList<String> desarrolladoresString = new ArrayList<>();
        for (Desarrollador desarrollador : desarrolladores) {
            desarrolladoresString.add(desarrollador.getNombre());
        }
        ObservableList<String> desarrolladoresList = FXCollections.observableArrayList(desarrolladoresString);
        comboBoxDesarrollador.setItems(desarrolladoresList);
    }

    public Videojuego recogerVideojuego() {
        Videojuego videojuego = new Videojuego();
        DesarrolladorDAO dao = new DesarrolladorDAO();
        if (comprobacionCampos()) {
            Desarrollador dearroladorSeleccionado = dao.findByName(comboBoxDesarrollador.getValue());
            videojuego.setNombre(nombreJuego.getText());
            videojuego.setPrecio(Float.parseFloat(precioJuego.getText()));
            videojuego.setDescripcion(DescripcionJuego.getText());
            videojuego.setDesarrollador(dearroladorSeleccionado);
            return videojuego;
        }else{
            System.out.println("Error en los campos");
        }
        return null;
    }

    public boolean comprobacionCampos() {

        VideojuegoDAO dao = new VideojuegoDAO();
        ArrayList<String> namesVideojuegos = dao.findAllNames();

        for (String name : namesVideojuegos) {
            if (name.equalsIgnoreCase(nombreJuego.getText())) { // Comparación insensible a mayúsculas
                // ALERTA: El nombre del juego ya existe
                System.out.println("Error: El nombre del videojuego ya existe.");
                return false;
            }
        }

        if (comboBoxDesarrollador.getValue() == null || nombreJuego.getText().isEmpty() ||
                precioJuego.getText().isEmpty() || DescripcionJuego.getText().isEmpty()) {
            // ALERTA: Campos vacíos
            System.out.println("Error: Hay campos vacíos que deben ser completados.");
            return false;
        }

        return true;
    }


    public void añadiJuegoBD() {
        VideojuegoDAO videojuegoDAO = new VideojuegoDAO();
        if (comprobacionCampos()) {
            try {
                videojuegoDAO.save(recogerVideojuego());

                Stage currentStage = (Stage) buttonGuardar.getScene().getWindow();
                currentStage.close();


                App.currentController.changeScene(Scenes.GAMES, null);
            } catch (Exception e) {
                //mostrarAlerta("Error al guardar el videojuego: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("error");
            //mostrarAlerta("Por favor, verifica los campos. Hay errores o información incompleta.");
        }
    }


    @Override
    public void onOpen(Object input) throws IOException {
    }
}
