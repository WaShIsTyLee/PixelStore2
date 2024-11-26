package org.example.view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.DAO.DesarrolladorDAO;
import org.example.Model.Desarrollador;

import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerDesaAdd extends Controller implements Initializable {
    @FXML
    TextField nombreDesa;
    @FXML
    TextField pais;
    @FXML
    Button Añadir;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {

    }
    @FXML
    private Desarrollador insertar(){
        Desarrollador nwDesa = new Desarrollador();
        String nombreD = nombreDesa.getText();
        String paisD = pais.getText();
        if (!exisDesa(nombreD) && paisD!=null){
            nwDesa.setNombre(nombreD);
            nwDesa.setPais(paisD);

        }else {
            nwDesa = null;
        }
        return nwDesa;
    }
    private boolean exisDesa(String desarrollador){
        boolean result = false;
        if (desarrollador !=null && !desarrollador.trim().isEmpty()){
            DesarrolladorDAO desarr = new DesarrolladorDAO();
            ArrayList<Desarrollador> desarrolladors = desarr.findAll();
            for(Desarrollador d : desarrolladors){
                if (d.getNombre() == desarrollador){
                    result = true;
                }
            }
        }
        return result;
    }
    @FXML
    private void AddDesarrollador() throws IOException {
        Desarrollador newDesarrollador = new Desarrollador();
        DesarrolladorDAO saveDesa = new DesarrolladorDAO();
        if (insertar() == null){
            System.out.println("ERROR al añadir El desarrollador");
        }else {
            newDesarrollador =insertar();
            saveDesa.save(newDesarrollador);
            App.currentController.changeScene(Scenes.DESARROLLADORES, null);
        }
    }
    @FXML
    private void closeWindow(Event event){
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
