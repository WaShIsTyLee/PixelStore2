package org.example.BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQLite {
    // Instancia estática de la clase para implementar el patrón Singleton
    private static ConnectionSQLite instance;

    // Conexión a la base de datos
    private static Connection connection;

    // URL de la base de datos SQLite (modifica según la ubicación de tu archivo DB)
    private static final String URL = "jdbc:sqlite:pixelstore.db";

    // Constructor privado para evitar que se instancie desde fuera de la clase
    private ConnectionSQLite() {
        try {
            // Establecer la conexión con la base de datos SQLite
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            connection = null; // Si hay un error, la conexión se establece como null
        }
    }

    // Método público estático para obtener la instancia de la conexión
    public static Connection getConnection() {
        if (instance == null) {
            // Si la instancia aún no ha sido creada, se crea aquí
            instance = new ConnectionSQLite();
        }
        return connection;
    }

    // Método para cerrar la conexión
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close(); // Cierra la conexión a la base de datos
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
