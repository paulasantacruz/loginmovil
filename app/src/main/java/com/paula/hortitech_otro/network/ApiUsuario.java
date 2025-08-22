package com.paula.hortitech_otro.network;
import com.paula.hortitech_otro.models.EmailRequest;
import com.paula.hortitech_otro.models.LoginRequest;
import com.paula.hortitech_otro.models.LoginResponse;
import com.paula.hortitech_otro.models.Persona;
import com.paula.hortitech_otro.models.ResetPasswordRequest;
import com.paula.hortitech_otro.models.VerifyCodeRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiUsuario {
    @POST("api/auth/login")
    Call<LoginResponse> loginUsuario(@Body LoginRequest loginRequest);

    @GET("api/user/profile")
    Call<Persona> getAuthenticatedUserProfile(@Header("Authorization") String authToken);

    @PUT("api/user/profile")
    Call<ResponseBody> updateAuthenticatedUserProfile(@Header("Authorization") String authToken, @Body Persona persona);

    @POST("api/auth/send-reset-code")
    Call<ResponseBody> sendPasswordResetCode(@Body EmailRequest emailRequest);

    @POST("api/auth/verify-reset-code")
    Call<ResponseBody> verifyPasswordResetCode(@Body VerifyCodeRequest verifyCodeRequest);

    @POST("api/auth/reset-password")
    Call<ResponseBody> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);
}