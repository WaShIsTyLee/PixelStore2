package org.example.DAO;

import org.example.BaseDatos.ConnectionDB;
import org.example.Model.Tienda;
import org.example.Model.Videojuego;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TiendaDAO {
    private final static String DELETE = "DELETE FROM tienda  WHERE id_tienda = ?";
    private final static String INSERT = "INSERT INTO tienda (ubicacion,telefono) VALUES (?,?)";
    private final static String GETBYID = "SELECT * FROM tienda WHERE id_tienda = ?";
    private final static String FINDALL = "SELECT * FROM tienda";
    private final static String ADDTOGAMESSHOPS = "INSERT INTO tiendavideojuego (id_videojuego, id_tienda) VALUES (?,?)";
    private final static String GETFROMGAMESSHOPS =
            "SELECT v.id_videojuego, v.nombre, v.precio, v.descripcion, v.id_desarrollador " +
                    "FROM tiendavideojuego tv " +
                    "JOIN videojuego v ON tv.id_videojuego = v.id_videojuego " +
                    "WHERE tv.id_tienda = ?";

        public ArrayList<Videojuego> buscarJuegosDeTienda(Tienda tienda) {
            ArrayList<Videojuego> videojuegos = new ArrayList<>();
            if (tienda != null && tienda.getId_tienda() > 0) {
                try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(GETFROMGAMESSHOPS)) {
                    pst.setInt(1, tienda.getId_tienda());
                    try (ResultSet rs = pst.executeQuery()) {
                        while (rs.next()) {
                            Videojuego videojuego = new Videojuego();
                            videojuego.setId_videojuego(rs.getInt("id_videojuego"));
                            videojuego.setNombre(rs.getString("nombre"));
                            videojuego.setPrecio(rs.getFloat("precio"));
                            videojuego.setDescripcion(rs.getString("descripcion"));
                            videojuego.setDesarrollador(new DesarrolladorDAO().findByID(rs.getInt("id_desarrollador")));
                            videojuegos.add(videojuego);
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("Error al buscar juegos de tienda: " + e.getMessage());
                }
            }
            return videojuegos; // Devuelve la lista de videojuegos.
        }

    public Tienda insertarTiendaVideojuego(Tienda tienda) {
        if (tienda != null && tienda.getId_tienda() > 0) {
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(ADDTOGAMESSHOPS)) {
                ArrayList<Videojuego> videojuegos = tienda.getVideojuegos();
                for (Videojuego videojuego : videojuegos) {
                    pst.setInt(1, videojuego.getId_videojuego());
                    pst.setInt(2, tienda.getId_tienda());
                    pst.executeUpdate();
                }
            } catch (SQLException e) {

                System.out.println("Error al insertar tienda y videojuegos: " + e.getMessage());
            }
        } else {
            System.out.println("Tienda inválida o sin ID.");
        }
        return tienda; // Devolvemos la tienda
    }



            public ArrayList<Tienda> findAll () {
                ArrayList<Tienda> tiendas = new ArrayList<>();
                try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(FINDALL)) {
                    try (ResultSet rs = pst.executeQuery()) {
                        while (rs.next()) {
                            Tienda tienda = new Tienda();
                            tienda.setId_tienda(rs.getInt("id_tienda"));
                            tienda.setUbicacion(rs.getString("ubicacion"));
                            tienda.setTelefono(rs.getString("telefono"));
                            tiendas.add(tienda);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return tiendas;
            }

            public Tienda delete (Tienda tienda){
                if (tienda != null || tienda.getId_tienda() > 0) {
                    try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(DELETE)) {
                        pst.setInt(1, tienda.getId_tienda());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return tienda;
            }
            public Tienda getTienda (Tienda tienda){
                Tienda tiendaaux = null; // Inicializamos como null para manejar casos sin resultados.
                String GETBYID = "SELECT id, ubicacion, telefono FROM tienda WHERE id = ?"; // Consulta de ejemplo.

                if (tienda.getId_tienda() > 0) {
                    try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(GETBYID)) {
                        pst.setInt(1, tienda.getId_tienda());

                        try (ResultSet rs = pst.executeQuery()) {
                            if (rs.next()) {
                                tiendaaux = new Tienda();
                                tiendaaux.setId_tienda(rs.getInt("id"));
                                tiendaaux.setUbicacion(rs.getString("ubicacion"));
                                tiendaaux.setTelefono(rs.getString("telefono"));
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return tiendaaux;
            }


            public Tienda insert (Tienda tienda){
                if (tienda != null || tienda.getId_tienda() > 0) {
                    try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(INSERT)) {
                        pst.setString(1, tienda.getUbicacion());
                        pst.setString(2, tienda.getTelefono());
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                return tienda;

            }

        }
