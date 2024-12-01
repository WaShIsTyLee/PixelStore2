package org.example.Test;

import org.example.BaseDatos.TablasSQLite;

public class TestTablaSQLite {
    public static void main(String[] args) {
        System.out.println("Creando las tablas en SQLite...");
        TablasSQLite.crearTodasLasTablas();
        System.out.println("¡Todas las tablas han sido creadas exitosamente!");
    }
}
