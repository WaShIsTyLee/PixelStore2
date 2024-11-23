package org.example.DAO;


import org.example.BaseDatos.ConnectionDB;
import org.example.Model.Videojuego;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class VideojuegoDAO {
    private final static String INSERTE ="INSERT INTO videojuego(nombre,precio,descripcion,id_desarrollador) VALUES (?, ?, ?, ?)";
    private final static String DELETE ="DELETE FROM videojuego WHERE id_videojuego=?";
    private final static String FINDBYID ="SELECT v.id_videojuego,v.nombre,v.precio,v.descripcion,id_desarrollador FROM videojuegos AS v WHERE v.id_videojuego=?";
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
}
