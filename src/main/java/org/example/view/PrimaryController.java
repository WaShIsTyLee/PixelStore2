package org.example.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

public class PrimaryController extends Controller implements Initializable {

    @FXML
    private void switchToSecondary() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {

    }
    public void backtoSecondart(Event event) throws IOException {

        App.currentController.changeScene(Scenes.SECONDARY, null);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
