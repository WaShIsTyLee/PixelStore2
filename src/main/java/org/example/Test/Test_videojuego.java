package org.example.Test;

import org.example.DAO.DesarrolladorDAO;
import org.example.DAO.VideojuegoDAO;
import org.example.Model.Desarrollador;
import org.example.Model.Videojuego;

public class Test_videojuego {
    public static void main(String[] args) {
        int i = 1;
        DesarrolladorDAO d = new DesarrolladorDAO();
        Desarrollador f=d.findByID(i);
        //d.save(desarrollador);
        Videojuego videojuego = new Videojuego("Mario bross","Juego en un mundo fisticio",20.3f,f);
        VideojuegoDAO v = new VideojuegoDAO();
        //Videojuego video = v.findById(3);
        //v.delete(video);
        v.save(videojuego);

    }
}
