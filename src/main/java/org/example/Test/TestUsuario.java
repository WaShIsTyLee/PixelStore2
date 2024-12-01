package org.example.Test;

import org.example.DAO.MySQL.UsuarioDAO;
import org.example.Model.Usuario;

public class TestUsuario {
    public static void main(String[] args) {
        Usuario usuario1 = new Usuario("Maria","maria@gmail","1234",false);
        UsuarioDAO udao = new UsuarioDAO();

        System.out.println(udao.findByEmail(usuario1));


    }
}
