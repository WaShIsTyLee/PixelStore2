package org.example.view;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.App;
import org.example.DAO.MySQL.DesarrolladorDAO;
import org.example.DAO.SQLite.DesarrolladorDAOSqlite;
import org.example.Model.Desarrollador;

import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;




public class AddDevModalController extends Controller implements Initializable {


    @FXML
    TextField nombreDesa;
    @FXML
    TextField pais;
    @FXML
    Button save;

    private Desarrollador developer =null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {
        developer = (Desarrollador) input;

        if (developer != null){
            nombreDesa.setText(developer.getNombre());
            pais.setText(developer.getPais());
            save.setText("Actualizar");
        }
    }
    @FXML
    private Desarrollador insertar(){
        Desarrollador nwDesa = null;
        String nombreD = nombreDesa.getText();
        String paisD = pais.getText();
        if (developer == null){
            if (!exisDesa(nombreD) && !paisD.trim().isEmpty() && validarNombrePais(paisD)){
                nwDesa = new Desarrollador();
                nwDesa.setNombre(nombreD);
                nwDesa.setPais(paisD);
            }else {
                AppController.showDatosIncorrectos();
            }
        }else {
            if (!nombreD.trim().isEmpty()){
                if (!exisDesa(nombreD)){
                    developer.setNombre(nombreD);
                }
            }if (!paisD.trim().isEmpty()&& validarNombrePais(paisD)) {
                developer.setPais(paisD);
            }
             nwDesa = developer;
        }

        return nwDesa;
    }

    private boolean exisDesa(String desarrollador){
        boolean result = false;
        if (desarrollador !=null){
            if (!desarrollador.trim().isEmpty()){
                DesarrolladorDAO desarr = new DesarrolladorDAO();
                ArrayList<Desarrollador> desarrolladors = desarr.findAll();
                for(Desarrollador d : desarrolladors){
                    if (d.getNombre().equals(desarrollador)){
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }
    private boolean validarNombrePais(String pais){
        boolean result = false;
        if (pais == null || pais.isEmpty()) {
            return false;
        }
        String regex = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$";
        result = pais.matches(regex);

        return result;
    }
    @FXML
    private void AddDesarrollador() throws IOException {
        Desarrollador newDesarrollador = insertar();
        DesarrolladorDAO saveDesa = new DesarrolladorDAO();
        DesarrolladorDAOSqlite saveDesaSql = new DesarrolladorDAOSqlite();
        if (newDesarrollador == null){

            //faltan poner alertas
            AppController.showAñadirDesarrollador();
        }else {
            newDesarrollador =insertar();
            saveDesa.save(newDesarrollador);
            saveDesaSql.save(newDesarrollador);
            App.currentController.changeScene(Scenes.DESARROLLADORES, null);
        }
    }
    @FXML
    private void deleteDesarrollador() throws IOException {
        DesarrolladorDAO delete = new DesarrolladorDAO();

        delete.delete(developer);
        App.currentController.changeScene(Scenes.DESARROLLADORES, null);
    }
    @FXML
    private void closeWindow(Event event){
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

}
