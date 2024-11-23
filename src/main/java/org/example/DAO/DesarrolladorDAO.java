package org.example.DAO;

import org.example.BaseDatos.ConnectionDB;
import org.example.Model.Desarrollador;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DesarrolladorDAO {
    private final static String INSERTE ="INSERT INTO Desarrollador(nombre,pais) VALUES (?, ?)";
    private final static String FINDBYID = "SELECT d.* FROM Desarrollador AS d WHERE d.id_desarrollador=?";

    public Desarrollador save(Desarrollador entity){
        Desarrollador result = entity;
        if (entity == null) return result;
        if (entity.getId_desarrollador()==0){
            try(PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(INSERTE, Statement.RETURN_GENERATED_KEYS)){
                pst.setString(1, entity.getNombre());
                pst.setString(2, entity.getPais());

                pst.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }else {
            //update que lo busca por id
        }
        return result;
    }
    public Desarrollador findByID(int id){
        Desarrollador result = null;
        if (id != 0){
           try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(FINDBYID)) {
              pst.setInt(1, id);
              try(ResultSet res = pst.executeQuery()) {
                  if (res.next()){
                      Desarrollador d = new Desarrollador();
                      d.setId_desarrollador(res.getInt("id_desarrollador"));
                      d.setNombre(res.getString("nombre"));
                      d.setPais(res.getString("pais"));

                      result = d;
                  }
              }
           }catch (SQLException e){
               e.printStackTrace();
           }
        }

        return result;
    }
}
