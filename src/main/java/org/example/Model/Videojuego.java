package org.example.Model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;
import java.util.Objects;
@XmlRootElement(name = "Videojuego")
@XmlAccessorType(XmlAccessType.FIELD)
public class Videojuego {
    private int id_videojuego;
    @XmlElement(name = "NombreJuego")
    public String nombre;
    private String descripcion;
    @XmlElement(name = "PrecioJuego")
    public float precio;
    private Desarrollador desarrollador;
    private String rutaImagen;
    private Date fechaCompra;

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Videojuego(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public Videojuego(int id_videojuego, String nombre, String descripcion, float precio, Desarrollador desarrollador, String rutaImagen) {
        this.id_videojuego = id_videojuego;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.desarrollador = desarrollador;
        this.rutaImagen = rutaImagen;
    }

    public Videojuego(String nombre, float precio, String rutaImagen, Date fechaCompra) {
        this.nombre = nombre;
        this.precio = precio;
        this.rutaImagen = rutaImagen;
        this.fechaCompra = fechaCompra;
    }

    public Videojuego() {
    }

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
                ", rutaImagen='" + rutaImagen + '\'' +
                '}';
    }
}
