package org.example.DAO.SQLite;

import org.example.BaseDatos.ConnectionSQLite;
import org.example.Model.Videojuego;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VideojuegoDAOSqlite {

    private final static String INSERTE = "INSERT INTO videojuego(nombre, precio, descripcion, id_desarrollador, foto) VALUES (?, ?, ?, ?, ?)";
    private final static String DELETE = "DELETE FROM videojuego WHERE id_videojuego=?";
    private final static String UPDATE = "UPDATE videojuego SET nombre=?, precio=?, descripcion=?, id_desarrollador=?, foto=? WHERE id_videojuego=?";

    public Videojuego save(Videojuego videojuego) {
        if (videojuego != null) {
            try (PreparedStatement pst = ConnectionSQLite.getConnection().prepareStatement(INSERTE)) {
                pst.setString(1, videojuego.getNombre());
                pst.setFloat(2, videojuego.getPrecio());
                pst.setString(3, videojuego.getDescripcion());
                pst.setInt(4, videojuego.getDesarrollador().getId_desarrollador());
                pst.setString(5, videojuego.getRutaImagen());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return videojuego;
    }


    public Videojuego delete(Videojuego videojuego) {
        if (videojuego != null) {
            try (PreparedStatement pst = ConnectionSQLite.getConnection().prepareStatement(DELETE)) {
                pst.setInt(1, videojuego.getId_videojuego());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                videojuego = null;
            }
        }
        return videojuego;
    }

    public void update(Videojuego videojuego) {
        if (videojuego != null) {
            try (PreparedStatement pst = ConnectionSQLite.getConnection().prepareStatement(UPDATE)) {
                pst.setString(1, videojuego.getNombre());
                pst.setFloat(2, videojuego.getPrecio());
                pst.setString(3, videojuego.getDescripcion());
                pst.setInt(4, videojuego.getDesarrollador().getId_desarrollador());
                pst.setString(5, videojuego.getRutaImagen());
                pst.setInt(6, videojuego.getId_videojuego());

                int filasActualizadas = pst.executeUpdate();

                if (filasActualizadas > 0) {
                    System.out.println("Éxito: Videojuego actualizado correctamente.");
                } else {
                    System.out.println("Advertencia: No se encontró el videojuego para actualizar.");
                }
            } catch (SQLException e) {

                System.err.println("Error durante la actualización: " + e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Error: La entidad proporcionada es nula.");
        }
    }
}
