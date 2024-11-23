package org.example.Test;

import org.example.DAO.DesarrolladorDAO;
import org.example.Model.Desarrollador;

public class Test_videojuego {
    public static void main(String[] args) {
        int i = 1;
        DesarrolladorDAO d = new DesarrolladorDAO();
        Desarrollador f=d.findByID(i);
        //d.save(desarrollador);
        //Videojuego videojuego = new Videojuego("Mario bross","Juego en un mundo fisticio",20.3f,d);
        //VideojuegoDAO v = new VideojuegoDAO();
        //v.save(videojuego);
        System.out.println(f);
    }
}
