package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.App;
import org.example.DAO.UsuarioDAO;
import org.example.DAO.VideojuegoDAO;
import org.example.Model.Usuario;
import org.example.Model.Videojuego;
import org.example.Utils.Sesion;
import org.example.view.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class UGamesController extends Controller implements Initializable {

    @FXML
    private ImageView atras;
    @FXML
    private ImageView carro;
    @FXML
    private ListView<Videojuego> videojuegos;
    @FXML
    private TextField buscador;

    private ObservableList<Videojuego> games;
    private ArrayList<Videojuego> selectedGames = new ArrayList<>();  // Lista de juegos seleccionados

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
                    // Contenedor principal para cada celda
                    HBox container = new HBox();
                    container.setSpacing(10);
                    container.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-color: lightgray;");

                    // Crear CheckBox (sin texto)
                    CheckBox checkBox = new CheckBox();
                    checkBox.setOnAction(event -> {
                        if (checkBox.isSelected()) {
                            if (!selectedGames.contains(item)) {
                                selectedGames.add(item); // Agregar a la lista de seleccionados
                            }
                        } else {
                            selectedGames.remove(item); // Eliminar de la lista si se deselecciona
                        }
                    });

                    // Crear Labels para la información del videojuego
                    Label nombre = new Label("Nombre: " + item.getNombre());
                    Label precio = new Label("Precio: $" + String.format("%.2f", item.getPrecio()));
                    Label descripcion = new Label("Descripción: " + item.getDescripcion());
                    Label desarrollador = new Label("Desarrollador: " + item.getDesarrollador().getNombre());
                    descripcion.setWrapText(true);

                    // Crear un VBox para contener las etiquetas (nombre, precio, descripción, desarrollador)
                    VBox infoContainer = new VBox(nombre, precio, descripcion, desarrollador);
                    infoContainer.setSpacing(5);

                    // Ajustar el CheckBox para que aparezca un poco por encima de la información
                    HBox checkBoxContainer = new HBox(checkBox);
                    VBox.setMargin(checkBoxContainer, new javafx.geometry.Insets(-5, 0, 5, 0));  // Mueve el CheckBox hacia arriba

                    // Agregar el CheckBox y el VBox al contenedor principal (HBox)
                    container.getChildren().addAll(checkBoxContainer, infoContainer);

                    // Establecer el gráfico de la celda
                    setGraphic(container);
                }
            }
        });

        // Establecer la lista de juegos en la ListView
        videojuegos.setItems(this.games);
    }

    @FXML
    private void atras() throws Exception {
        App.currentController.changeScene(Scenes.PANTALLAUSER, null);
    }

    @Override
    public void onOpen(Object input) throws IOException {
        VideojuegoDAO game = new VideojuegoDAO();
        List<Videojuego> gameList = game.gameList();
        this.games = FXCollections.observableArrayList(gameList);
        videojuegos.setItems(this.games);
    }

    public void añadirACarrito() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario user = Sesion.getInstancia().getUsuarioIniciado();
        usuarioDAO.InsertarVideojuegosEnCarrito(user, selectedGames);
    }

    @FXML
    public void buscar() {
        ObservableList<Videojuego> filteredGames = games.stream()
                .filter(game -> game.getNombre().toLowerCase().contains(buscador.getText()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        videojuegos.setItems(filteredGames);
        videojuegos.refresh();
    }

    // Método para obtener los videojuegos seleccionados
    public ArrayList<Videojuego> getSelectedGames() {
        return selectedGames;
    }
}
