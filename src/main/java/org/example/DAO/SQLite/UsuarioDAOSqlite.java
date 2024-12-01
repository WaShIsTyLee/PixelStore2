package org.example.DAO.SQLite;


import org.example.BaseDatos.ConnectionSQLite;
import org.example.Model.Usuario;
import org.example.Model.Videojuego;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAOSqlite {
    private final static String INSERT = "INSERT INTO usuario( nombre, email, contrasena, administrador) VALUES (?,?,?,?) ";

    public boolean insertarVideojuegosEnCarrito(Usuario usuario, Videojuego videojuego) {
        boolean success = false;
        if (usuario == null || videojuego == null) {
            System.out.println("Error: Usuario o Videojuego nulos.");
            return false;
        }
        String ADDTOCARRITO = "INSERT INTO usuariovideojuego (id_usuario, id_videojuego, fecha_compra) VALUES (?, ?, ?)";
        try (PreparedStatement pst = ConnectionSQLite.getConnection().prepareStatement(ADDTOCARRITO)) {
            pst.setInt(1, usuario.getId_usuario());
            pst.setInt(2, videojuego.getId_videojuego());
            pst.setDate(3, new java.sql.Date(System.currentTimeMillis())); // Fecha actual
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                success = true;
                System.out.println("Videojuego agregado al carrito con éxito.");
            } else {
                System.out.println("No se pudo agregar el videojuego al carrito.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


    public Usuario insert(Usuario usuario) {
        if (usuario != null || usuario.getId_usuario() > 0) {
            try (PreparedStatement pst = ConnectionSQLite.getConnection().prepareStatement(INSERT)) {

                pst.setString(1, usuario.getNombre());
                pst.setString(2, usuario.getEmail());
                pst.setString(3, usuario.getContrasena());
                pst.setBoolean(4, usuario.isAdministrador());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;
    }

}


