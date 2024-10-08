package com.ulp.loginsharedpreference.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.loginsharedpreference.R;
import com.ulp.loginsharedpreference.model.Usuario;
import com.ulp.loginsharedpreference.ui.registro.RegistroActivity;

public class MainActivity extends AppCompatActivity {
    //declaro variables
    private MainActivityViewModel mainActivityViewModel;
    private EditText etUsuario;
    private EditText etPassword;
    private Button btnIngresar;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicio elementos de la interfaz
        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Inicio  el ViewModel
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // Observo el resultado del login, si es valido me voy al registro
        mainActivityViewModel.getLoginSuccess().observe(this, usuario -> {
            if (usuario != null) {
                goToRegistroActivity(usuario);
            }
        });

        // Observo los errores de login y me frena el igreso
        mainActivityViewModel.getLoginError().observe(this, errorMessage -> {
            Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        });

        // evento del boton ingreso, llamo al evento login en el view
        btnIngresar.setOnClickListener(view -> {
            String mail = etUsuario.getText().toString();
            String password = etPassword.getText().toString();
            mainActivityViewModel.login(this, mail, password);
        });

        // evento del boton registrar
        btnRegistrar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
            startActivity(intent);
        });
    }

    // metodo para redirigir a mi otra acticity paso los datos del usuario
    private void goToRegistroActivity(Usuario usuario) {
        Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
        // Pasar los datos del usuario al intent
        intent.putExtra("dni", usuario.getDni());
        intent.putExtra("apellido", usuario.getApellido());
        intent.putExtra("nombre", usuario.getNombre());
        intent.putExtra("mail", usuario.getMail());
        intent.putExtra("password", usuario.getPassword());
        startActivity(intent);
        finish();  // una vezz finalizado se cierra la acivity
    }
}