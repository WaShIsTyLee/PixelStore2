package org.example.DAO;

import org.example.BaseDatos.ConnectionDB;
import org.example.Model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private final static String DELETE = "DELETE FROM usuario  WHERE id_usuario = ?";
    private final static String INSERT = "INSERT INTO usuario( nombre, email, contrasena, administrador) VALUES (?,?,?,?) ";
    private final static String FINDBYEMAIL = "SELECT * FROM usuario WHERE email = ?";

    public Usuario delete(Usuario usuario) {
        if (usuario != null || usuario.getId_usuario() > 0) {
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(DELETE)) {
                pst.setInt(1, usuario.getId_usuario());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuario;
    }

    public Usuario insert(Usuario usuario) {
        if (usuario != null || usuario.getId_usuario() > 0) {
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(INSERT)) {

                pst.setString(1, usuario.getNombre());
                pst.setString(2, usuario.getEmail());
                pst.setString(3, usuario.getContrasena());
                pst.setBoolean(4, usuario.isAdministrador());
                pst.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return usuario;
    }
    // seleccionar usuario de la base de datos para comprobaciones como correo

    public Usuario findByEmail(Usuario usuario) {
        Usuario usuarioAux = null;

        if (usuario != null && usuario.getEmail() != null) {
            try (
                    Connection conn = ConnectionDB.getConnection();
                    PreparedStatement pst = conn.prepareStatement(FINDBYEMAIL)
            ) {
                pst.setString(1, usuario.getEmail());

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        usuarioAux = new Usuario();
                        usuarioAux.setId_usuario(rs.getInt("id_usuario"));
                        usuarioAux.setNombre(rs.getString("nombre"));
                        usuarioAux.setEmail(rs.getString("email"));
                        usuarioAux.setContrasena(rs.getString("contrasena"));
                        usuarioAux.setAdministrador(rs.getBoolean("administrador"));

                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al buscar el usuario por ID", e);
            }
        }

        return usuarioAux;
    }

}
