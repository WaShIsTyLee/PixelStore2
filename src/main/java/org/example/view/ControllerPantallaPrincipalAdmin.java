package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.example.App;
import org.example.Utils.Sesion;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPantallaPrincipalAdmin extends Controller implements Initializable {
    @FXML
    Button botonTienda;
    @FXML
    Button botonDesarrolladores;
    @FXML
    Button botonJuegos;
    @FXML
    Text texto;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void onOpen(Object input) throws IOException {
        if (Sesion.getInstancia().getUsuarioIniciado().isAdministrador()){
            texto.setText("Bienvenido, " + Sesion.getInstancia().getUsuarioIniciado().getNombre() +
                    " has inciado sesion como Administrador");
        }else{
            texto.setText("Bienvenido, " + Sesion.getInstancia().getUsuarioIniciado().getNombre());
        }
    }



    @FXML
    public void GoToShops() throws IOException {
        App.currentController.changeScene(Scenes.SECONDARY, null);
    }
    @FXML
    public void GoToDevs() throws IOException {
        App.currentController.changeScene(Scenes.SECONDARY, null);
    }
    @FXML
    public void GoToGames() throws IOException {
        App.currentController.changeScene(Scenes.SECONDARY, null);
    }
}
