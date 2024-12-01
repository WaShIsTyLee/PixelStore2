package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.App;
import org.example.DAO.MySQL.DesarrolladorDAO;
import org.example.DAO.MySQL.VideojuegoDAO;
import org.example.DAO.SQLite.VideojuegoDAOSqlite;
import org.example.Model.Desarrollador;
import org.example.Model.Videojuego;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddGamesModalController extends Controller implements Initializable {

    @FXML
    TextField nombreJuego;
    @FXML
    TextField precioJuego;
    @FXML
    ComboBox<String> comboBoxDesarrollador;
    @FXML
    TextArea DescripcionJuego;
    @FXML
    Button buttonGuardar;
    @FXML
    Button buttonSeleccionarImagen;
    @FXML
    ImageView imagenJuego;

    // Archivo seleccionado
    private File view;

    // URL de la imagen seleccionada
    private String imageUrl;

    @FXML
    private void chooseView() {
        FileChooser file = new FileChooser();
        file.setTitle("Selecciona el archivo");
        file.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File select = file.showOpenDialog(buttonSeleccionarImagen.getScene().getWindow());
        if (select != null) {
            view = select;

            // Obtener la URL de la imagen seleccionada
            imageUrl = select.getAbsolutePath().toString();
            System.out.println("URL de la imagen seleccionada: " + imageUrl);

            // Cargar la imagen en el ImageView
            Image image = new Image(imageUrl);
            imagenJuego.setImage(image);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cargar los desarrolladores en el ComboBox
        DesarrolladorDAO desarrolladorDAO = new DesarrolladorDAO();
        ArrayList<Desarrollador> desarrolladores = desarrolladorDAO.findAll();
        ArrayList<String> desarrolladoresString = new ArrayList<>();
        for (Desarrollador desarrollador : desarrolladores) {
            desarrolladoresString.add(desarrollador.getNombre());
        }
        ObservableList<String> desarrolladoresList = FXCollections.observableArrayList(desarrolladoresString);
        comboBoxDesarrollador.setItems(desarrolladoresList);
    }

    public Videojuego recogerVideojuego() {
        Videojuego videojuego = new Videojuego();
        DesarrolladorDAO dao = new DesarrolladorDAO();
        if (comprobacionCampos()) {
            Desarrollador desarrolladorSeleccionado = dao.findByName(comboBoxDesarrollador.getValue());
            videojuego.setNombre(nombreJuego.getText());
            videojuego.setPrecio(Float.parseFloat(precioJuego.getText()));
            videojuego.setDescripcion(DescripcionJuego.getText());
            videojuego.setDesarrollador(desarrolladorSeleccionado);
            if (imageUrl != null) {
                videojuego.setRutaImagen(imageUrl);
            }else {
                String rutaImagen = getClass().getResource("/org/example/view/Fotos/Portada.jpg").toExternalForm();
                videojuego.setRutaImagen(rutaImagen); //METER FOTO EN ESPECIFICO
            }


            return videojuego;
        } else {
            AppController.showErrorCampos();
        }
        return null;
    }

    public boolean comprobacionCampos() {
        VideojuegoDAO dao = new VideojuegoDAO();
        ArrayList<String> namesVideojuegos = dao.findAllNames();

        for (String name : namesVideojuegos) {
            if (name.equalsIgnoreCase(nombreJuego.getText())) { // Comparación insensible a mayúsculas
                AppController.showVideojuegoYaExisteEnBBDD();
                return false;
            }
        }

        if (comboBoxDesarrollador.getValue() == null || nombreJuego.getText().isEmpty() ||
                precioJuego.getText().isEmpty() || DescripcionJuego.getText().isEmpty()) {
            AppController.showCamposIncompletos();
            return false;
        }

        return true;
    }

    public void añadiJuegoBD() {
        VideojuegoDAO videojuegoDAO = new VideojuegoDAO();
        VideojuegoDAOSqlite videojuegoDAOSqlite = new VideojuegoDAOSqlite();
        if (comprobacionCampos()) {
            try {
                videojuegoDAO.save(recogerVideojuego());
                videojuegoDAOSqlite.save(recogerVideojuego());

                // Cerrar la ventana actual
                Stage currentStage = (Stage) buttonGuardar.getScene().getWindow();
                currentStage.close();

                // Cambiar la escena a la lista de juegos
                App.currentController.changeScene(Scenes.GAMES, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            AppController.showVerificaCampos();

        }
    }

    @Override
    public void onOpen(Object input) throws IOException {
    }
}
