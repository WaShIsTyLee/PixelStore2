package org.example.DAO;

import org.example.BaseDatos.ConnectionDB;
import org.example.Model.Usuario;
import org.example.Model.Videojuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {
    private final static String DELETE = "DELETE FROM usuario  WHERE id_usuario = ?";
    private final static String INSERT = "INSERT INTO usuario( nombre, email, contrasena, administrador) VALUES (?,?,?,?) ";
    private final static String FINDBYEMAIL = "SELECT * FROM usuario WHERE email = ?";
    private final static String FINDALLEMAILS = "SELECT email FROM usuario";
    private final static String ADDTOCARRITO = "INSERT INTO usuariovideojuego (id_usuario, id_videojuego, fecha_compra) VALUES (?,?,?)";

    public void InsertarVideojuegosEnCarrito(Usuario usuario, ArrayList<Videojuego> videojuegos) {
        String ADDTOCARRITO = "INSERT INTO usuariovideojuego (id_usuario, id_videojuego, fecha_compra) VALUES (?,?,?)";
        try (Connection conn = ConnectionDB.getConnection()) {
            for (Videojuego videojuego : videojuegos) {
                try (PreparedStatement pst = conn.prepareStatement(ADDTOCARRITO)) {
                    pst.setInt(1, usuario.getId_usuario());
                    pst.setInt(2, videojuego.getId_videojuego());
                    pst.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                    pst.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


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

    public ArrayList<Usuario> findAllEmails() {
        ArrayList<Usuario> emails = new ArrayList<>();
        try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(FINDALLEMAILS)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    emails.add(new Usuario(rs.getString("email")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
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
                e.printStackTrace();
            }
        }
        return usuario;
    }
    // seleccionar usuario de la base de datos para comprobaciones como correo

    public Usuario findByEmail(Usuario usuario) {
        Usuario usuarioAux = null;

        if (usuario != null && usuario.getEmail() != null) {
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(FINDBYEMAIL)) {
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
                throw new RuntimeException("Error al buscar el usuario por email", e);
            }
        }
        return usuarioAux;
    }


}
