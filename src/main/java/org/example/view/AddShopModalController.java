package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.App;
import org.example.DAO.MySQL.TiendaDAO;
import org.example.DAO.SQLite.TiendaDAOSqlite;
import org.example.Model.Tienda;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddShopModalController extends Controller implements Initializable {

    @FXML
    private Button butonAñadir;
    @FXML
    private TextField telefono;

    @FXML
    private TextField direccion;


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
        TiendaDAO tdao = new TiendaDAO();
        ArrayList<Tienda> tiendas = tdao.findAll();
        if (direccion.getText().isEmpty() || telefono.getText().isEmpty()) {
            AppController.showCamposIncompletos();
            return false;
        }
        String nuevaDireccion = direccion.getText();
        String nuevoTelefono = telefono.getText();
        for (Tienda tienda : tiendas) {
            if (tienda.getUbicacion().equalsIgnoreCase(nuevaDireccion) || tienda.getTelefono().equals(nuevoTelefono)) {
                AppController.showTiendaYaExistente();
                return false;
            }
        }

        return true;
    }


    public void insertarTiendaBD() throws IOException {
        TiendaDAO tdao = new TiendaDAO();
        TiendaDAOSqlite tdao2 = new TiendaDAOSqlite();
        Tienda tienda = recogerDatos();
       
        if (tienda != null) {
            tdao.insert(tienda);
            tdao2.insert(tienda);

        } else {
            AppController.showDatosIncorrectos();
            System.out.println("Error: Dirección o teléfono ya existen o los datos son inválidos.");
        }
  

        Stage currentStage = (Stage) butonAñadir.getScene().getWindow();
        currentStage.close();
        App.currentController.changeScene(Scenes.TIENDAS, null);
    }

}
