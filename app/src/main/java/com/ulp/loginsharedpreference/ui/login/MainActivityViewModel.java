package com.ulp.loginsharedpreference.ui.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.loginsharedpreference.model.Usuario;
import com.ulp.loginsharedpreference.request.ApiCliente;

public class MainActivityViewModel extends AndroidViewModel {


    // declaro variables q voy a observar
    private MutableLiveData<Usuario> loginSuccess = new MutableLiveData<>();// puedo ver cualquier cambio osea la activity no usuario
    private MutableLiveData<String> loginError = new MutableLiveData<>();// me dice el error

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }
    // los metods para acceder al observable
    public LiveData<Usuario> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> getLoginError() {
        return loginError;
    }
// aqui en este metodo valido la entrada del usuario
    public void login(Context context, String mail, String password) {
        if (isInputValid(mail, password)) {
            Usuario usuario = ApiCliente.login(context, mail, password);
            if (usuario != null) {
                loginSuccess.setValue(usuario);  // le digo a mi login que tuve exito
            } else {
                loginError.setValue("Credenciales incorrectas");  // aviso que las credenciales son incorrectas
            }
        } else {
            loginError.setValue("Por favor, ingrese su correo y contraseña");  // si hay campos vacios me larga el mensaje
        }
    }

    private boolean isInputValid(String mail, String password) {
        return !mail.trim().isEmpty() && !password.trim().isEmpty();  // valido que los campos no este vacios
    }
}
