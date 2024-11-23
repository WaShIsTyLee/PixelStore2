package org.example.DAO;

import org.example.BaseDatos.ConnectionDB;
import org.example.Model.Usuario;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {
    private final static String DELETE = "DELETE FROM usuario  WHERE id_usuario = ?";

    public Usuario delete(Usuario child) {
        if (child != null || child.getId_usuario() > 0) {
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(DELETE)) {
                pst.setInt(1, child.getId_usuario());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return child;
    }
}
