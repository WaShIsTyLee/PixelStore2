package org.example.Test;

import org.example.DAO.MySQL.UsuarioDAO;
import org.example.Model.Usuario;
import org.example.Model.Videojuego;

import java.util.ArrayList;

public class Test_videojuego {
    public static void main(String[] args) {
        //int i = 1;
        //DesarrolladorDAO d = new DesarrolladorDAO();
        Usuario user = new Usuario("j@gmail.com");
        UsuarioDAO u = new UsuarioDAO();
        Usuario us =u.findByEmail(user);
        System.out.println(us);
        ArrayList<Videojuego> gameruser =u.gammerUser(us);

        //u.findByEmail()
        //Desarrollador f=d.findByID(i);
        //d.save(desarrollador);
        //System.out.println(f);
        //Videojuego videojuego = new Videojuego("Celda","Juego en un mundo fisticio",17.3f,f);
        //VideojuegoDAO v = new VideojuegoDAO();
       // List<Videojuego> lisv = .gamesDesarrollador(f);
        for(Videojuego videojuego: gameruser){
            System.out.println(videojuego);
        }
        //Videojuego video = v.findById(3);
        //v.delete(video);
        //v.save(videojuego);

    }
}
