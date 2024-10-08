package com.ulp.loginsharedpreference.ui.registro;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.loginsharedpreference.model.Usuario;
import com.ulp.loginsharedpreference.request.ApiCliente;

public class RegistroActivityViewModel extends AndroidViewModel {
// declaro variables q voy a observar
private MutableLiveData<Usuario> usuarioActual = new MutableLiveData<>();
    private MutableLiveData<Boolean> registroSuccess = new MutableLiveData<>();
    private MutableLiveData<String> registroError = new MutableLiveData<>();

    public RegistroActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Usuario> getUsuarioActual() {
        return usuarioActual;
    }

    public LiveData<Boolean> getRegistroSuccess() {
        return registroSuccess;
    }

    public LiveData<String> getRegistroError() {
        return registroError;
    }

    // este metodo me va a cargar el usuario actual
    public void cargarUsuario(Usuario usuario) {
        usuarioActual.setValue(usuario);
    }

    // este metodo es para  para obtener el valor del DNI sin mostrar -1
    public String obtenerValorDni(Usuario usuario) {
        return (usuario != null && usuario.getDni() != -1) ? String.valueOf(usuario.getDni()) : "";
    }

    // este metodo lo uso para registrar el usuario con validacion de campos
    public void registrarUsuario(Context context, Usuario usuario) {
        if (areFieldsValid(usuario)) {  // valida q mis campos no esten vacios
            try {
                ApiCliente.guardar(context, usuario);  // Guarda
                registroSuccess.setValue(true);  //  registro fue exitoso
            } catch (Exception e) {
                registroError.setValue("Error al guardar los datos"); // sino error
            }
        } else {
            registroError.setValue("Todos los campos son obligatorios");
        }
    }

    //este metodo lo uso para validar que todos los campos del usuario estén completos
    private boolean areFieldsValid(Usuario usuario) {
        return usuario.getDni() > 0 &&
                !usuario.getApellido().trim().isEmpty() &&
                !usuario.getNombre().trim().isEmpty() &&
                !usuario.getMail().trim().isEmpty() &&
                !usuario.getPassword().trim().isEmpty();
    }
}
