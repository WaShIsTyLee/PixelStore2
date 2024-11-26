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
import org.example.Model.Desarrollador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerSaveGames extends Controller implements Initializable {

    @FXML
    TextField nombreJuego;
    @FXML
    TextField precioJuego;
    @FXML
    ComboBox <String> comboBoxDesarrollador;
    @FXML
    TextArea DescripcionJuego;
    @FXML
    Button buttonGuardar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DesarrolladorDAO desarrolladorDAO = new DesarrolladorDAO ();
        ArrayList<Desarrollador> desarrolladores = desarrolladorDAO.findAll();
        ArrayList<String> desarrolladoresString = new ArrayList<>();
        for(Desarrollador desarrollador : desarrolladores) {
            desarrolladoresString.add(desarrollador.getNombre());
        }
        ObservableList<String> desarrolladoresList = FXCollections.observableArrayList(desarrolladoresString);
        comboBoxDesarrollador.setItems(desarrolladoresList);
    }

    @Override
    public void onOpen(Object input) throws IOException {
    }
}