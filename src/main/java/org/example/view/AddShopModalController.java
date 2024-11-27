package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.DAO.TiendaDAO;
import org.example.Model.Tienda;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
        TiendaDAO tdao = new TiendaDAO();
        ArrayList<Tienda> tiendas = tdao.findAll();

        // Validar campos vacíos
        if (direccion.getText().isEmpty() || telefono.getText().isEmpty()) {
            return false;
        }

        // Obtener los valores ingresados
        String nuevaDireccion = direccion.getText();
        String nuevoTelefono = telefono.getText();

        // Verificar si ya existe la dirección o el teléfono
        for (Tienda tienda : tiendas) {
            if (tienda.getUbicacion().equalsIgnoreCase(nuevaDireccion) || tienda.getTelefono().equals(nuevoTelefono)) {
                return false; // Ya existe una tienda con esa dirección o teléfono
            }
        }

        return true; // Los datos son válidos
    }

    public void insertarTiendaBD() {
        TiendaDAO tdao = new TiendaDAO();
        Tienda tienda = recogerDatos();

        if (tienda != null) { // Verificar que los datos son válidos
            tdao.insert(tienda); // Insertar la nueva tienda
        } else {
            System.out.println("Error: Dirección o teléfono ya existen o los datos son inválidos.");
        }
    }

}
