package org.example.Model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@XmlRootElement(name = "Usuario")
@XmlAccessorType(XmlAccessType.FIELD)
public class Usuario {
   private int id_usuario;
   @XmlElement(name = "NombreUsuario")
   public String nombre;
   private String email;
   private String contrasena;
   private boolean administrador;
   @XmlElement(name = "Carrito")
   public ArrayList<Videojuego> carrito = new ArrayList<>();
   @XmlElement(name = "PrecioTotal")
   public float precioTotal;

   public Usuario(int id_usuario, String nombre, String email, String contrasena, boolean administrador, ArrayList<Videojuego> carrito) {
      this.id_usuario = id_usuario;
      this.nombre = nombre;
      this.email = email;
      this.contrasena = contrasena;
      this.administrador = administrador;
      this.carrito = carrito;
   }


   public String hashPassword(String pass) {
      try {
         MessageDigest digest = MessageDigest.getInstance("SHA-256");
         byte[] hashedBytes = digest.digest(pass.getBytes());

         StringBuilder stringBuilder = new StringBuilder();
         for (byte b : hashedBytes) {
            stringBuilder.append(String.format("%02x", b));
         }
         return stringBuilder.toString();
      } catch (NoSuchAlgorithmException e) {
         throw new RuntimeException("SHA-256 algorithm not found", e);
      }
   }

   public ArrayList<Videojuego> getCarrito() {
      return carrito;
   }

   public void setCarrito(ArrayList<Videojuego> carrito) {
      for (Videojuego videojuego: carrito){
         this.precioTotal = videojuego.getPrecio()+ precioTotal;
      }
      this.carrito = carrito;
   }
   public float getPrecioTotal() {
      return precioTotal;
   }

   public void setPrecioTotal(float precioTotal) {
      this.precioTotal = precioTotal;
   }

   public Usuario(int id_usuario, String nombre, String email, String contrasena, boolean administrador) {
      this.id_usuario = id_usuario;
      this.nombre = nombre;
      this.email = email;
      this.contrasena = contrasena;
      this.administrador = administrador;
   }

   public Usuario(String nombre, String email, String contrasena, boolean administrador) {
      this.nombre = nombre;
      this.email = email;
      this.contrasena = contrasena;
      this.administrador = administrador;
   }

   public Usuario(int id_usuario) {
      this.id_usuario = id_usuario;
   }

   public Usuario() {
   }
   public Usuario(String email) {
      this.email = email;
   }

   public int getId_usuario() {
      return id_usuario;
   }

   public void setId_usuario(int id_usuario) {
      this.id_usuario = id_usuario;
   }

   public String getNombre() {
      return nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getContrasena() {
      return contrasena;
   }

   public void setContrasena(String contrasena) {
      this.contrasena = contrasena;
   }

   public boolean isAdministrador() {
      return administrador;
   }

   public void setAdministrador(boolean administrador) {
      this.administrador = administrador;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Usuario usuario = (Usuario) o;
      return Objects.equals(email, usuario.email);
   }

   public static boolean isAdministrator(String email) {
      String regex = "^[a-zA-Z0-9._%+-]+@pixelstore\\.[a-zA-Z]{2,}$";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(email);
      return matcher.matches();
   }
   public static boolean isValidEmail(String email) {
      String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(email);
      return matcher.matches();
   }


   @Override
   public int hashCode() {
      return Objects.hashCode(email);
   }

   @Override
   public String toString() {
      return "Usuario{" +
              "id_usuario=" + id_usuario +
              ", nombre='" + nombre + '\'' +
              ", email='" + email + '\'' +
              ", contrasena='" + contrasena + '\'' +
              ", administrador=" + administrador +
              '}';
   }
}
