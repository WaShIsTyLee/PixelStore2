package org.example.Test;


import org.example.BaseDatos.ConnectionProperties;
import org.example.BaseDatos.XMLManager;

public class LoadBD {
    public static void main(String[] args) {

        ConnectionProperties conn = XMLManager.readXML(new ConnectionProperties(),"connection.xml");
        System.out.println(conn);
    }
}
