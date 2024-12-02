package org.example.DAO.MySQL;

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
    private final static String FINDBYEMAILNECESARY ="SELECT id_usuario,nombre, email FROM usuario WHERE email=?";
    private final static String FINDALLEMAILS = "SELECT email FROM usuario";
    private final static String FINDGAMEUSER = "SELECT v.nombre, v.precio, v.foto, uv.fecha_compra  FROM videojuego v  " +
            "JOIN usuariovideojuego uv ON v.id_videojuego = uv.id_videojuego  JOIN usuario u ON uv.id_usuario = u.id_usuario  WHERE u.id_usuario = ?";
    private final static String FINDGAMEUSERXML = "SELECT v.nombre, v.precio,uv.fecha_compra  FROM videojuego v  " +
            "JOIN usuariovideojuego uv ON v.id_videojuego = uv.id_videojuego  JOIN usuario u ON uv.id_usuario = u.id_usuario  WHERE u.id_usuario = ?";
    private final static String DELETECARRITO = "DELETE FROM usuariovideojuego WHERE id_usuario = ?";

    public void deleteCarrito(Usuario usuario) {
        if (usuario != null || usuario.getId_usuario() > 0) {
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(DELETECARRITO)) {
                pst.setInt(1, usuario.getId_usuario());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    public boolean insertarVideojuegosEnCarrito(Usuario usuario, Videojuego videojuego) {
        boolean success = false;
        if (usuario == null || videojuego == null) {
            System.out.println("Error: Usuario o Videojuego nulos.");
            return false;
        }
        String ADDTOCARRITO = "INSERT INTO usuariovideojuego (id_usuario, id_videojuego, fecha_compra) VALUES (?, ?, ?)";
        try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(ADDTOCARRITO)) {
            pst.setInt(1, usuario.getId_usuario());
            pst.setInt(2, videojuego.getId_videojuego());
            pst.setDate(3, new java.sql.Date(System.currentTimeMillis()));
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
    public ArrayList<Videojuego> gammerUser(Usuario usuario){
        ArrayList<Videojuego> result = new ArrayList<>();
        if (usuario != null){
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(FINDGAMEUSER)) {
                pst.setInt(1,usuario.getId_usuario());
                try (ResultSet res = pst.executeQuery()){
                    while (res.next()){
                        Videojuego v = new Videojuego();
                        v.setNombre(res.getString("nombre"));
                        v.setPrecio(res.getFloat("precio"));
                        v.setRutaImagen(res.getString("foto"));
                        v.setFechaCompra(res.getDate("fecha_compra"));
                        result.add(v);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    public Usuario findByEmailNecesary(Usuario usuario){
        Usuario usuarioAux = null;

        if (usuario != null && usuario.getEmail() != null) {
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(FINDBYEMAILNECESARY)) {
                pst.setString(1, usuario.getEmail());

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        usuarioAux = new Usuario();
                        usuarioAux.setId_usuario(rs.getInt("id_usuario"));
                        usuarioAux.setNombre(rs.getString("nombre"));
                        usuarioAux.setEmail(rs.getString("email"));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error al buscar el usuario por email", e);
            }
        }
        return usuarioAux;
    }
    public ArrayList<Videojuego> gammerUserXML(Usuario usuario){
        ArrayList<Videojuego> result = new ArrayList<>();
        if (usuario != null){
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(FINDGAMEUSERXML)) {
                pst.setInt(1,usuario.getId_usuario());

                try (ResultSet res = pst.executeQuery()){
                    while (res.next()){
                        Videojuego v = new Videojuego();
                        v.setNombre(res.getString("nombre"));
                        v.setPrecio(res.getFloat("precio"));
                        v.setFechaCompra(res.getDate("fecha_compra"));
                        result.add(v);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
