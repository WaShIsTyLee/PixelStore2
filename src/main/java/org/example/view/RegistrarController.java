package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.example.App;
import org.example.DAO.UsuarioDAO;
import org.example.Model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegistrarController extends Controller implements Initializable {
    @FXML
    ImageView imageFlechaAtras;
    @FXML
    PasswordField password;
    @FXML
    TextField name;
    @FXML
    TextField email;
    @FXML
    Button botonRegistrar;

    @FXML
    ImageView imageFlechaIncioSesión;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public Usuario recogerUsurio() {
        Usuario usuarioaux = new Usuario();
        if (comprobacionCampos()) {
            usuarioaux.setNombre(name.getText());
            usuarioaux.setContrasena(password.getText());
            usuarioaux.setEmail(email.getText());
            if (Usuario.isAdministrator(email.getText())) {
                usuarioaux.setAdministrador(true);
            } else {
                usuarioaux.setAdministrador(false);
            }
            return usuarioaux;
        } else {
            System.out.println("Error al registrar usuario");
            //ALERTA DE EROOR
        }
        return null;
    }

    public boolean comprobacionCampos() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (name.getText().isEmpty() || password.getText().isEmpty() || email.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean comprobarBD(Usuario usuario) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        ArrayList <Usuario> usuariosregistrados = usuarioDAO.findAllEmails();
        for (Usuario usuario1 : usuariosregistrados){
            if (usuario1.getEmail().equals(usuario.getEmail())) {
                return false;
            }
        }
            return true;
    }

    public void registrar() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = recogerUsurio();
        if (usuario == null) {
            System.out.println("ERROR alguno de los campos esta vacio");
        }else if (!comprobarBD(usuario)){
            //ALERTA
            System.out.println("Error email ya registrado");
        } else {
            usuarioDAO.insert(usuario);
        }
    }

    @Override
    public void onOpen(Object input) throws IOException {

    }

    @FXML
    public void IrAPrimary() throws IOException {
        App.currentController.changeScene(Scenes.PRIMARY, null);
    }


    @FXML
    public void IrAInicioSesion() throws IOException {
        App.currentController.changeScene(Scenes.INICIOSESION, null);
    }
}
