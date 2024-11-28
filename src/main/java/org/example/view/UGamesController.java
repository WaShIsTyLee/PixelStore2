package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.ImageView;
import org.example.App;
import org.example.DAO.UsuarioDAO;
import org.example.DAO.VideojuegoDAO;
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
    private TilePane videojuegos; // Cambiado a TilePane
    @FXML
    private TextField buscador;

    private ObservableList<Videojuego> games;
    private ArrayList<Videojuego> selectedGames = new ArrayList<>(); // Lista de juegos seleccionados

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configuración inicial del TilePane (espaciado entre las "tarjetas")
        videojuegos.setHgap(20);
        videojuegos.setVgap(20);
        videojuegos.setPrefColumns(3); // Número de columnas visibles
        videojuegos.setStyle("-fx-background-color: #0f0f1a; -fx-padding: 20;");
    }

    @Override
    public void onOpen(Object input) throws IOException {
        // Obtener lista de videojuegos desde el DAO
        VideojuegoDAO gameDAO = new VideojuegoDAO();
        List<Videojuego> gameList = gameDAO.gameList();
        this.games = FXCollections.observableArrayList(gameList);

        // Mostrar videojuegos en el TilePane
        renderGames(this.games);
    }

    // Método para renderizar los videojuegos en el TilePane
    private void renderGames(ObservableList<Videojuego> games) {
        videojuegos.getChildren().clear(); // Limpiar elementos previos
        for (Videojuego game : games) {
            videojuegos.getChildren().add(createGameCard(game));
        }
    }

    // Método para crear una "tarjeta" para cada videojuego
    private VBox createGameCard(Videojuego game) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setStyle("-fx-padding: 15; -fx-background-color: #1c1c2e; -fx-border-color: #ff4500; "
                + "-fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");
        card.setPrefWidth(150); // Ancho de cada tarjeta
        card.setMinHeight(200); // Altura mínima de la tarjeta

        // Imagen del videojuego (comentado temporalmente para evitar errores)
        /*
        ImageView imageView = new ImageView();
        try {
            // Intenta cargar la imagen si está disponible
            Image image = new Image(game.getImageUrl(), true);
            imageView.setImage(image);
        } catch (Exception e) {
            // Si no hay imagen o ocurre un error, deja el ImageView vacío
            System.out.println("Error al cargar la imagen: " + e.getMessage());
        }
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        */

        // Etiqueta con el nombre del videojuego
        Label name = new Label(game.getNombre());
        name.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        name.setStyle("-fx-text-fill: white;");

        // Etiqueta con el precio del videojuego
        Label price = new Label("€" + String.format("%.2f", game.getPrecio()));
        price.setStyle("-fx-text-fill: #50fa7b; -fx-font-size: 14;");

        // Etiqueta con el nombre del desarrollador
        Label developer = new Label(game.getDesarrollador().getNombre());
        developer.setStyle("-fx-text-fill: white; -fx-font-size: 12;");

        // Etiqueta con la descripción del videojuego
        Label description = new Label(game.getDescripcion());
        description.setStyle("-fx-text-fill: #b0b0b0; -fx-font-size: 12; -fx-wrap-text: true;");

        // Botón para añadir al carrito
        Button addToCart = new Button("Añadir al carrito");
        addToCart.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white; -fx-font-size: 12; "
                + "-fx-background-radius: 5; -fx-padding: 5;");

        // Acción del botón: llamar al método añadirACarrito
        addToCart.setOnAction(event -> {
            // Obtener el usuario actual de la sesión
            Usuario usuario = Sesion.getInstancia().getUsuarioIniciado();

            // Llamar al método añadirACarrito para insertar el videojuego en el carrito
            try {
                añadirACarrito(usuario, game);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Añadir los elementos al contenedor de la tarjeta
        card.getChildren().addAll(name, price, developer, description, addToCart);

        // Efecto de expansión solo sobre el cuadrado donde el ratón entra
        card.setOnMouseEntered(event -> {
            // Expande solo la tarjeta
            card.setScaleX(1.2); // Escala X (ancho) para expansión
            card.setScaleY(1.2); // Escala Y (alto) para expansión
        });

        card.setOnMouseExited(event -> {
            // Vuelve a la normalidad
            card.setScaleX(1.0); // Restaurar tamaño original
            card.setScaleY(1.0); // Restaurar tamaño original
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
        renderGames(filteredGames); // Renderizar los juegos filtrados
    }

    // Método para insertar un videojuego en el carrito
    public void añadirACarrito(Usuario usuario, Videojuego videojuego) throws IOException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        // Aquí llamamos al DAO para insertar el videojuego en la base de datos
        usuarioDAO.insertarVideojuegosEnCarrito(usuario, videojuego);
        App.currentController.changeScene(Scenes.VIDEOJUEGOSUSUARIO, null);
    }

    // Método para obtener los videojuegos seleccionados
    public ArrayList<Videojuego> getSelectedGames() {
        return selectedGames;
    }
}
