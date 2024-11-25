package org.example.Model;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Usuario {
   private int id_usuario;
   private String nombre;
   private String email;
   private String contrasena;
   private boolean administrador;

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
