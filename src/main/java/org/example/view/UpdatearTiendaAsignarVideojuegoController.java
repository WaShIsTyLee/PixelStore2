package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.Model.Tienda;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdatearTiendaAsignarVideojuegoController extends Controller implements Initializable{
    @FXML
    TextField textFieldLocalizacion;
    @FXML
    TextField textFieldTelefono;
    @FXML
    ListView listViewVideojuegos;
    @FXML
    Button botonAsignar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {
        Tienda tienda = (Tienda) input;
        textFieldLocalizacion.setText(tienda.getUbicacion());
        textFieldTelefono.setText(tienda.getTelefono());

    }

    public void AsignarVideojuego() throws Exception {
        App.currentController.openModalv(Scenes.ASIGNARVIDEOJUEGO,"asignarVideojuego",this,null);
    }
}
