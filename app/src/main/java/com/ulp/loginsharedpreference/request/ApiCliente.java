package com.ulp.loginsharedpreference.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.ulp.loginsharedpreference.model.Usuario;

public class ApiCliente {
    private static SharedPreferences sp;

    // conectar a SharedPreferences
    private static SharedPreferences conectar(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences("datos", 0);
        }
        return sp;
    }

    // mi mertodo para guardar el usuario en SharedPreferences
    public static void guardar(Context context, Usuario usuario) {
        SharedPreferences sp = conectar(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("dni", usuario.getDni());
        editor.putString("apellido", usuario.getApellido());
        editor.putString("nombre", usuario.getNombre());
        editor.putString("mail", usuario.getMail());
        editor.putString("password", usuario.getPassword());
        editor.commit();  // guardo datos rapido
    }

    // mi metodo para leer el usuario completo de SharedPreferences
    public static Usuario leer(Context context) {
        SharedPreferences sp = conectar(context);
        Long dni = sp.getLong("dni", -1);
        String apellido = sp.getString("apellido", "-1");
        String nombre = sp.getString("nombre", "-1");
        String email = sp.getString("mail", "-1");
        String pass = sp.getString("password", "-1");
        Usuario usuario = new Usuario (dni, apellido, nombre, email, pass);
        return  usuario;
    }

    // mi metodo para verificar el login
    public static Usuario login(Context context, String mail, String password) {
        Usuario usuario = null;
        SharedPreferences sp = conectar(context);
        long dni = sp.getLong("dni", -1);
        String apellido = sp.getString("apellido", "-1");
        String nombre = sp.getString("nombre", "-1");
        String email = sp.getString("mail", "-1");
        String pass = sp.getString("password", "-1");

        if (email.equals(mail) && pass.equals(password)) {
            usuario = new Usuario(dni, apellido, nombre, mail, password);
        }
        return usuario;
    }
}
