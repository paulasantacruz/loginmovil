package com.paula.hortitech_otro.network;
import com.paula.hortitech_otro.models.LoginRequest;
import com.paula.hortitech_otro.models.LoginResponse;
import com.paula.hortitech_otro.models.Persona;
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
}