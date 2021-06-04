package com.trotos.appsubastas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trotos.appsubastas.Modelos.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrearPassActivity extends AppCompatActivity {

    TextView titleText;
    EditText firstPasswordText;
    EditText secondPasswordText;
    Button createPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pass);

        configureUI();

        createPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    String password = firstPasswordText.getText().toString();
                    createPassword(password);
                    showAlert("Exito!", "Contraseña creada de forma satisfactoria. Ya puedes disfrutar de nuestro servicio!");
                } else {
                    showAlert("Contraseña invalida", "Debes ingresar una contraseña y re escribirla para continuar");
                }
            }

            private boolean checkInput() {
                String first = firstPasswordText.getText().toString();
                String second = secondPasswordText.getText().toString();

                if (first.isEmpty() || second.isEmpty()) {
                    return false;
                } else {
                    if (first.equals(second)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        });
    }

    private void configureUI() {
        User user = (User) getIntent().getSerializableExtra("usuario");
        titleText = (TextView) findViewById(R.id.titleText);
        titleText.setText("Hola " + user.getName());
        firstPasswordText = (EditText) findViewById(R.id.passwordEditText);
        secondPasswordText = (EditText) findViewById(R.id.secondPasswordEditText);
        createPasswordButton = (Button) findViewById(R.id.createPasswordButton);
    }

    private void createPassword(String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://URL-de-la-API.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<User> call = as.createPassword(password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body() != null) {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Contraseña creada de forma satisfactoria!", Toast.LENGTH_LONG);
                    toast1.show();
                    Intent intent = new Intent(CrearPassActivity.this, SubastaActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al intentar crear la contraseña.", Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }

    private void showAlert(String titulo, String mensaje) {
        new AlertDialog.Builder(CrearPassActivity.this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }
}