package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import org.example.App;
import org.example.Utils.Sesion;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PantallaPrincipalUserController extends Controller implements Initializable {
    @FXML
    private Text nombreUsuarioIniciado;
    @FXML
    private ImageView logOut;
    @FXML
    private ImageView carrito;
    @FXML
    private StackPane desarrolladores;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {
        nombreUsuarioIniciado.setText(Sesion.getInstancia().getUsuarioIniciado().getNombre() + "  🙂");
    }

    @FXML
    public void logOut() throws IOException {
        Sesion.getInstancia().logOut();
        App.currentController.changeScene(Scenes.PRIMARY,null);
    }
    @FXML
    public void goShowDev() throws IOException {
        App.currentController.changeScene(Scenes.SHOWDESARROLLADOR, null);
    }
    @FXML
    public void goShowGames() throws IOException {
        App.currentController.changeScene(Scenes.VIDEOJUEGOSUSUARIO, null);
    }
    @FXML
    public void goShowShop() throws IOException {
        App.currentController.changeScene(Scenes.TIENDAUSER, null);
    }
    @FXML
    public void goCarrito() throws IOException {
        App.currentController.changeScene(Scenes.SHOWKART, null);
    }
}
