package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.App;
import org.example.DAO.TiendaDAO;
import org.example.Model.Tienda;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddShopModalController extends Controller implements Initializable {

    @FXML
    Button butonAñadir;
    @FXML
    TextField telefono;

    @FXML
    TextField direccion;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void onOpen(Object input) throws IOException {
    }

    public Tienda recogerDatos() {
        Tienda tiendaAux = new Tienda();
        if (comprobarDatos()) {
            tiendaAux.setUbicacion(direccion.getText());
            tiendaAux.setTelefono(telefono.getText());
            return tiendaAux;
        }
        return null;
    }

    public boolean comprobarDatos() {
        if (direccion.getText().isEmpty() || telefono.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public void insertarTiendaBD() throws IOException {
        TiendaDAO tdao = new TiendaDAO();
        Tienda tienda = recogerDatos();
        tdao.insert(tienda);

        Stage currentStage = (Stage) butonAñadir.getScene().getWindow();
        currentStage.close();


        App.currentController.changeScene(Scenes.TIENDAS, null);
    }


}
