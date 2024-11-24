package org.example.Utils;

import org.example.Model.Usuario;

public class Sesion {

    private static Sesion _instance;
    private static Usuario userLoged;

    private Sesion() {
    }

    public static Sesion getInstancia() {
        if (_instance == null) {
            _instance = new Sesion();
            _instance.logIn(userLoged);
        }
        return _instance;
    }

    public void logIn(Usuario user) {
        userLoged = user;
    }

    public Usuario getUsuarioIniciado() {
        return userLoged;
    }

    public void logOut() {
        userLoged = null;
    }
}


