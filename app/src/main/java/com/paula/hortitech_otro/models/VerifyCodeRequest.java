package com.paula.hortitech_otro.models;

public class VerifyCodeRequest {
    private String correo;
    private String verificationCode;
    public VerifyCodeRequest(String correo, String code) {
        this.correo = correo;
        this.verificationCode = code;
    }
}
