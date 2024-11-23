package org.example.Model;

import java.util.Objects;

public class Videojuego {
    private int id_videojuego;
    private String nombre;
    private String descripcion;
    private float precio;
    private Desarrollador desarrollador;

    public Videojuego(String nombre, String descripcion, float precio, Desarrollador desarrollador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.desarrollador = desarrollador;
    }

    public int getId_videojuego() {
        return id_videojuego;
    }

    public void setId_videojuego(int id_videojuego) {
        this.id_videojuego = id_videojuego;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Desarrollador getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(Desarrollador desarrollador) {
        this.desarrollador = desarrollador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Videojuego that = (Videojuego) o;
        return id_videojuego == that.id_videojuego;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_videojuego);
    }

    @Override
    public String toString() {
        return "Videojuego{" +
                "id_videojuego=" + id_videojuego +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", desarrollador=" + desarrollador +
                '}';
    }
}
