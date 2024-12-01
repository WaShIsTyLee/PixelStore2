package org.example.Test;

import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlElement;
import org.example.DAO.UsuarioDAO;
import org.example.Model.Usuario;
import org.example.Utils.FacturaXML;
import org.example.Utils.XMLusuario;

public class Test_Xml {
    public static void main(String[] args) {

        Usuario u = new Usuario("j@gmail.com");
        UsuarioDAO us = new UsuarioDAO();
        Usuario aux = us.findByEmailNecesary(u);
        aux.setCarrito(us.gammerUserXML(aux));
        XMLusuario ux = new XMLusuario(aux.getNombre());
        System.out.println(aux.getPrecioTotal());
        try {
            ux.guardarFactura(aux);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
