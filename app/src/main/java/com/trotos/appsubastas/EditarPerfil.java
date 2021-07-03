package com.trotos.appsubastas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trotos.appsubastas.Modelos.ResponseMPTarjetas;
import com.trotos.appsubastas.Modelos.User;

import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarPerfil extends AppCompatActivity {

    EditText mail, direccion;
    Button salvarCambios;
    User user;
    Boolean done = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        mail = findViewById(R.id.editarMailBox);
        direccion = findViewById(R.id.editarDireccionBox);

        salvarCambios = findViewById(R.id.botonEditarPerfil);

        salvarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEstadoCampos()){
                    modificarUsuario();
                }else{
                    showAlert("Error.", "Debe completar los campos correctamente");
                }
            }

            private boolean checkEstadoCampos() {
                String stringMail = mail.getText().toString();
                String stringDireccion = direccion.getText().toString();
                if ((!stringMail.isEmpty() || !stringDireccion.isEmpty()) && isEmailValid(stringMail)){
                    return true;
                }else{
                    return false;
                }
            }

            private boolean isEmailValid(String email) {
                String stringMail = mail.getText().toString();
                if(stringMail.isEmpty()) {
                    return true;
                } else {
                    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(email);
                    return matcher.matches();
                }
            }
        });
    }

    private void getUser() {
        SharedPreferences  sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);
    }

    private void modificarUsuario() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);

        String stringMail = mail.getText().toString();
        String stringDireccion = direccion.getText().toString();
        getUser();
        if(!stringMail.isEmpty()) {
            user.setMail(stringMail);
        }
        if(!stringDireccion.isEmpty()) {
            user.setAddress(stringDireccion);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<Pair<String, String>> call = as.modifyUser(user, "Bearer "+ token);

        call.enqueue(new Callback<Pair<String, String>>() {
            @Override
            public void onResponse(Call<Pair<String, String>> call, Response<Pair<String, String>> response) {
                if(response.isSuccessful()) {
                    done = true;
                    showAlert("Exito!", "Usuario modificado correctamente.");
                } else {
                    if(response.code() == 404) {
                        Toast toast2 = Toast.makeText(getApplicationContext(), "Usuario no encontrado.", Toast.LENGTH_LONG);
                        toast2.show();
                    } else if(response.code() == 403) {
                        Toast toast2 = Toast.makeText(getApplicationContext(), "El mail indicado ya esta en uso.", Toast.LENGTH_LONG);
                        toast2.show();
                    } else {
                        Toast toast2 = Toast.makeText(getApplicationContext(), "Error en el servidor.", Toast.LENGTH_LONG);
                        toast2.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Pair<String, String>> call, Throwable t) {
                Toast toast2 = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast2.show();
            }
        });
    }

    private void showAlert(String titulo, String mensaje) {
        new AlertDialog.Builder(EditarPerfil.this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        if(done) {
                            saveUser(user);
                            finish();
                        }}})
                .show();
    }

    private void saveUser(User userToSave) {
        SharedPreferences  sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userToSave);
        prefsEditor.putString("User", json);
        prefsEditor.apply();
    }

}