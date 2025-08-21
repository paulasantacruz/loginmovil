package com.paula.hortitech_otro.view;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.paula.hortitech_otro.R;
import com.paula.hortitech_otro.models.Persona;
import com.paula.hortitech_otro.network.ApiClient;
import com.paula.hortitech_otro.network.ApiUsuario;
import com.paula.hortitech_otro.controllers.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPerfilActivity extends AppCompatActivity {
    private TextInputEditText etNombreUsuario, etCorreo, etContrasena;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        sessionManager = new SessionManager(this);

        etNombreUsuario = findViewById(R.id.etNombreUsuario);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        Button btnGuardarCambios = findViewById(R.id.btnGuardarCambios);

        if (getIntent().hasExtra("PERFIL_ACTUAL")) {
            Persona perfilActual = (Persona) getIntent().getSerializableExtra("PERFIL_ACTUAL");
            etNombreUsuario.setText(perfilActual.getNombre_usuario());
            etCorreo.setText(perfilActual.getCorreo());
        }

        btnGuardarCambios.setOnClickListener(v -> guardarCambios());
    }

    private void guardarCambios() {
        String nombre = etNombreUsuario.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();

        Persona personaActualizada = new Persona();
        if (!nombre.isEmpty()) personaActualizada.setNombre_usuario(nombre);
        if (!correo.isEmpty()) personaActualizada.setCorreo(correo);

        ApiUsuario api = ApiClient.getClient().create(ApiUsuario.class);
        api.updateAuthenticatedUserProfile(sessionManager.getAuthToken(), personaActualizada).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditarPerfilActivity.this, "Perfil actualizado", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(EditarPerfilActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditarPerfilActivity.this, "Fallo de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}