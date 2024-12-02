package org.example.DAO.MySQL;


import org.example.BaseDatos.ConnectionDB;
import org.example.Model.Videojuego;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VideojuegoDAO {
    private final static String INSERTE = "INSERT INTO videojuego(nombre,precio,descripcion,id_desarrollador,foto) VALUES (?, ?, ?, ?,?)";
    private final static String DELETE = "DELETE FROM videojuego WHERE id_videojuego=?";
    private final static String FINDBYID = "SELECT v.id_videojuego,v.nombre,v.precio,v.descripcion,v.id_desarrollador, v.foto FROM videojuego AS v WHERE v.id_videojuego=?";
    private final static String LISTGAMES = "SELECT v.id_videojuego, v.nombre,v.precio,v.descripcion, v.id_desarrollador, v.foto FROM videojuego AS v GROUP BY v.precio DESC";
    private final static String FINDALLNAMES = "SELECT v.nombre FROM videojuego AS v GROUP BY V.nombre";
    private final static String UPDATE = "UPDATE videojuego SET nombre=?, precio=?, descripcion=?, id_desarrollador=?,foto=? WHERE id_videojuego=?";


    public Videojuego save(Videojuego videojuego) {
        Videojuego result = videojuego;
        if (videojuego == null) return result;
        if (videojuego.getId_videojuego() == 0) {
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(INSERTE, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, videojuego.getNombre());
                pst.setFloat(2, videojuego.getPrecio());
                pst.setString(3, videojuego.getDescripcion());
                pst.setInt(4, videojuego.getDesarrollador().getId_desarrollador());
                pst.setString(5, videojuego.getRutaImagen());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
        }
        return result;
    }


    public ArrayList<String> findAllNames() {
        ArrayList<String> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(FINDALLNAMES)) {
            try (ResultSet res = pst.executeQuery()) {
                while (res.next()) {
                    result.add(res.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Videojuego findById(int id){
        Videojuego result = null;
        DesarrolladorDAO desDAO = new DesarrolladorDAO();
        try(PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(FINDBYID)) {
            pst.setInt(1, id);
            try (ResultSet res = pst.executeQuery()) {
                if(res.next()){
                    Videojuego v = new Videojuego();
                    v.setId_videojuego(res.getInt("id_videojuego"));
                    v.setNombre(res.getString("nombre"));
                    v.setPrecio(res.getFloat("precio"));
                    v.setDescripcion(res.getString("descripcion"));
                    v.setDesarrollador(desDAO.findByID(res.getInt("id_desarrollador")));
                    v.setRutaImagen(res.getString("foto"));

                    result = v;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public Videojuego delete(Videojuego entity){
        if (entity !=null){
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(DELETE)){
                pst.setInt(1, entity.getId_videojuego());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                entity = null;
            }
        }
        return entity;
    }
    public List<Videojuego> gameList(){
        List<Videojuego> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(LISTGAMES)) {
            ResultSet res = pst.executeQuery();
            while (res.next()){
                DesarrolladorDAO dao = new DesarrolladorDAO();
                Videojuego v = new Videojuego();
                v.setId_videojuego(res.getInt("id_videojuego"));
                v.setNombre(res.getString("nombre"));
                v.setPrecio(res.getFloat("precio"));
                v.setDescripcion(res.getString("descripcion"));
                v.setDesarrollador(dao.findByID(res.getInt("id_desarrollador")));
                v.setRutaImagen(res.getString("foto"));

                result.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void update(Videojuego entity) {
        if (entity != null) {
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(UPDATE)) {
                pst.setString(1, entity.getNombre());
                pst.setFloat(2, entity.getPrecio());
                pst.setString(3, entity.getDescripcion());
                pst.setInt(4, entity.getDesarrollador().getId_desarrollador());
                pst.setString(5,entity.getRutaImagen());
                pst.setInt(6, entity.getId_videojuego());

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
