package org.example.Test;

import org.example.DAO.TiendaDAO;
import org.example.Model.Tienda;

public class TestTienda {
    public static void main(String[] args) {
        Tienda tienda = new Tienda(4, "calle 1", "123456789");
        TiendaDAO tdao = new TiendaDAO();

        System.out.println(tdao.getTienda(tienda));
        tdao.delete(tdao.getTienda(tienda));

    }
}
