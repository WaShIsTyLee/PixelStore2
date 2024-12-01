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
import javafx.stage.Stage;
import org.example.App;
import org.example.DAO.MySQL.TiendaDAO;
import org.example.DAO.MySQL.VideojuegoDAO;
import org.example.DAO.SQLite.TiendaDAOSqlite;
import org.example.Model.Tienda;
import org.example.Model.Videojuego;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ListaVideojuegosParaAsignarController extends Controller implements Initializable {

    @FXML
    private ListView<Videojuego> videojuegos;
    @FXML
    private TextField buscador;
    @FXML
    private Button botonInsertar;

    private ObservableList<Videojuego> games;
    Tienda tiendSeleccionada;

    // Lista para almacenar los videojuegos seleccionados
    private ArrayList<Videojuego> seleccionados = new ArrayList<>();

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
                    container.setMaxWidth(Double.MAX_VALUE);  // Asegura que el HBox ocupe todo el espacio disponible

                    // Crear el CheckBox
                    CheckBox checkBox = new CheckBox();
                    checkBox.setOnAction(event -> {
                        if (checkBox.isSelected()) {
                            if (!seleccionados.contains(item)) {
                                seleccionados.add(item); // Agregar a la lista de seleccionados
                            }
                        } else {
                            seleccionados.remove(item); // Eliminar de la lista si se deselecciona
                        }
                    });

                    // Crear las etiquetas con la información del videojuego
                    Label nombre = new Label("Nombre: " + item.getNombre());
                    Label precio = new Label("Precio: $" + String.format("%.2f", item.getPrecio()));
                    Label descripcion = new Label("Descripción: " + item.getDescripcion());
                    Label desarrollador = new Label("Desarrollador: " + item.getDesarrollador().getNombre());
                    descripcion.setWrapText(true);  // Ajuste de texto para que se vea correctamente

                    // Agregar las etiquetas a un VBox
                    VBox infoContainer = new VBox(nombre, precio, descripcion, desarrollador);
                    infoContainer.setSpacing(5);
                    infoContainer.setMaxWidth(Double.MAX_VALUE);  // Hacer que el VBox ocupe todo el ancho disponible

                    // Crear el ImageView para mostrar la imagen
                    ImageView imagen = new ImageView();
                    if (item.getRutaImagen() != null && !item.getRutaImagen().isEmpty()) {
                        imagen.setImage(new Image(item.getRutaImagen()));  // Establecer la imagen
                        imagen.setFitHeight(100);  // Ajustar el tamaño de la imagen
                        imagen.setPreserveRatio(true);  // Mantener las proporciones
                    }

                    // Crear un Region vacío para empujar la imagen a la derecha
                    Region espacioVacío = new Region();
                    HBox.setHgrow(espacioVacío, Priority.ALWAYS);  // Hace que el espacio vacío ocupe todo el espacio disponible

                    // Añadir el CheckBox, el contenedor de la información y la imagen al contenedor principal
                    container.getChildren().addAll(checkBox, infoContainer, espacioVacío, imagen);

                    // Configurar el gráfico de la celda
                    setGraphic(container);
                }
            }
        });

        // Asignar la lista de videojuegos a la ListView
        videojuegos.setItems(this.games);
    }


    @Override
    public void onOpen(Object input) throws IOException {
        tiendSeleccionada = (Tienda) input;
        VideojuegoDAO game = new VideojuegoDAO();
        List<Videojuego> gameList = game.gameList();
        this.games = FXCollections.observableArrayList(gameList);
        videojuegos.setItems(this.games);
    }

    @FXML
    public void buscar() {
        String palabra = buscador.getText().toLowerCase();
        ObservableList<Videojuego> filteGame = games.stream()
                .filter(game -> game.getNombre().toLowerCase().contains(palabra))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        videojuegos.setItems(filteGame);
        videojuegos.refresh();
    }

    /**
     * Método para obtener la lista de elementos seleccionados
     *
     * @return Lista de videojuegos seleccionados
     */
    public ArrayList<Videojuego> getSeleccionados() {
        return seleccionados;
    }

    public void añadirBD() throws Exception {
        TiendaDAO tiendaDAO = new TiendaDAO();
        ArrayList<Videojuego> videojuegosActuales = tiendaDAO.buscarJuegosDeTienda(tiendSeleccionada);
        ArrayList<Videojuego> juegosSeleccionados = getSeleccionados();
        tiendSeleccionada.setVideojuegos(videojuegosActuales);
        boolean aux = false;
        for (Videojuego videojuego : videojuegosActuales){
            for (Videojuego juegoSeleccionado : juegosSeleccionados){
                if (videojuego.getId_videojuego() == juegoSeleccionado.getId_videojuego()){
                    AppController.showVideojuegoExistenteEnTienda();

                    aux = true;
                    break;
                }
            }
            }
        if (!aux){
            tiendSeleccionada.setVideojuegos(juegosSeleccionados);
            TiendaDAOSqlite tiendaDAOSqlite = new TiendaDAOSqlite();
            tiendaDAO.insertarTiendaVideojuego(tiendSeleccionada);
            tiendaDAOSqlite.insertarTiendaVideojuego(tiendSeleccionada);

            Stage currentStage = (Stage) botonInsertar.getScene().getWindow();
            currentStage.close();
            App.currentController.changeScene(Scenes.MODIFICARTIENDA, tiendSeleccionada);
        }

    }






}
