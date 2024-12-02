package org.example.BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQLite {
    private static ConnectionSQLite instance;

    private static Connection connection;

    private static final String URL = "jdbc:sqlite:pixelstore.db";

    private ConnectionSQLite() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
            connection = null;
        }
    }

    public static Connection getConnection() {
        if (instance == null) {
            instance = new ConnectionSQLite();
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
