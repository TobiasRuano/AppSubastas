package com.trotos.appsubastas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.trotos.appsubastas.Modelos.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearUsuarioActivity extends AppCompatActivity{

    EditText nameText;
    EditText surnameText;
    EditText mailText;
    EditText dniText;
    EditText addressText;
    Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        configureUI();

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEstadoCampos()) {
                    User user = createUser();
                    createAccount(user);
                } else {
                    showAlert("Campos incompletos", "Por favor completa todos los campos para continuar.");
                }
            }

            private boolean checkEstadoCampos() {
                String name = nameText.getText().toString();
                String surname = surnameText.getText().toString();
                String mail = mailText.getText().toString();
                String dni = dniText.getText().toString();
                String address = addressText.getText().toString();
                return !name.isEmpty() && !surname.isEmpty() && !mail.isEmpty() && isEmailValid(mail) && !dni.isEmpty() && !address.isEmpty();
            }

            private boolean isEmailValid(String email) {
                String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(email);
                return matcher.matches();
            }
        });
    }

    private void configureUI() {
        nameText = (EditText) findViewById(R.id.nameEditText);
        surnameText = (EditText) findViewById(R.id.apellidoEditText);
        mailText = (EditText) findViewById(R.id.mailEditText);
        dniText = (EditText) findViewById(R.id.dniEditText);
        addressText = (EditText) findViewById(R.id.direccionEditText);
        createAccount = (Button) findViewById(R.id.createAccountButton);
    }

    private User createUser() {
        String name = nameText.getText().toString();
        String surname = surnameText.getText().toString();
        String mail = mailText.getText().toString();
        int dni = Integer.parseInt(dniText.getText().toString());
        String address = addressText.getText().toString();
        return new User(name, surname, mail, dni, address);
    }

    private void createAccount(User user) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://URL-de-la-API.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<User> call = as.createAccount(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body() != null) {
                    //showAlert("Exito!", "Usuario creado de forma satisfactoria!");
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Usuario creado de forma satisfactoria!", Toast.LENGTH_LONG);
                    toast1.show();
                    Intent intent = new Intent(CrearUsuarioActivity.this, SubastaActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al intentar crear la cuenta.", Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }

    private void showAlert(String titulo, String mensaje) {
        new AlertDialog.Builder(CrearUsuarioActivity.this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }
}
