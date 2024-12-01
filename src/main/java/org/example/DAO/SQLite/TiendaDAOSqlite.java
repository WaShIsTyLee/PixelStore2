package org.example.DAO.SQLite;

import org.example.BaseDatos.ConnectionSQLite;
import org.example.Model.Tienda;
import org.example.Model.Videojuego;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class TiendaDAOSqlite {
    private final static String DELETE = "DELETE FROM tienda WHERE id_tienda = ?";
    private final static String INSERT = "INSERT INTO tienda (ubicacion, telefono) VALUES (?, ?)";
    private final static String UPDATE = "UPDATE tienda SET ubicacion = ?, telefono = ? WHERE id_tienda = ?";
    private final static String ADDTOGAMESSHOPS = "INSERT INTO tiendavideojuego (id_videojuego, id_tienda) VALUES (?, ?)";
    private final static String DELETEFROMGAMESHOPS = "DELETE FROM tiendavideojuego WHERE id_videojuego = ?";


    public void eliminarJuegosDeTienda(Tienda tienda, ArrayList<Videojuego> videojuegosAEliminar) {
        if (tienda != null && tienda.getId_tienda() > 0) {  // Verificar que la tienda sea válida
            for (Videojuego videojuego : videojuegosAEliminar) {
                try (PreparedStatement pst = ConnectionSQLite.getConnection().prepareStatement(DELETEFROMGAMESHOPS)) {
                    pst.setInt(1, videojuego.getId_videojuego());  // Establecer el ID del videojuego
                    pst.setInt(2, tienda.getId_tienda());  // Establecer el ID de la tienda
                    pst.executeUpdate();  // Ejecutar la eliminación
                } catch (SQLException e) {
                    System.err.println("Error al eliminar el videojuego con ID: " + videojuego.getId_videojuego());
                    e.printStackTrace();
                }
            }
        } else {
            System.err.println("La tienda es nula o tiene un ID inválido.");
        }
    }
    public Tienda insertarTiendaVideojuego(Tienda tienda) {
        if (tienda != null && tienda.getId_tienda() > 0) {  // Solo realizamos la inserción si la tienda tiene un ID válido
            try (PreparedStatement pst = ConnectionSQLite.getConnection().prepareStatement(ADDTOGAMESSHOPS)) {
                ArrayList<Videojuego> videojuegos = tienda.getVideojuegos();  // Obtenemos la lista de videojuegos de la tienda
                for (Videojuego videojuego : videojuegos) {
                    pst.setInt(1, videojuego.getId_videojuego());  // Establecemos el ID del videojuego
                    pst.setInt(2, tienda.getId_tienda());  // Establecemos el ID de la tienda
                    pst.executeUpdate();  // Ejecutamos la inserción
                }
            } catch (SQLException e) {
                System.out.println("Error al insertar tienda y videojuegos: " + e.getMessage());
            }
        } else {
            System.out.println("Tienda inválida o sin ID.");
        }
        return tienda;  // Devolvemos la tienda
    }

    // Método para insertar tienda
    public Tienda insert(Tienda tienda) {
        if (tienda != null && tienda.getId_tienda() == 0) { // Aseguramos que la tienda no tenga ID antes de insertar
            try (PreparedStatement pst = ConnectionSQLite.getConnection().prepareStatement(INSERT)) {
                pst.setString(1, tienda.getUbicacion());
                pst.setString(2, tienda.getTelefono());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tienda;
    }

    // Método para actualizar tienda
    public void update(Tienda tienda) {
        if (tienda != null && tienda.getId_tienda() > 0) { // Solo actualizamos si la tienda tiene un ID válido
            try (PreparedStatement pst = ConnectionSQLite.getConnection().prepareStatement(UPDATE)) {
                pst.setString(1, tienda.getUbicacion());
                pst.setString(2, tienda.getTelefono());
                pst.setInt(3, tienda.getId_tienda());

                int filasActualizadas = pst.executeUpdate();
                if (filasActualizadas > 0) {
                    System.out.println("Éxito: Tienda actualizada correctamente.");
                } else {
                    System.out.println("Advertencia: No se encontró la tienda para actualizar.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para eliminar tienda
    public Tienda delete(Tienda tienda) {
        if (tienda != null && tienda.getId_tienda() > 0) { // Solo eliminamos si la tienda tiene un ID válido
            try (PreparedStatement pst = ConnectionSQLite.getConnection().prepareStatement(DELETE)) {
                pst.setInt(1, tienda.getId_tienda());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tienda;
    }
}
