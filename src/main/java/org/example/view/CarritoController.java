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
import javafx.scene.text.Text;
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
    private Text textoTotal;
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
                    Label fechaCompra = new Label("Fecha de Compra: " + item.getFechaCompra().toString());
                    labels.getChildren().addAll(nombre, precio, fechaCompra);
                    ImageView imagen = new ImageView();
                    if (item.getRutaImagen() != null && !item.getRutaImagen().isEmpty()) {
                        imagen.setImage(new Image(item.getRutaImagen()));
                        imagen.setFitHeight(100);
                        imagen.setPreserveRatio(true);
                    }
                    Region espacioVacío = new Region();
                    HBox.setHgrow(espacioVacío, Priority.ALWAYS);
                    container.getChildren().addAll(labels, espacioVacío, imagen);

                    setGraphic(container);
                }
            }
        });

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

        textoTotal.setText(String.valueOf(aux.getPrecioTotal() ) + " €");
        try {
            ux.guardarFactura(aux);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
