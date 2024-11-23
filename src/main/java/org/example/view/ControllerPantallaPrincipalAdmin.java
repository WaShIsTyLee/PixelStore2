package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.example.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPantallaPrincipalAdmin extends Controller implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void onOpen(Object input) throws IOException {
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
