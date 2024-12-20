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

public class UpdateGameModalController extends Controller implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextArea tfDescripcion;
    @FXML
    private TextField tfPrecio;
    @FXML
    private ComboBox<String> cmbDesarrolador;
    @FXML
    private Button btnGuardar;
    @FXML
    private ImageView imagenGame;
    @FXML
    private Button btnBorrar;
    @FXML
    private Button cambiarImagen;

    private File view;
    private String imageUrl;

    private Videojuego videojuegoCapturado = new Videojuego();

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
        if (videojuegoCapturado.getRutaImagen() != null) {
            Image image = new Image(videojuegoCapturado.getRutaImagen());
            imagenGame.setImage(image);
        }
        DesarrolladorDAO desarrolladorDAO = new DesarrolladorDAO();
        ArrayList<Desarrollador> desarrolladores = desarrolladorDAO.findAll();
        ArrayList<String> nombresDesarroladores = new ArrayList<>();
        for (Desarrollador desarrolador : desarrolladores) {
            nombresDesarroladores.add(desarrolador.getNombre());
        }
        ObservableList<String> string = FXCollections.observableArrayList(nombresDesarroladores);
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
                    AppController.showNombreVideojuegoExistente();
                    return false;
                }
            }
        }
        return true; }

    public boolean validarCampos() {
        if (cmbDesarrolador.getValue() == null || tfNombre.getText().isEmpty() ||
                tfPrecio.getText().isEmpty() || tfDescripcion.getText().isEmpty()) {
           AppController.showCamposIncompletos();
            return false;
        }

        try {
            Float.parseFloat(tfPrecio.getText());
        } catch (NumberFormatException e) {
            AppController.showPrecioValido();
            return false;
        }

        return true;
    }

    public boolean comprobacionCampos() {

        return validarNombreParaUpdate() && validarCampos();
    }

    public Videojuego recogerDatos() {
        if (!comprobacionCampos()) {
            AppController.showVerificaCampos();
            return null;
        }
        Videojuego videojuego = new Videojuego();
        videojuego.setNombre(tfNombre.getText());
        videojuego.setDescripcion(tfDescripcion.getText());
        videojuego.setPrecio(Float.parseFloat(tfPrecio.getText()));
        DesarrolladorDAO desarrolladorDAO = new DesarrolladorDAO();
        videojuego.setDesarrollador(desarrolladorDAO.findByName(cmbDesarrolador.getValue()));
        videojuego.setId_videojuego(videojuegoCapturado.getId_videojuego());

        if (imageUrl != null) {
            videojuego.setRutaImagen(imageUrl);
        } else {
            videojuego.setRutaImagen(videojuegoCapturado.getRutaImagen());
        }
        return videojuego;
    }

    @FXML
    public void modificarJuego2() throws IOException {
        Videojuego videojuegoActualizado = recogerDatos();

        if (videojuegoActualizado == null) {
            AppController.showVerificaCampos();
            return;
        }
        VideojuegoDAO videojuegoDAO = new VideojuegoDAO();
        VideojuegoDAOSqlite videojuegoDAOSqlite = new VideojuegoDAOSqlite();
        videojuegoDAO.update(videojuegoActualizado);
        videojuegoDAOSqlite.update(videojuegoActualizado);
        Stage currentStage = (Stage) btnGuardar.getScene().getWindow();
        currentStage.close();
        App.currentController.changeScene(Scenes.GAMES, null);
    }

    @FXML
    public void borrarJuego() throws IOException {
        VideojuegoDAO videojuegoDAO = new VideojuegoDAO();
        VideojuegoDAOSqlite videojuegoDAOSqlite = new VideojuegoDAOSqlite();
        videojuegoDAO.delete(videojuegoCapturado);
        videojuegoDAOSqlite.delete(videojuegoCapturado);
        Stage currentStage = (Stage) btnBorrar.getScene().getWindow();
        currentStage.close();
        App.currentController.changeScene(Scenes.GAMES, null);
    }

    @FXML
    private void chooseView() {
        FileChooser file = new FileChooser();
        file.setTitle("Selecciona el archivo");
        file.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        File select = file.showOpenDialog(cambiarImagen.getScene().getWindow());
        if (select != null) {
            view = select;
            imageUrl = select.getAbsolutePath().toString();
            System.out.println("URL de la imagen seleccionada: " + imageUrl);
            Image image = new Image(imageUrl);
            imagenGame.setImage(image);
        }
    }
}
