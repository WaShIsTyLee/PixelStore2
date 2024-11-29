package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.App;
import org.example.DAO.VideojuegoDAO;
import org.example.Model.Videojuego;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GameMenuController extends Controller implements Initializable {
    @FXML
    private ImageView atras;
    @FXML
    private ListView<Videojuego> videojuegos;
    @FXML
    private TextField buscador;



    private ObservableList<Videojuego> games;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        videojuegos.setCellFactory(lv -> new ListCell<Videojuego>() {
            @Override
            protected void updateItem(Videojuego item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Crear un diseño horizontal (HBox) para cada elemento
                    HBox container = new HBox();
                    container.setSpacing(10);  // Espaciado entre los elementos
                    container.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-color: lightgray;");
                    container.setMaxWidth(Double.MAX_VALUE);  // Asegura que el HBox ocupe todo el espacio disponible

                    // Crear los labels con la información del videojuego
                    VBox labels = new VBox();
                    labels.setSpacing(5);
                    labels.setMaxWidth(Double.MAX_VALUE);  // Hacer que el VBox ocupe todo el ancho disponible
                    labels.setStyle("-fx-hgap: 10;");

                    Label nombre = new Label("Nombre: " + item.getNombre());
                    Label precio = new Label("Precio: $" + String.format("%.2f", item.getPrecio()));
                    Label descripcion = new Label("Descripción: " + item.getDescripcion());
                    Label desarrollador = new Label("Desarrollador: " + item.getDesarrollador().getNombre());
                    descripcion.setWrapText(true);  // Ajuste de texto para que se vea correctamente

                    labels.getChildren().addAll(nombre, precio, descripcion, desarrollador);

                    // Crear un ImageView para mostrar la imagen
                    ImageView imagen = new ImageView();
                    if (item.getRutaImagen() != null && !item.getRutaImagen().isEmpty()) {
                        imagen.setImage(new Image(item.getRutaImagen()));  // Establecer la imagen
                        imagen.setFitHeight(100);  // Ajustar el tamaño de la imagen
                        imagen.setPreserveRatio(true);  // Mantener las proporciones
                    }

                    // Crear un Region vacío para empujar la imagen a la derecha
                    Region espacioVacío = new Region();
                    HBox.setHgrow(espacioVacío, Priority.ALWAYS);  // Hace que el espacio vacío ocupe todo el espacio disponible

                    // Añadir los labels y la imagen al HBox
                    container.getChildren().addAll(labels, espacioVacío, imagen);

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
        String palabra = buscador.getText().toLowerCase();
        ObservableList<Videojuego> filteGame = games.stream()
                .filter(game -> game.getNombre().toLowerCase().contains(palabra))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        videojuegos.setItems(filteGame);
        videojuegos.refresh();
    }
    @FXML
    private void addGame() throws Exception {
        App.currentController.openModalv(Scenes.INSERTGAMES,"Añadiendo Videojuego",this,null);
    }

    @FXML
    private void atras() throws Exception {
        App.currentController.changeScene(Scenes.PANTALLAADMIN,null);
    }



    @FXML
    private void GoToModifyDeleteGames() throws Exception {
        Videojuego videojuegoCapturado = videojuegos.getSelectionModel().getSelectedItem();
        //videojuegoCapturado.setRutaImagen();
        System.out.println(videojuegoCapturado.toString());
        App.currentController.openModalv(Scenes.MODIFICARVIDEOJUEGO,"Modificar Videojuego",this,videojuegoCapturado);
    }


}
