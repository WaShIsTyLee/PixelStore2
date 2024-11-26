package org.example.DAO;

import org.example.BaseDatos.ConnectionDB;
import org.example.Model.Desarrollador;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DesarrolladorDAO {
    private final static String INSERTE ="INSERT INTO Desarrollador(nombre,pais) VALUES (?, ?)";
    private final static String FINDBYID = "SELECT d.* FROM Desarrollador AS d WHERE d.id_desarrollador=?";
    private final static String DELETE = "DELETE FROM desarrollador WHERE id_desarrollador";
    private final static String FINDALL = "SELECT * FROM Desarrollador";
    private final static String FINDBYNAME = "SELECT d.* FROM Desarrollador AS d WHERE d.nombre=?";

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
    public Desarrollador findByName(String name){
        Desarrollador result = null;
        if (name != null){
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(FINDBYNAME)) {
                pst.setString(1, name);
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


    public ArrayList<Desarrollador> findAll(){
        ArrayList<Desarrollador> result = new ArrayList<>();
        try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(FINDALL)){
            try (ResultSet res = pst.executeQuery()){
                while (res.next()){
                    Desarrollador d = new Desarrollador();
                    d.setId_desarrollador(res.getInt("id_desarrollador"));
                    d.setNombre(res.getString("nombre"));
                    d.setPais(res.getString("pais"));
                    result.add(d);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    //Se tiene que cambiar la accion que hace en la base de datos
    public Desarrollador delete(Desarrollador entity){
        if (entity != null){
            try (PreparedStatement pst = ConnectionDB.getConnection().prepareStatement(DELETE)){
                pst.setInt(1,entity.getId_desarrollador());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                entity = null;
            }
        }
        return entity;
    }
}
