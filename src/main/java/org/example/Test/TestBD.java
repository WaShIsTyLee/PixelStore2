package org.example.Test;

import org.example.BaseDatos.DataBaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestBD {
    public static void main(String[] args) {
        try (Connection conn = DataBaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Conexión a la base de datos establecida.");
                insertUsuario(conn);
            }
        } catch (SQLException e) {
            System.out.println("Error en la conexión o en la ejecución de SQL: " + e.getMessage());
        }
    }

    private static void insertUsuario(Connection conn) {
        String insertSQL = "INSERT INTO Usuario (nombre, email, contrasena, administrador) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, "Juan Pérez"); // Nombre
            pstmt.setString(2, "juan.perez@example.com"); // Email
            pstmt.setString(3, "1234"); // Contraseña
            pstmt.setBoolean(4, false); // Administrador
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Usuario insertado correctamente.");
            } else {
                System.out.println("No se pudo insertar el usuario.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
        }
    }

}
