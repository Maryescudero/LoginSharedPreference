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
import com.ulp.loginsharedpreference.databinding.ActivityMainBinding;
import com.ulp.loginsharedpreference.model.Usuario;
import com.ulp.loginsharedpreference.ui.registro.RegistroActivity;

public class MainActivity extends AppCompatActivity {


        private MainActivityViewModel mainActivityViewModel;
        private ActivityMainBinding binding; // variables

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityMainBinding.inflate(getLayoutInflater()); // Inflo el layout
            setContentView(binding.getRoot());

            // Inicializamos el viewModel
            mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

            // Observer el resultado del login
            mainActivityViewModel.getLoginSuccess().observe(this, usuario -> {
                if (usuario != null) {
                    goToRegistroActivity(usuario);
                }
            });

            // Observer  errores de login
            mainActivityViewModel.getLoginError().observe(this, errorMessage -> {
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            });

            // Evento del botpn ingresar
            binding.btnIngresar.setOnClickListener(view -> {
                String mail = binding.etUsuario.getText().toString();
                String password = binding.etPassword.getText().toString();
                mainActivityViewModel.login(this, mail, password); // llamo metodo
            });

            // Evento del boton registrar
            binding.btnRegistrar.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
                startActivity(intent);
            });
        }

        private void goToRegistroActivity(Usuario usuario) {
            Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
            intent.putExtra("usuario", usuario); // aqui uso el serializable
            startActivity(intent);
            finish();
        }
    }
