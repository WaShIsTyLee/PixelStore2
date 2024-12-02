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
import org.example.DAO.MySQL.TiendaDAO;
import org.example.Model.Tienda;
import org.example.Model.Videojuego;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TiendaUserController extends Controller implements Initializable {
    @FXML
    private ImageView atras;
    @FXML
    private ListView<Tienda> tiendasListView;
    @FXML
    private TextField buscador;


    private ObservableList<Tienda> tiendas2;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tiendasListView.setCellFactory(lv -> new ListCell<>() {
            protected void updateItem(Tienda item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox container = new VBox();
                    container.setSpacing(5);
                    container.setStyle("-fx-padding: 10; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 5; -fx-background-color: lightgray;");
                    Label ubicación = new Label("Ubicación: " + item.getUbicacion());
                    Label teléfono = new Label("Teléfono: " + item.getTelefono());

                    TiendaDAO tdao = new TiendaDAO();
                    List<Videojuego> videojuegos = tdao.buscarJuegosDeTienda(item);

                    StringBuilder juegosConcatenados = new StringBuilder("Juegos: ");
                    for (int i = 0; i < videojuegos.size(); i++) {
                        juegosConcatenados.append(videojuegos.get(i).getNombre());
                        if (i < videojuegos.size() - 1) {
                            juegosConcatenados.append(", ");
                        }
                    }

                    Label juegosLabel = new Label(juegosConcatenados.toString());
                    container.getChildren().addAll(ubicación, teléfono, juegosLabel);
                    setGraphic(container);
                }
            }
        });

        tiendasListView.setItems(this.tiendas2);
    }

    @Override
    public void onOpen(Object input) throws IOException {
        TiendaDAO tdao = new TiendaDAO();
        List<Tienda> tiendaList = tdao.findAll();
        this.tiendas2 = FXCollections.observableArrayList(tiendaList);
        tiendasListView.setItems(this.tiendas2);

    }

    @FXML
    public void buscar(){
        String palabra = buscador.getText().toLowerCase();
        ObservableList<Tienda> filteGame = tiendas2.stream()
                .filter(tienda -> tienda.getUbicacion().toLowerCase().contains(palabra))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        tiendasListView.setItems(filteGame);
        tiendasListView.refresh();
    }

    @FXML
    private void GoMainUser() throws IOException {
        App.currentController.changeScene(Scenes.PANTALLAUSER, null);
    }
}
