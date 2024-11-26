package org.example.Model;

import java.util.ArrayList;
import java.util.Objects;

public class Desarrollador {
    private int id_desarrollador;
    private String nombre;
    private String pais;
    private ArrayList<Videojuego> videojuegos;

    public Desarrollador(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
    }

    public Desarrollador(int id_desarrollador) {
        this.id_desarrollador = id_desarrollador;
    }

    public Desarrollador(int id_desarrollador, String nombre, String pais) {
        this.id_desarrollador = id_desarrollador;
        this.nombre = nombre;
        this.pais = pais;
    }

    public Desarrollador() {}

    public int getId_desarrollador() {
        return id_desarrollador;
    }

    public void setId_desarrollador(int id_desarrollador) {
        this.id_desarrollador = id_desarrollador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public ArrayList<Videojuego> getVideojuegos() {
        return videojuegos;
    }
    public void setVideojuegos(ArrayList<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Desarrollador that = (Desarrollador) o;
        return id_desarrollador == that.id_desarrollador;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_desarrollador);
    }

    @Override
    public String toString() {
        return "Desarrollador{" +
                "id_desarrollador=" + id_desarrollador +
                ", nombre='" + nombre + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }
}
