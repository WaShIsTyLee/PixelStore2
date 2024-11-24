package org.example.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import org.example.App;

public class PrimaryController extends Controller implements Initializable {

    @FXML
    Button button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {

    }
    @FXML
    public void GoLogin() throws IOException {

        App.currentController.changeScene(Scenes.INICIOSESION, null);
    }
    @FXML
    public void IrARegistrar() throws IOException {
        App.currentController.changeScene(Scenes.REGISTRAR,null);

    }

}
