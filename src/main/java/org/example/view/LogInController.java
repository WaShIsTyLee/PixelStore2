package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.example.App;
import org.example.DAO.UsuarioDAO;
import org.example.Model.Usuario;
import org.example.Utils.Sesion;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInController extends Controller implements Initializable {
    @FXML
    ImageView imageFlechaAtras;
    @FXML
    TextField email;
    @FXML
    PasswordField contraseña;


    @FXML
    ImageView imageFlechaRegistrar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void onOpen(Object input) throws IOException {
    }

    public Usuario recogerDatos() throws IOException {
        Usuario usuario = new Usuario();
        usuario.setEmail(email.getText());
        usuario.setContrasena(contraseña.getText());
        return usuario;
    }

    public void logIn() throws IOException {
        Usuario usuarioRecogido = recogerDatos();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario encontrado = usuarioDAO.findByEmail(usuarioRecogido);
        if (encontrado == null) {
            AppController.showUsuarioInexistente();

        }else{
            if (usuarioRecogido.getContrasena().equals(encontrado.getContrasena()) && usuarioRecogido.getEmail().equals(encontrado.getEmail())) {
                usuarioRecogido = encontrado;
                Sesion.getInstancia().logIn(usuarioRecogido);
                if (usuarioRecogido.isAdministrador()) {
                    AppController.showAdmin();
                    irAPantallaPrincipalAdmin();
                } else {
                    //CAMBIAR A PANTALLA PRINCIPAL DE NORMAL
                    AppController.showUser();
                    irAPantallaPrincipal();
                }
            }else {
                AppController.showCredenciales();

            }
        }

    }

    @FXML
    public void IrAPrimary() throws IOException {
        App.currentController.changeScene(Scenes.PRIMARY, null);
    }

    @FXML
    public void IrARegistrar() throws IOException {
        App.currentController.changeScene(Scenes.REGISTRAR, null);
    }

    @FXML
    public void irAPantallaPrincipalAdmin() throws IOException {
        App.currentController.changeScene(Scenes.PANTALLAADMIN, null);
    }

    public void irAPantallaPrincipal() throws IOException {
        App.currentController.changeScene(Scenes.PANTALLAUSER, null);
    }


}
