package org.example.Model;

import java.util.Objects;

public class Tienda {
    private int id_tienda;
    private String ubicacion;
    private String telefono;

    public Tienda(int id_tienda, String ubicacion, String telefono) {
        this.id_tienda = id_tienda;
        this.ubicacion = ubicacion;
        this.telefono = telefono;
    }

    public Tienda(String ubicacion, String telefono) {

        this.ubicacion = ubicacion;
        this.telefono = telefono;
    }


    public Tienda() {

    }

    public int getId_tienda() {
        return id_tienda;
    }

    public void setId_tienda(int id_tienda) {
        this.id_tienda = id_tienda;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tienda tienda = (Tienda) o;
        return id_tienda == tienda.id_tienda;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id_tienda);
    }

    @Override
    public String toString() {
        return "Tienda{" +
                "id_tienda=" + id_tienda +
                ", ubicacion='" + ubicacion + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
