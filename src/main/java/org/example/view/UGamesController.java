package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.ImageView;
import org.example.App;
import org.example.DAO.MySQL.UsuarioDAO;
import org.example.DAO.SQLite.UsuarioDAOSqlite;
import org.example.DAO.MySQL.VideojuegoDAO;
import org.example.Model.Usuario;
import org.example.Model.Videojuego;
import org.example.Utils.Sesion;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UGamesController extends Controller implements Initializable {

    @FXML
    private ImageView atras;
    @FXML
    private ImageView carro;
    @FXML
    private TilePane videojuegos;
    @FXML
    private TextField buscador;

    private ObservableList<Videojuego> games;
    private ArrayList<Videojuego> selectedGames = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        videojuegos.setHgap(20);
        videojuegos.setVgap(20);
        videojuegos.setPrefColumns(3);
        videojuegos.setStyle("-fx-background-color: #0f0f1a; -fx-padding: 20;");
    }

    @Override
    public void onOpen(Object input) throws IOException {
        VideojuegoDAO gameDAO = new VideojuegoDAO();
        List<Videojuego> gameList = gameDAO.gameList();
        this.games = FXCollections.observableArrayList(gameList);
        renderGames(this.games);
    }

    private void renderGames(ObservableList<Videojuego> games) {
        videojuegos.getChildren().clear();
        for (Videojuego game : games) {
            videojuegos.getChildren().add(createGameCard(game));
        }
    }
    private VBox createGameCard(Videojuego game) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setStyle("-fx-padding: 15; -fx-background-color: #1c1c2e; -fx-border-color: #ff4500; "
                + "-fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");
        card.setPrefWidth(150);
        card.setMinHeight(200);
        ImageView imageView = new ImageView();
        try {

            if (game.getRutaImagen() != null && !game.getRutaImagen().isEmpty()) {
                imageView.setImage(new Image(game.getRutaImagen()));
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                imageView.setPreserveRatio(true);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen: " + e.getMessage());
        }

        Label name = new Label(game.getNombre());
        name.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        name.setStyle("-fx-text-fill: white;");
        Label price = new Label( String.format("%.2f", game.getPrecio()) + " €");
        price.setStyle("-fx-text-fill: #50fa7b; -fx-font-size: 14;");
        Label developer = new Label(game.getDesarrollador().getNombre());
        developer.setStyle("-fx-text-fill: white; -fx-font-size: 12;");
        Label description = new Label(game.getDescripcion());
        description.setStyle("-fx-text-fill: #b0b0b0; -fx-font-size: 12; -fx-wrap-text: true;");


        Button addToCart = new Button("Añadir al carrito");
        addToCart.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white; -fx-font-size: 12; "
                + "-fx-background-radius: 5; -fx-padding: 5;");


        addToCart.setOnAction(event -> {

            Usuario usuario = Sesion.getInstancia().getUsuarioIniciado();


            try {
                añadirACarrito(usuario, game);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        card.getChildren().addAll(imageView, name, price, developer, description, addToCart);

        card.setOnMouseEntered(event -> {
            card.setScaleX(1.2);
            card.setScaleY(1.2);
        });

        card.setOnMouseExited(event -> {
            card.setScaleX(1.0);
            card.setScaleY(1.0);
        });

        return card;
    }


    @FXML
    private void atras() throws Exception {
        App.currentController.changeScene(Scenes.PANTALLAUSER, null);
    }

    @FXML
    public void buscar() {
        String query = buscador.getText().toLowerCase();
        ObservableList<Videojuego> filteredGames = games.filtered(game -> game.getNombre().toLowerCase().contains(query));
        renderGames(filteredGames);
    }


    public void añadirACarrito(Usuario usuario, Videojuego videojuego) throws IOException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        UsuarioDAOSqlite usuarioDAOSqlite = new UsuarioDAOSqlite();
        usuarioDAO.insertarVideojuegosEnCarrito(usuario, videojuego);
        App.currentController.changeScene(Scenes.VIDEOJUEGOSUSUARIO, null);
    }

    public ArrayList<Videojuego> getSelectedGames() {
        return selectedGames;
    }
}
