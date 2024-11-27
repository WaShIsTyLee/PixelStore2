package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.DAO.VideojuegoDAO;
import org.example.Model.Videojuego;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ListaVideojuegosParaAsignarController extends Controller implements Initializable {

    @FXML
    private ListView<Videojuego> videojuegos;
    @FXML
    private TextField buscador;



    private ObservableList<Videojuego> games;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        videojuegos.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Videojuego item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Crear un diseño encuadrado para cada elemento
                    VBox container = new VBox();
                    container.setSpacing(5);
                    container.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-color: lightgray;");

                    Label nombre = new Label("Nombre: " + item.getNombre());
                    Label precio = new Label("Precio: $" + String.format("%.2f", item.getPrecio()));
                    Label descripcion = new Label("Descripción: " + item.getDescripcion());
                    Label desarrollador = new Label("Desarrollador: " + item.getDesarrollador().getNombre());
                    descripcion.setWrapText(true);

                    container.getChildren().addAll(nombre, precio, descripcion,desarrollador);

                    setGraphic(container);
                }
            }
        });

        videojuegos.setItems(this.games);
    }

    @Override
    public void onOpen(Object input) throws IOException {
        VideojuegoDAO game = new VideojuegoDAO();
        List<Videojuego> gameList = game.gameList();
        this.games = FXCollections.observableArrayList(gameList);
        videojuegos.setItems(this.games);
    }
    @FXML
    public void buscar(){
        ObservableList<Videojuego> filteGame = games.stream()
                .filter(game -> game.getNombre().toLowerCase().contains(buscador.getText()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        videojuegos.setItems(filteGame);
        videojuegos.refresh();
    }


}
