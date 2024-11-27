package org.example.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.example.App;
import org.example.DAO.DesarrolladorDAO;
import org.example.Model.Desarrollador;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class DevMenuController extends Controller implements Initializable {
    @FXML
    private ListView<Desarrollador> des;
    @FXML
    private TextField buscador;

    private ObservableList<Desarrollador> desarrolladors;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        des.setCellFactory(lv -> new ListCell<>() {
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
                    pais.setWrapText(true);

                    container.getChildren().addAll( Nombre, pais);

                    setGraphic(container);
                }
            }
        });

        des.setItems(this.desarrolladors);
    }

    @Override
    public void onOpen(Object input) throws IOException {
        DesarrolladorDAO desr = new DesarrolladorDAO();
        List<Desarrollador> desarroList = desr.findAll();
        this.desarrolladors = FXCollections.observableArrayList((desarroList));
        des.setItems(this.desarrolladors);
    }
    @FXML
    public void buscar(){
        ObservableList<Desarrollador> filteGame = desarrolladors.stream()
                .filter(desa -> desa.getNombre().toLowerCase().contains(buscador.getText()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        des.setItems(filteGame);
        des.refresh();
    }
    @FXML
    private void atras() throws Exception {
        App.currentController.changeScene(Scenes.PANTALLAADMIN,null);
    }
    @FXML
    private void addDesarrollador() throws Exception {
        App.currentController.openModalv(Scenes.ADDDESARROLLADOR,"Guardando Desarrollador",this,null);
    }
    @FXML
    private void modifyDesa() throws Exception {
        Desarrollador desaSelec = des.getSelectionModel().getSelectedItem();
        App.currentController.openModalv(Scenes.ADDDESARROLLADOR,"Modificando Desarrollador", this, desaSelec);
    }

}
