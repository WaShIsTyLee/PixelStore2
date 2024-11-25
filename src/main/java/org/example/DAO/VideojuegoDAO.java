package org.example.DAO;


import org.example.BaseDatos.ConnectionDB;
import org.example.Model.Videojuego;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VideojuegoDAO {
    private final static String INSERTE ="INSERT INTO videojuego(nombre,precio,descripcion,id_desarrollador) VALUES (?, ?, ?, ?)";
    private final static String DELETE ="DELETE FROM videojuego WHERE id_videojuego=?";
    private final static String FINDBYID ="SELECT v.id_videojuego,v.nombre,v.precio,v.descripcion,v.id_desarrollador FROM videojuego AS v WHERE v.id_videojuego=?";
    private final static String LISTGAMES = "SELECT v.nombre,v.precio,v.descripcion FROM videojuego AS v";
    public Videojuego save(Videojuego entity){
        Videojuego result = entity;
        if (entity == null) return result;
        if (entity.getId_videojuego()==0){
        try(PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(INSERTE, Statement.RETURN_GENERATED_KEYS)){
            pst.setString(1, entity.getNombre());
            pst.setFloat(2, entity.getPrecio());
            pst.setString(3, entity.getDescripcion());
            pst.setInt(4, entity.getDesarrollador().getId_desarrollador());
            pst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        }else {
            //update que lo busca por id
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

                    result = v;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Hay que cambiar la accion de la base de datos para que no sea cascade
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
                Videojuego v = new Videojuego();
                v.setNombre(res.getString("nombre"));
                v.setPrecio(res.getFloat("precio"));
                v.setDescripcion(res.getString("descripcion"));
                result.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
