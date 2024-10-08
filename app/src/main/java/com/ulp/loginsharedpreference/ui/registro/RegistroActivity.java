package com.ulp.loginsharedpreference.ui.registro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.loginsharedpreference.R;
import com.ulp.loginsharedpreference.model.Usuario;
import com.ulp.loginsharedpreference.ui.login.MainActivity;


public class RegistroActivity extends AppCompatActivity {
    //declaro variables vista y boton

    private RegistroActivityViewModel registroActivityViewModel;
    private EditText etDni, etApellido, etNombre, etMail, etPass;
    private Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // aqui inicializo  los elementos de la interfaz
        etDni = findViewById(R.id.etDni);
        etApellido = findViewById(R.id.etApellido);
        etNombre = findViewById(R.id.etNombre);
        etMail = findViewById(R.id.etMail);
        etPass = findViewById(R.id.etPass);
        btnGuardar = findViewById(R.id.btnGuardar);

        // inicio el ViewModel
        registroActivityViewModel = new ViewModelProvider(this).get(RegistroActivityViewModel.class);

        // aqui voy a recuperar los datos del intent y voy a  crear el objeto Usuario
        long dni = getIntent().getLongExtra("dni", -1);
        String apellido = getIntent().getStringExtra("apellido");
        String nombre = getIntent().getStringExtra("nombre");
        String mail = getIntent().getStringExtra("mail");
        String password = getIntent().getStringExtra("password");

        Usuario usuario = new Usuario(dni, apellido, nombre, mail, password);
        registroActivityViewModel.cargarUsuario(usuario);

        // aca voy a observar los datos del usuario desde el viewModel y actualizar la interfaz
        registroActivityViewModel.getUsuarioActual().observe(this, usuarioActual -> {
            etDni.setText(registroActivityViewModel.obtenerValorDni(usuarioActual));
            etApellido.setText(usuarioActual.getApellido() != null ? usuarioActual.getApellido() : "");
            etNombre.setText(usuarioActual.getNombre() != null ? usuarioActual.getNombre() : "");
            etMail.setText(usuarioActual.getMail() != null ? usuarioActual.getMail() : "");
            etPass.setText(usuarioActual.getPassword() != null ? usuarioActual.getPassword() : "");
        });

        // evento del boton guardar para actualizar los datos de mi usuario
        btnGuardar.setOnClickListener(view -> {
            // se me crea nuevamente el usuario pero ahora actualizado con los nuevos valors
            long nuevoDni = Long.parseLong(etDni.getText().toString().trim());
            String nuevoApellido = etApellido.getText().toString().trim();
            String nuevoNombre = etNombre.getText().toString().trim();
            String nuevoMail = etMail.getText().toString().trim();
            String nuevoPassword = etPass.getText().toString().trim();

            Usuario usuarioActualizado = new Usuario(nuevoDni, nuevoApellido, nuevoNombre, nuevoMail, nuevoPassword);
            registroActivityViewModel.registrarUsuario(this, usuarioActualizado);  // voy a llamar al viewModel para registrar el usuario
        });

        // aqui voy a ver si el registro fue con exito y si es asi redirido a la segunda activity, sino no puedo avanzar
        registroActivityViewModel.getRegistroSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // veo los errores y le muestro un mensaje al usuario
        registroActivityViewModel.getRegistroError().observe(this, errorMessage -> {
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        });
    }


}