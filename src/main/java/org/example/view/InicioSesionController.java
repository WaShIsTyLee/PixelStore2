package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import org.example.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InicioSesionController extends Controller implements Initializable {
    @FXML
    ImageView imageFlechaAtras;

    @FXML
    ImageView imageFlechaRegistrar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @FXML
    public void IrAPrimary () throws IOException {
        App.currentController.changeScene(Scenes.PRIMARY,null);
    }

    @FXML
    public void IrARegistrar () throws IOException {
        App.currentController.changeScene(Scenes.REGISTRAR,null);
    }


}
