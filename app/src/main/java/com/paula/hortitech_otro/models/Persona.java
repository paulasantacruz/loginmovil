package com.paula.hortitech_otro.models;
import java.io.Serializable;
public class Persona implements Serializable {
    private int id_persona;
    private String nombre_usuario;
    private String correo;
    private String rol;
    private String estado;
    private boolean isVerified;
    private Perfil perfil;

    public int getId_persona() { return id_persona; }
    public String getNombre_usuario() { return nombre_usuario; }
    public void setNombre_usuario(String nombre_usuario) { this.nombre_usuario = nombre_usuario; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getRol() { return rol; }
    public String getEstado() { return estado; }
    public boolean isVerified() { return isVerified; }
    public Perfil getPerfil() { return perfil; }
}
