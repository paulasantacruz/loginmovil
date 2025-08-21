package com.paula.hortitech_otro.models;

public class LoginRequest {
    private String correo;
    private String contrasena;

    public LoginRequest(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }
}
