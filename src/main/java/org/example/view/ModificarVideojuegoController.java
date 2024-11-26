package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.App;
import org.example.DAO.DesarrolladorDAO;
import org.example.DAO.VideojuegoDAO;
import org.example.Model.Desarrollador;
import org.example.Model.Videojuego;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observer;
import java.util.ResourceBundle;

public class ModificarVideojuegoController extends Controller implements Initializable{

    @FXML
    TextField tfNombre;
    @FXML
    TextArea tfDescripcion;
    @FXML
    TextField tfPrecio;
    @FXML
    ComboBox cmbDesarrolador;
    @FXML
    Button btnGuardar;

    @FXML
    Button btnBorrar;


    Videojuego videojuegoCapturado = new Videojuego();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {
       videojuegoCapturado = (Videojuego) input;
        tfNombre.setText(videojuegoCapturado.getNombre());
        tfDescripcion.setText(videojuegoCapturado.getDescripcion());
        Float precio = videojuegoCapturado.getPrecio();
        tfPrecio.setText(precio.toString());

        // Obtén la lista de desarrolladores y conviértela en un ObservableList
        DesarrolladorDAO desarrolladorDAO = new DesarrolladorDAO();
        ArrayList <Desarrollador> desarrolladores = desarrolladorDAO.findAll();
        ArrayList<String> nombresDesarroladores = new ArrayList<>();
        for(Desarrollador desarrolador : desarrolladores){
            nombresDesarroladores.add(desarrolador.getNombre());
        }
        ObservableList <String> string = FXCollections.observableArrayList(nombresDesarroladores);
        // Establece los elementos del ComboBox
        cmbDesarrolador.setValue(videojuegoCapturado.getDesarrollador().getNombre());
        cmbDesarrolador.setItems(string);

    }
    public boolean validarNombreParaUpdate() {
        VideojuegoDAO dao = new VideojuegoDAO();
        ArrayList<String> namesVideojuegos = dao.findAllNames();
        String nombreIngresado = tfNombre.getText();

        for (String name : namesVideojuegos) {
            if (name.equalsIgnoreCase(nombreIngresado)) {
                if (!name.equalsIgnoreCase(videojuegoCapturado.getNombre())) {
                    // ALERTA: El nombre ya pertenece a otro videojuego
                    System.out.println("Error: El nombre ingresado ya existe para otro videojuego.");
                    return false;
                }
            }
        }
        return true; // Nombre válido (es el mismo que tenía o no existe en la base)
    }

    public boolean validarCampos() {
        if (cmbDesarrolador.getValue() == null || tfNombre.getText().isEmpty() ||
                tfPrecio.getText().isEmpty() || tfDescripcion.getText().isEmpty()) {
            // ALERTA: Campos vacíos
            System.out.println("Error: Hay campos vacíos que deben ser completados.");
            return false;
        }

        try {
            Float.parseFloat(tfPrecio.getText());
        } catch (NumberFormatException e) {
            System.out.println("Error: El precio debe ser un número válido.");
            return false;
        }

        return true;
    }

    public boolean comprobacionCampos() {
        // Combina ambas validaciones
        return validarNombreParaUpdate() && validarCampos();
    }

    public Videojuego recogerDatos() {
        if (!comprobacionCampos()) {
            // Si la validación falla, no se recoge ni actualiza
            System.out.println("Error: No se puede procesar debido a validaciones fallidas.");
            return null;
        }

        // Recoge los datos para la actualización
        Videojuego videojuego = new Videojuego();
        videojuego.setNombre(tfNombre.getText());
        videojuego.setDescripcion(tfDescripcion.getText());
        videojuego.setPrecio(Float.parseFloat(tfPrecio.getText()));
        videojuego.setDesarrollador(videojuegoCapturado.getDesarrollador());
        videojuego.setId_videojuego(videojuegoCapturado.getId_videojuego());
        return videojuego;
    }

    @FXML
    public void modificarJuego2() throws IOException {
        // Validar y recoger los datos
        Videojuego videojuegoActualizado = recogerDatos();

        if (videojuegoActualizado == null) {
            // Si la validación falla, detener el proceso
            System.out.println("Error: No se puede modificar el videojuego debido a errores de validación.");
            return;
        }

        // Realizar el update en la base de datos
        VideojuegoDAO videojuegoDAO = new VideojuegoDAO();
        videojuegoDAO.update(videojuegoActualizado);
        Stage currentStage = (Stage) btnGuardar.getScene().getWindow();
        currentStage.close();
        App.currentController.changeScene(Scenes.GAMES,null);


    }

    @FXML
    public void borrarJuego() throws IOException {
        VideojuegoDAO videojuegoDAO = new VideojuegoDAO();
        videojuegoDAO.delete(videojuegoCapturado);
        Stage currentStage = (Stage) btnBorrar.getScene().getWindow();
        currentStage.close();
        App.currentController.changeScene(Scenes.GAMES,null);
    }

}
