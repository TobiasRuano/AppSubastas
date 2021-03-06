package com.trotos.appsubastas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.trotos.appsubastas.Modelos.LoginInformation;
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
    String mail;

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
        });
    }

    private boolean checkInput() {
        String first = firstPasswordText.getText().toString();
        String second = secondPasswordText.getText().toString();
        if (first.isEmpty() || second.isEmpty()) {
            return false;
        } else {
            return first.equals(second);
        }
    }

    private void configureUI() {
        User user = (User) getIntent().getSerializableExtra("usuario");
        titleText = (TextView) findViewById(R.id.titleText);
        titleText.setText("Hola " + user.getName());
        mail = user.getMail();
        firstPasswordText = (EditText) findViewById(R.id.passwordEditText);
        secondPasswordText = (EditText) findViewById(R.id.secondPasswordEditText);
        createPasswordButton = (Button) findViewById(R.id.createPasswordButton);
    }

    private void createPassword(String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        LoginInformation logIn = new LoginInformation(mail, password);
        Call<User> call = as.createPassword(logIn);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Contraseña creada de forma satisfactoria!", Toast.LENGTH_LONG);
                    toast1.show();
                    finish();
                } else {
                    if(response.code() == 404) {
                        Toast toast1 = Toast.makeText(getApplicationContext(),"Usuario incorrecto.", Toast.LENGTH_LONG);
                        toast1.show();
                    }else if(response.code() == 409) {
                        Toast toast1 = Toast.makeText(getApplicationContext(),"El usuario ya posee una contraseña.", Toast.LENGTH_LONG);
                        toast1.show();
                    }else {
                        Toast toast1 = Toast.makeText(getApplicationContext(),"Error en el servidor.", Toast.LENGTH_LONG);
                        toast1.show();
                    }
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