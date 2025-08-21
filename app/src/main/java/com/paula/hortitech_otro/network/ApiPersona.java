package com.paula.hortitech_otro.network;
import com.paula.hortitech_otro.models.Persona;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiPersona {
    @GET("api/persona")
    Call<List<Persona>> getPersona();
    @GET("api/persona/operarios")
    Call<List<Persona>> getOperarios();
    @GET("api/persona/activos")
    Call<List<Persona>> getActivos();
}
