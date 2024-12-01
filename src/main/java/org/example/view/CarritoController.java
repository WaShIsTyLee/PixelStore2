package org.example.view;

import jakarta.xml.bind.JAXBException;
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
import org.example.DAO.MySQL.UsuarioDAO;
import org.example.Model.Usuario;
import org.example.Model.Videojuego;
import org.example.Utils.Sesion;
import org.example.Utils.XMLusuario;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class CarritoController extends Controller implements Initializable{
    @FXML
    private ListView<Videojuego> gamerUser;
    @FXML
    private TextField buscador;
    @FXML
    private ImageView volver;
    private ObservableList<Videojuego> games;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gamerUser.setCellFactory(lv -> new ListCell<Videojuego>() {
            @Override
            protected void updateItem(Videojuego item, boolean empty) {
                super.updateItem(item, empty);

                // Verifica si el item es vacío o nulo
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

                    // Nombre del videojuego
                    Label nombre = new Label("Nombre: " + item.getNombre());
                    // Precio del videojuego
                    Label precio = new Label("Precio: $" + String.format("%.2f", item.getPrecio()));
                    // Fecha de compra
                    Label fechaCompra = new Label("Fecha de Compra: " + item.getFechaCompra().toString());

                    // Añadir las etiquetas al VBox
                    labels.getChildren().addAll(nombre, precio, fechaCompra);

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

                    setGraphic(container);  // Establecer el gráfico de la celda
                }
            }
        });

// Asignar los items al ListView
        gamerUser.setItems(this.games);

    }

    @Override
    public void onOpen(Object input) throws IOException {
        UsuarioDAO game = new UsuarioDAO();
        List<Videojuego> gameList = game.gammerUser(Sesion.getInstancia().getUsuarioIniciado());
        this.games = FXCollections.observableArrayList(gameList);
        gamerUser.setItems(this.games);
    }
    @FXML
    public void buscar(){
        String palabra = buscador.getText().toLowerCase();
        ObservableList<Videojuego> filteGame = games.stream()
                .filter(game -> game.getNombre().toLowerCase().contains(palabra))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        gamerUser.setItems(filteGame);
        gamerUser.refresh();
    }
    @FXML
    private void GoMainUser() throws IOException {
        App.currentController.changeScene(Scenes.PANTALLAUSER, null);
    }
    @FXML
    private void XML(){
        UsuarioDAO us = new UsuarioDAO();
        Usuario aux = us.findByEmailNecesary(Sesion.getInstancia().getUsuarioIniciado());
        aux.setCarrito(us.gammerUserXML(aux));
        XMLusuario ux = new XMLusuario(aux.getNombre());
        System.out.println(aux.getPrecioTotal());
        try {
            ux.guardarFactura(aux);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
