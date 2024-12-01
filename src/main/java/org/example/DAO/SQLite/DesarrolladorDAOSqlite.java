package org.example.DAO.SQLite;

import org.example.BaseDatos.ConnectionSQLite;
import org.example.Model.Desarrollador;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DesarrolladorDAOSqlite {

    private final static String INSERT = "INSERT INTO Desarrollador(nombre, pais) VALUES (?, ?)";
    private final static String UPDATE = "UPDATE Desarrollador SET nombre=?, pais=? WHERE id_desarrollador=?";
    private final static String DELETE = "DELETE FROM Desarrollador WHERE id_desarrollador=?";

    public Desarrollador save(Desarrollador entity) {
        Desarrollador result = entity;
        if (entity == null) return result;
        if (entity.getId_desarrollador() == 0) {
            try (PreparedStatement pst = ConnectionSQLite.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                pst.setString(1, entity.getNombre());
                pst.setString(2, entity.getPais());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try (PreparedStatement pst = ConnectionSQLite.getConnection().prepareStatement(UPDATE)) {
                pst.setString(1, entity.getNombre());
                pst.setString(2, entity.getPais());
                pst.setInt(3, entity.getId_desarrollador());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public Desarrollador delete(Desarrollador entity) {
        if (entity != null) {
            try (PreparedStatement pst = ConnectionSQLite.getConnection().prepareStatement(DELETE)) {
                pst.setInt(1, entity.getId_desarrollador());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                entity = null;
            }
        }
        return entity;
    }
}

