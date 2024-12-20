package org.example.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.example.App;
import org.example.DAO.MySQL.UsuarioDAO;
import org.example.DAO.SQLite.UsuarioDAOSqlite;
import org.example.Model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RegisterController extends Controller implements Initializable {
    @FXML
    private ImageView imageFlechaAtras;
    @FXML
    private PasswordField password;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private Button botonRegistrar;

    @FXML
    private ImageView imageFlechaIncioSesión;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public Usuario recogerUsurio() {
        Usuario usuarioaux = new Usuario();
        if (comprobacionCampos() && Usuario.isValidEmail(email.getText())) {
            usuarioaux.setNombre(name.getText());
            usuarioaux.setContrasena(usuarioaux.hashPassword(password.getText()));
            usuarioaux.setEmail(email.getText());
            if (Usuario.isAdministrator(email.getText())) {
                usuarioaux.setAdministrador(true);
            } else {
                usuarioaux.setAdministrador(false);
            }
            return usuarioaux;
        } else {
            AppController.showRegistrarUsuario();
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
        UsuarioDAOSqlite usuarioDAOSqlite = new UsuarioDAOSqlite();
        Usuario usuario = recogerUsurio();
        if (usuario == null) {
            AppController.showCamposIncompletos();
        }else if (!comprobarBD(usuario)){
            AppController.showEmailRegistrado();
        } else {
            usuarioDAO.insert(usuario);
            usuarioDAOSqlite.insert(usuario);

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
