package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.App;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController extends Controller implements Initializable {
    @FXML
    private BorderPane borderPane;
    private Controller centerController;
    @FXML
    static Alert alertError = new Alert(Alert.AlertType.ERROR);
    @FXML
    static Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {
        changeScene(Scenes.PRIMARY, null);
    }

    public static void showDatosIncorrectos() {
        alertError.setTitle("❌ Datos incorrectos ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" Los datos introducidos son incorrectos, por favor revíselos");
        alertError.showAndWait();
    }

    public static void showAñadirDesarrollador() {
        alertError.setTitle("❌ Añadir Desarrollador ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" Error al añadir el desarrollador");
        alertError.showAndWait();
    }

    public static void showErrorCampos() {
        alertError.setTitle("❌ Campos incorrectos ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" Error en los campos introducidos");
        alertError.showAndWait();
    }

    public static void showVideojuegoYaExisteEnBBDD() {
        alertError.setTitle("❌ Videojuego Existente ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" El videojuego que desea introducir ya se encuentra registrado en la base de datos");
        alertError.showAndWait();
    }

    public static void showCamposIncompletos() {
        alertError.setTitle("❌ Campos Incompletos ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" Hay campos vacíos que deben ser rellenados");
        alertError.showAndWait();
    }

    public static void showVerificaCampos() {
        alertError.setTitle("❌ Campos no verificados ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" Por favor, vuelve a verificar los campos ");
        alertError.showAndWait();
    }


    public static void showTiendaYaExistente() {
        alertError.setTitle("❌ Tienda existente ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" Ya existe una tienda con esa dirección o teléfono");
        alertError.showAndWait();
    }

    public static void showVideojuegoExistenteEnTienda() {
        alertError.setTitle("❌ Videojuego Existente ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" El juego seleccionado ya existe en esta tienda");
        alertError.showAndWait();
    }

    public static void showUsuarioInexistente() {
        alertError.setTitle("❌ Usuario Inexistente ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" El usuario no existe");
        alertError.showAndWait();
    }

    public static void showAdmin() {
        alertInfo.setTitle("Administrador");
        alertInfo.getDialogPane().setPrefWidth(700);
        alertInfo.getDialogPane().setPrefHeight(160);
        alertInfo.setHeaderText(null);
        alertInfo.setContentText(" Sesión Iniciada como Administrador");
        alertInfo.showAndWait();
    }

    public static void showUser() {
        alertInfo.setTitle("Usuario");
        alertInfo.getDialogPane().setPrefWidth(700);
        alertInfo.getDialogPane().setPrefHeight(160);
        alertInfo.setHeaderText(null);
        alertInfo.setContentText(" Sesión Iniciada como Usuario");
        alertInfo.showAndWait();
    }

    public static void showCredenciales() {
        alertError.setTitle("❌ Credenciales ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" Las credenciales no coinciden");
        alertError.showAndWait();
    }
    public static void showRegistrarUsuario() {
        alertError.setTitle("❌ Registrar Usuario ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" Error al registrar el usuario");
        alertError.showAndWait();
    }
    public static void showEmailRegistrado() {
        alertError.setTitle("❌ Email Registrado ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" El email ya se encuentra registrado en la base de datos");
        alertError.showAndWait();
    }

    public static void showUbicacionExistente() {
        alertError.setTitle("❌ Ubicación existente ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" la ubicación ingresada ya existe para otra tienda");
        alertError.showAndWait();
    }

    public static void showNombreVideojuegoExistente() {
        alertError.setTitle("❌ Nombre Videojuego Existente ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" El nombre ingresado ya existe para otro videojuego");
        alertError.showAndWait();
    }

    public static void showPrecioValido() {
        alertError.setTitle("❌ Precio no válido ❌");
        alertError.getDialogPane().setPrefWidth(700);
        alertError.getDialogPane().setPrefHeight(160);
        alertError.setHeaderText(null);
        alertError.setContentText(" El precio debe ser un número válido");
        alertError.showAndWait();
    }

    public static View loadFXML(Scenes scenes) throws IOException {
        String url = scenes.getURL();
        System.out.println(url);
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url));
        Parent p = loader.load();
        Controller c = loader.getController();
        View view = new View();
        view.scene = p;
        view.controller = c;
        return view;
    }
    public void changeScene(Scenes scene, Object data) throws IOException {
        View view = loadFXML(scene);
        borderPane.setCenter(view.scene);
        this.centerController = view.controller;
        this.centerController.onOpen(data);
    }
    public void openModalv(Scenes scenes, String tilte, Controller parent, Object data) throws Exception {
        View view = loadFXML(scenes);
        Stage stage = new Stage();
        stage.setTitle(tilte);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.stage);
        Scene _scene = new Scene(view.scene);
        stage.setScene(_scene);
        view.controller.onOpen(data);
        stage.showAndWait();
    }

}
