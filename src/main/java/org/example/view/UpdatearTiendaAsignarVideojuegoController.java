package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.example.Model.Tienda;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdatearTiendaAsignarVideojuegoController extends Controller implements Initializable{
    @FXML
    TextField textFieldLocalizacion;
    @FXML
    TextField textFieldTelefono;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {
        Tienda tienda = (Tienda) input;
        textFieldLocalizacion.setText(tienda.getUbicacion());
        textFieldTelefono.setText(tienda.getTelefono());

    }
}
