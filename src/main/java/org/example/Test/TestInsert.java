package org.example.Test;

import org.example.DAO.MySQL.UsuarioDAO;
import org.example.Model.Usuario;

public class TestInsert {
    public static void main(String[] args) {
        UsuarioDAO udao = new UsuarioDAO();
        Usuario usuario = new Usuario(4);
        udao.delete(usuario);
    }



}
