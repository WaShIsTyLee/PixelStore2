package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.example.App;
import org.example.DAO.MySQL.TiendaDAO;
import org.example.Model.Tienda;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShopMenuController extends Controller implements Initializable {

    @FXML
    private ImageView atras; // Botón para regresar

    @FXML
    ListView<Tienda> tiendas; // ListView para mostrar las tiendas
    @FXML
    private TextField buscador; // Campo de búsqueda para filtrar las tiendas
    @FXML
    ImageView anadirTienda;


    private ObservableList<Tienda> tiendasOA; // Lista observable de tiendas

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar la fábrica de celdas para personalizar la visualización
        tiendas.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Tienda item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Crear un diseño para cada tienda
                    VBox container = new VBox();
                    container.setSpacing(5);
                    container.setStyle("-fx-padding: 10; -fx-border-color: black; " +
                            "-fx-border-width: 2; -fx-border-radius: 5; -fx-background-color: lightgray;");

                    Label ubicacion = new Label("Ubicación: " + item.getUbicacion());
                    Label telefono = new Label("Teléfono: " + item.getTelefono());

                    container.getChildren().addAll(ubicacion, telefono);

                    setGraphic(container);
                }
            }
        });

        // Asignar la lista observable al ListView
        tiendas.setItems(this.tiendasOA);
    }

    @Override
    public void onOpen(Object input) throws IOException {
        TiendaDAO tiendaDAO = new TiendaDAO();
        List<Tienda> tiendaList = tiendaDAO.findAll();
        this.tiendasOA = FXCollections.observableArrayList(tiendaList);
        tiendas.setItems(this.tiendasOA);
    }

    @FXML
    public void buscar() {
        String palabra = buscador.getText().toLowerCase();
        ObservableList<Tienda> filteredTiendas = tiendasOA.stream()
                .filter(tienda -> tienda.getUbicacion().toLowerCase().contains(palabra))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        tiendas.setItems(filteredTiendas);
        tiendas.refresh();
    }

    @FXML
    private void addStore() throws Exception {
        // Método para abrir un modal y añadir una tienda
        App.currentController.openModalv(Scenes.ADDTIENDA, "Añadiendo Tienda", this, null);
    }

    @FXML
    private void atras() throws Exception {
        // Cambiar a la pantalla principal
        App.currentController.changeScene(Scenes.PANTALLAADMIN, null);
    }

    @FXML
    private void GoToModifyDeleteStore() throws Exception {
        // Obtener la tienda seleccionada y abrir un modal para modificarla o eliminarla
        Tienda tiendaSeleccionada = tiendas.getSelectionModel().getSelectedItem();
        System.out.println(tiendaSeleccionada.toString());
        App.currentController.changeScene(Scenes.MODIFICARTIENDA, tiendaSeleccionada);

    }
}
