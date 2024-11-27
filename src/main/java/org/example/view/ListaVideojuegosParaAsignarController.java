package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.DAO.TiendaDAO;
import org.example.DAO.VideojuegoDAO;
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

                    // Crear CheckBox
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

                    Label nombre = new Label("Nombre: " + item.getNombre());
                    Label precio = new Label("Precio: $" + String.format("%.2f", item.getPrecio()));
                    Label descripcion = new Label("Descripción: " + item.getDescripcion());
                    Label desarrollador = new Label("Desarrollador: " + item.getDesarrollador().getNombre());
                    descripcion.setWrapText(true);

                    // Agregar etiquetas a un VBox
                    VBox infoContainer = new VBox(nombre, precio, descripcion, desarrollador);
                    infoContainer.setSpacing(5);

                    // Agregar CheckBox y VBox al contenedor principal
                    container.getChildren().addAll(checkBox, infoContainer);

                    // Configurar el gráfico de la celda
                    setGraphic(container);
                }
            }
        });

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
        ObservableList<Videojuego> filteGame = games.stream()
                .filter(game -> game.getNombre().toLowerCase().contains(buscador.getText()))
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

    public void añadirBD() {
        TiendaDAO tiendaDAO = new TiendaDAO();
        ArrayList<Videojuego> videojuegosActuales = tiendaDAO.buscarJuegosDeTienda(tiendSeleccionada);
        ArrayList<Videojuego> juegosSeleccionados = getSeleccionados();
        tiendSeleccionada.setVideojuegos(videojuegosActuales);
        boolean aux = false;
        for (Videojuego videojuego : videojuegosActuales){
            for (Videojuego juegoSeleccionado : juegosSeleccionados){
                if (videojuego.getId_videojuego() == juegoSeleccionado.getId_videojuego()){
                    System.out.println("ya esta insertado");
                    aux = true;
                    break;
                }
            }
            }
        if (!aux){
            tiendSeleccionada.setVideojuegos(juegosSeleccionados);
            tiendaDAO.insertarTiendaVideojuego(tiendSeleccionada);
        }

    }




}