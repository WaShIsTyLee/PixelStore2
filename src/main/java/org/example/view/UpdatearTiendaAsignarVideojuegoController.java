package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.App;
import org.example.DAO.MySQL.TiendaDAO;
import org.example.DAO.SQLite.TiendaDAOSqlite;
import org.example.Model.Tienda;
import org.example.Model.Videojuego;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdatearTiendaAsignarVideojuegoController extends Controller implements Initializable {
    @FXML
    private ImageView volver;
    @FXML
    private TextField textFieldLocalizacion;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    ListView<Videojuego> listViewVideojuegos;
    private ObservableList<Videojuego> games = FXCollections.observableArrayList();
    @FXML
    private Button botonAsignar;
    @FXML
    private Button botonEliminar;
    @FXML
    private Button botonEliminarJuego;
    @FXML
    private Button botonModificar;

    Tienda tiendaseleccionada;
    private final ObservableList<Videojuego> videojuegosSeleccionados = FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle resourceBundle) {
        listViewVideojuegos.setCellFactory(lv -> new ListCell<Videojuego>() {
            @Override
            protected void updateItem(Videojuego item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox container = new HBox();
                    container.setSpacing(10);
                    container.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-color: lightgray;");
                    container.setMaxWidth(Double.MAX_VALUE);
                    VBox labels = new VBox();
                    labels.setSpacing(5);
                    labels.setMaxWidth(Double.MAX_VALUE);
                    labels.setStyle("-fx-hgap: 10;");

                    Label nombre = new Label("Nombre: " + item.getNombre());
                    Label precio = new Label("Precio: $" + String.format("%.2f", item.getPrecio()));
                    Label descripcion = new Label("Descripción: " + item.getDescripcion());
                    Label desarrollador = new Label("Desarrollador: " + item.getDesarrollador().getNombre());
                    descripcion.setWrapText(true);

                    labels.getChildren().addAll(nombre, precio, descripcion, desarrollador);

                    ImageView imagen = new ImageView();
                    if (item.getRutaImagen() != null && !item.getRutaImagen().isEmpty()) {
                        imagen.setImage(new Image(item.getRutaImagen()));
                        imagen.setFitHeight(100);
                        imagen.setPreserveRatio(true);
                    }

                    CheckBox checkbox = new CheckBox();
                    checkbox.setSelected(videojuegosSeleccionados.contains(item));
                    checkbox.setOnAction(event -> {
                        if (checkbox.isSelected()) {
                            if (!videojuegosSeleccionados.contains(item)) {
                                videojuegosSeleccionados.add(item);
                            }
                        } else {
                            videojuegosSeleccionados.remove(item);
                        }
                    });

                    VBox infoContainer = new VBox(labels);
                    infoContainer.setSpacing(5);

                    Region espacioVacío = new Region();
                    HBox.setHgrow(espacioVacío, Priority.ALWAYS);
                    container.getChildren().addAll(checkbox, infoContainer, espacioVacío, imagen);
                    setGraphic(container);
                }
            }
        });
        listViewVideojuegos.setItems(this.games);
    }

    /**
     * Método para obtener los videojuegos seleccionados.
     *
     * @return Lista de videojuegos seleccionados.
     */
    public ArrayList<Videojuego> obtenerVideojuegosSeleccionados() {
        return new ArrayList<>(videojuegosSeleccionados);
    }

    @Override
    public void onOpen(Object input) throws IOException {
        Tienda tienda = (Tienda) input;
        TiendaDAO tdao = new TiendaDAO();
        tienda.setVideojuegos(tdao.buscarJuegosDeTienda(tienda));
        if (tienda.getVideojuegos() == null) {
            tienda.setVideojuegos(new ArrayList<>());
        }
        this.games.setAll(tienda.getVideojuegos());
        tiendaseleccionada = tienda;
        textFieldLocalizacion.setText(tienda.getUbicacion());
        textFieldTelefono.setText(tienda.getTelefono());
    }


    public void AsignarVideojuego() throws Exception {
        App.currentController.openModalv(Scenes.ASIGNARVIDEOJUEGO, "asignarVideojuego", this, tiendaseleccionada);
    }

    public void Modificar() throws Exception {
        modificaTienda();
        App.currentController.changeScene(Scenes.TIENDAS,  tiendaseleccionada);
    }

    @FXML
    public void eliminarTienda() throws IOException {
        TiendaDAO tdao = new TiendaDAO();
        TiendaDAOSqlite tdaoSqlite = new TiendaDAOSqlite();
        tdao.delete(tiendaseleccionada);
        tdaoSqlite.delete(tiendaseleccionada);
        App.currentController.changeScene(Scenes.TIENDAS,null);
    }

    public boolean validarDatos(){
        if (textFieldLocalizacion.getText().isEmpty() || textFieldTelefono.getText().isEmpty()) {
            AppController.showCamposIncompletos();
            return false;
        }
        return true;
    }

    public boolean validarNombreParaUpdate() {
        TiendaDAO tdao = new TiendaDAO();
        ArrayList<String> namesTiendas = tdao.findAllNames();
        String ubicacionIngresada = textFieldLocalizacion.getText();

        for (String ubicacion : namesTiendas) {
            if (ubicacion.equals(ubicacionIngresada)) {
                if (!ubicacion.equalsIgnoreCase(tiendaseleccionada.getUbicacion())){
                    AppController.showUbicacionExistente();
                    return false;
                }
            }
        }
        return true;
    }

    public boolean comprobacionCampos() {
        return validarNombreParaUpdate() && validarDatos();
    }


    public Tienda recogerDatos(){
        if (!comprobacionCampos()) {
            AppController.showVerificaCampos();
            System.out.println("Error: No se puede procesar debido a validaciones fallidas.");
            return null;
        }

        Tienda tienda = new Tienda();
        tienda.setUbicacion(textFieldLocalizacion.getText());
        tienda.setTelefono(textFieldTelefono.getText());

        return tienda;
    }

    @FXML
    public void modificaTienda() throws Exception {
        Tienda tiendaActualizada = recogerDatos();
        tiendaActualizada.setId_tienda(tiendaseleccionada.getId_tienda());
        if (tiendaActualizada == null) {
            AppController.showVerificaCampos();
            return;
        }

        TiendaDAO tdao = new TiendaDAO();
        TiendaDAOSqlite tdaoSqlite = new TiendaDAOSqlite();
        tdao.update(tiendaActualizada);
        tdaoSqlite.insert(tiendaActualizada);

    }

    public void eliminarJuegos() throws IOException {
        TiendaDAO tdao = new TiendaDAO();
        TiendaDAOSqlite tdaoSqlite = new TiendaDAOSqlite();
        tdao.eliminarJuegosDeTienda(tiendaseleccionada, obtenerVideojuegosSeleccionados());
        tdaoSqlite.eliminarJuegosDeTienda(tiendaseleccionada, obtenerVideojuegosSeleccionados());
        App.currentController.changeScene(Scenes.TIENDAS, null);

    }
    @FXML
    public void goToshops() throws IOException {
        App.currentController.changeScene(Scenes.TIENDAS,null);
    }

}
