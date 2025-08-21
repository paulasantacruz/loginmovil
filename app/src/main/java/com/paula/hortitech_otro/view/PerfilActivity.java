package com.paula.hortitech_otro.view;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.paula.hortitech_otro.R;
import com.paula.hortitech_otro.models.Persona;
import com.paula.hortitech_otro.network.ApiClient;
import com.paula.hortitech_otro.network.ApiUsuario;
import com.paula.hortitech_otro.controllers.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity {
    private TextView tvNombreUsuario, tvCorreo, tvRol, tvEstado;
    private ImageView ivFotoPerfil;
    private SessionManager sessionManager;
    private Persona perfilActual;

    private final ActivityResultLauncher<Intent> editProfileLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    cargarDatosPerfil();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        sessionManager = new SessionManager(this);

        tvNombreUsuario = findViewById(R.id.tvNombreUsuario);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvRol = findViewById(R.id.tvRol);
        tvEstado = findViewById(R.id.tvEstado);
        ivFotoPerfil = findViewById(R.id.ivFotoPerfil);
        Button btnEditarPerfil = findViewById(R.id.btnEditarPerfil);
        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        cargarDatosPerfil();

        btnEditarPerfil.setOnClickListener(v -> {
            if (perfilActual != null) {
                Intent intent = new Intent(this, EditarPerfilActivity.class);
                intent.putExtra("PERFIL_ACTUAL", perfilActual);
                editProfileLauncher.launch(intent);
            }
        });

        btnCerrarSesion.setOnClickListener(v -> sessionManager.logoutUser());
    }

    private void cargarDatosPerfil() {
        ApiUsuario api = ApiClient.getClient().create(ApiUsuario.class);
        api.getAuthenticatedUserProfile(sessionManager.getAuthToken()).enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, Response<Persona> response) {
                if (response.isSuccessful() && response.body() != null) {
                    perfilActual = response.body();
                    tvNombreUsuario.setText(perfilActual.getNombre_usuario());
                    tvCorreo.setText(perfilActual.getCorreo());
                    tvRol.setText("Rol: " + perfilActual.getRol());
                    tvEstado.setText("Estado: " + perfilActual.getEstado());

                    if (perfilActual.getPerfil() != null && perfilActual.getPerfil().getFoto_url() != null) {
                        Glide.with(PerfilActivity.this)
                                .load(perfilActual.getPerfil().getFoto_url())
                                .circleCrop()
                                .placeholder(R.drawable.ic_profile_placeholder)
                                .into(ivFotoPerfil);
                    }
                }
            }
            @Override
            public void onFailure(Call<Persona> call, Throwable t) {
                Toast.makeText(PerfilActivity.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}