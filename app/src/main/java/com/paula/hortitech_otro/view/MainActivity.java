package com.paula.hortitech_otro.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.paula.hortitech_otro.R;
import com.paula.hortitech_otro.models.LoginRequest;
import com.paula.hortitech_otro.models.LoginResponse;
import com.paula.hortitech_otro.network.ApiClient;
import com.paula.hortitech_otro.network.ApiUsuario;
import com.paula.hortitech_otro.view.HomeActvity;
import com.paula.hortitech_otro.controllers.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);

        if (sessionManager.getAuthToken() != null) {
            startActivity(new Intent(this, HomeActvity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        TextInputEditText etCorreo = findViewById(R.id.etCorreo);
        TextInputEditText etContrasena = findViewById(R.id.etContrasena);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString().trim();
            String contrasena = etContrasena.getText().toString().trim();

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            iniciarSesion(correo, contrasena);
        });
    }

    private void iniciarSesion(String correo, String contrasena) {
        ApiUsuario api = ApiClient.getClient().create(ApiUsuario.class);
        api.loginUsuario(new LoginRequest(correo, contrasena)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    sessionManager.saveAuthToken(response.body().getToken());
                    sessionManager.saveUserId(response.body().getUser().getId_persona());

                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, HomeActvity.class));
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}