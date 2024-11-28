package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.App;
import org.example.DAO.TiendaDAO;
import org.example.Model.Tienda;
import org.example.Model.Videojuego;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpdatearTiendaAsignarVideojuegoController extends Controller implements Initializable {
    @FXML
    TextField textFieldLocalizacion;
    @FXML
    TextField textFieldTelefono;
    @FXML
    ListView<Videojuego> listViewVideojuegos;
    private ObservableList<Videojuego> games = FXCollections.observableArrayList(); // Inicializar como ObservableList
    @FXML
    Button botonAsignar;
    @FXML
    Button botonEliminar;
    @FXML
    Button botonEliminarJuego;
    @FXML
    Button botonModificar;

    Tienda tiendaseleccionada;
    private final ObservableList<Videojuego> videojuegosSeleccionados = FXCollections.observableArrayList();


    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configuración de la celda de ListView
        listViewVideojuegos.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Videojuego item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Contenedor principal para cada celda
                    HBox container = new HBox();
                    container.setSpacing(10);
                    container.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-color: lightgray;");

                    // Etiquetas de información del videojuego
                    Label nombre = new Label("Nombre: " + item.getNombre());
                    Label precio = new Label("Precio: $" + String.format("%.2f", item.getPrecio()));
                    Label descripcion = new Label("Descripción: " + item.getDescripcion());
                    Label desarrollador = new Label("Desarrollador: " + item.getDesarrollador().getNombre());
                    descripcion.setWrapText(true);

                    // Checkbox para seleccionar el videojuego
                    CheckBox checkbox = new CheckBox();
                    checkbox.setSelected(videojuegosSeleccionados.contains(item)); // Sincroniza con la lista de seleccionados
                    checkbox.setOnAction(event -> {
                        if (checkbox.isSelected()) {
                            if (!videojuegosSeleccionados.contains(item)) {
                                videojuegosSeleccionados.add(item);
                            }
                        } else {
                            videojuegosSeleccionados.remove(item);
                        }
                    });

                    // Contenedor para la información del videojuego
                    VBox infoContainer = new VBox(nombre, precio, descripcion, desarrollador);
                    infoContainer.setSpacing(5);

                    // Agregar checkbox y la información al contenedor principal
                    container.getChildren().addAll(checkbox, infoContainer);

                    // Configurar el gráfico de la celda
                    setGraphic(container);
                }
            }
        });

        // Asignar la lista de videojuegos a la ListView
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
        App.currentController.changeScene(Scenes.TIENDAS, tiendaseleccionada);
    }

    @FXML
    public void eliminarTienda() throws IOException {
        TiendaDAO tdao = new TiendaDAO();
        tdao.delete(tiendaseleccionada);
        App.currentController.changeScene(Scenes.TIENDAS, null);
    }

    public void eliminarJuegos() throws IOException {
        TiendaDAO tdao = new TiendaDAO();
        tdao.eliminarJuegosDeTienda(tiendaseleccionada, obtenerVideojuegosSeleccionados());
        App.currentController.changeScene(Scenes.TIENDAS, null);

    }
}
