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
import org.example.DAO.DesarrolladorDAO;
import org.example.Model.Desarrollador;
import org.example.Model.Videojuego;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShowDevController extends Controller implements Initializable {
    @FXML
    private ListView<Desarrollador> desarrolladorListView;
    @FXML
    ImageView atras;
    @FXML
    TextField buscador;

    private ObservableList<Desarrollador> desarrolladors;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        desarrolladorListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Desarrollador item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Crear un diseño encuadrado para cada elemento
                    VBox container = new VBox();
                    container.setSpacing(5);
                    container.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-color: lightgray;");

                    //Label id = new Label("Codigo: " + item.getId_desarrollador());
                    Label Nombre = new Label("Nombre: " + item.getNombre());
                    Label pais = new Label("Pais: " + item.getPais());

                    DesarrolladorDAO dao = new DesarrolladorDAO();
                    List<Videojuego> videojuegos = dao.gamesDesarrollador(item);

                    StringBuilder juegosConcatenados = new StringBuilder("Juegos: ");
                    for (int i = 0; i < videojuegos.size(); i++) {
                        juegosConcatenados.append(videojuegos.get(i).getNombre());
                        if (i < videojuegos.size() - 1) {
                            juegosConcatenados.append(", "); // Agregar coma si no es el último juego
                        }
                    }

                    Label juegosLabel = new Label(juegosConcatenados.toString());

                    // Agregar elementos al contenedor principal
                    container.getChildren().addAll(Nombre, pais, juegosLabel);
                    setGraphic(container);
                }
            }
        });

        desarrolladorListView.setItems(this.desarrolladors);
    }

    @Override
    public void onOpen(Object input) throws IOException {
        DesarrolladorDAO desr = new DesarrolladorDAO();
        List<Desarrollador> desarroList = desr.findAll();
        this.desarrolladors = FXCollections.observableArrayList((desarroList));
        desarrolladorListView.setItems(this.desarrolladors);
    }
    @FXML
    public void buscar(){
        String palabra = buscador.getText().toLowerCase();
        ObservableList<Desarrollador> filteGame = desarrolladors.stream()
                .filter(desa -> desa.getNombre().toLowerCase().contains(palabra))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        desarrolladorListView.setItems(filteGame);
        desarrolladorListView.refresh();
    }
    @FXML
    private void GoMainUser() throws IOException {
        App.currentController.changeScene(Scenes.PANTALLAUSER, null);
    }
}
