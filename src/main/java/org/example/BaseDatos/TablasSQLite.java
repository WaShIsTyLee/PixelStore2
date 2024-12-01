package org.example.BaseDatos;

import java.sql.Connection;
import java.sql.Statement;

public class TablasSQLite {

    // Crear tabla usuario
    public static void crearTablaUsuario() {
        String sql = "CREATE TABLE IF NOT EXISTS usuario (\n" +
                "    id_usuario INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    nombre TEXT NOT NULL,\n" +
                "    email TEXT NOT NULL,\n" +
                "    contrasena TEXT NOT NULL,\n" +
                "    administrador BOOLEAN NOT NULL\n" +
                ");";

        ejecutarSQL(sql, "Tabla usuario creada.");
    }

    // Crear tabla desarrollador
    public static void crearTablaDesarrollador() {
        String sql = "CREATE TABLE IF NOT EXISTS desarrollador (\n" +
                "    id_desarrollador INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    nombre TEXT NOT NULL,\n" +
                "    pais TEXT NOT NULL\n" +
                ");";

        ejecutarSQL(sql, "Tabla desarrollador creada.");
    }

    // Crear tabla videojuego
    public static void crearTablaVideojuego() {
        String sql = "CREATE TABLE IF NOT EXISTS videojuego (\n" +
                "    id_videojuego INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    nombre TEXT NOT NULL,\n" +
                "    precio DECIMAL(10,2) NOT NULL,\n" +
                "    descripcion TEXT,\n" +
                "    id_desarrollador INTEGER NOT NULL,\n" +
                "    foto TEXT,\n" +
                "    FOREIGN KEY (id_desarrollador) REFERENCES desarrollador(id_desarrollador)\n" +
                ");";

        ejecutarSQL(sql, "Tabla videojuego creada.");
    }

    // Crear tabla tienda
    public static void crearTablaTienda() {
        String sql = "CREATE TABLE IF NOT EXISTS tienda (\n" +
                "    id_tienda INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    ubicacion TEXT NOT NULL,\n" +
                "    telefono TEXT\n" +
                ");";

        ejecutarSQL(sql, "Tabla tienda creada.");
    }

    // Crear tabla tiendavideojuego
    public static void crearTablaTiendaVideojuego() {
        String sql = "CREATE TABLE IF NOT EXISTS tiendavideojuego (\n" +
                "    id_tienda INTEGER NOT NULL,\n" +
                "    id_videojuego INTEGER NOT NULL,\n" +
                "    PRIMARY KEY (id_tienda, id_videojuego),\n" +
                "    FOREIGN KEY (id_tienda) REFERENCES tienda(id_tienda),\n" +
                "    FOREIGN KEY (id_videojuego) REFERENCES videojuego(id_videojuego)\n" +
                ");";

        ejecutarSQL(sql, "Tabla tiendavideojuego creada.");
    }

    // Crear tabla usuariovideojuego
    public static void crearTablaUsuarioVideojuego() {
        String sql = "CREATE TABLE IF NOT EXISTS usuariovideojuego (\n" +
                "    id_videojuego INTEGER NOT NULL,\n" +
                "    id_usuario INTEGER NOT NULL,\n" +
                "    fecha_compra DATE,\n" +
                "    PRIMARY KEY (id_videojuego, id_usuario),\n" +
                "    FOREIGN KEY (id_videojuego) REFERENCES videojuego(id_videojuego),\n" +
                "    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)\n" +
                ");";

        ejecutarSQL(sql, "Tabla usuariovideojuego creada.");
    }

    // Método general para ejecutar consultas SQL
    private static void ejecutarSQL(String sql, String mensajeExito) {
        // Usamos la clase ConnectionSQLite para obtener la conexión
        try (Connection conn = ConnectionSQLite.getConnection(); // Usamos el método getConnection()
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println(mensajeExito);
        } catch (Exception e) {
            System.out.println("Error al ejecutar SQL: " + e.getMessage());
        }
    }


    // Método para crear todas las tablas
    public static void crearTodasLasTablas() {
        crearTablaUsuario();
        crearTablaDesarrollador();
        crearTablaVideojuego();
        crearTablaTienda();
        crearTablaTiendaVideojuego();
        crearTablaUsuarioVideojuego();
    }
}
