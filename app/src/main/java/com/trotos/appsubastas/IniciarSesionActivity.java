package com.trotos.appsubastas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trotos.appsubastas.Modelos.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IniciarSesionActivity extends AppCompatActivity {

    EditText mailText;
    EditText passText;
    Button logInButton;
    Button registerButton;
    Switch hasPassSwitch;

    Boolean userHasPass = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        configureUI();

        hasPassSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean hasPass) {
                if (hasPass) {
                    passText.setFocusableInTouchMode(true);
                    passText.setEnabled(true);
                    logInButton.setText("Iniciar Sesion");
                    userHasPass = true;
                } else {
                    passText.setFocusable(false);
                    passText.setEnabled(false);
                    passText.setText("");
                    logInButton.setText("Crear Contraseña");
                    userHasPass = false;
                }
            }
        });

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEstadoMail()) {
                    String mail = mailText.getText().toString();
                    if (userHasPass) {
                        if (checkPassword()) {
                            String password = passText.getText().toString();
                            logIn(mail, password);
                        } else {
                            showAlert("Usuario o Contraseña invalidos", "Por favor, chequea que los datos ingresados sean correctos.");
                        }
                    } else {
                        getEstadoPassword(mail);
                    }
                } else {
                    showAlert("Mail invalido", "Debes ingresar un mail valido para continuar.");
                }
            }

            private boolean checkEstadoMail() {
                String mail = mailText.getText().toString();
                if (!mail.isEmpty() && isEmailValid(mail)) {
                    return true;
                } else {
                    return false;
                }
            }

            private boolean checkPassword() {
                String pass = passText.getText().toString();
                return !pass.isEmpty();
            }

            private boolean isEmailValid(String email) {
                String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(email);
                return matcher.matches();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IniciarSesionActivity.this, CrearUsuarioActivity.class);
                startActivity(intent);
            }
        });
    }

    private void configureUI() {
        mailText = (EditText) findViewById(R.id.mailText);
        passText = (EditText) findViewById(R.id.passText);
        logInButton = (Button) findViewById(R.id.submitButton);
        registerButton = (Button) findViewById(R.id.RegisterButton);
        hasPassSwitch = (Switch) findViewById(R.id.hasPassSwitch);
    }

    private void logIn(String mail, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://URL-de-la-API.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<User> call = as.logIn();

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body() != null) {
                    saveUser(response.body());
                    Intent intent = new Intent(IniciarSesionActivity.this, SubastaActivity.class);
                    intent.putExtra("estadoLoggeado",true);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al Iniciar sesion", Toast.LENGTH_LONG);
                toast1.show();
                // para entrar
                Intent intent = new Intent(IniciarSesionActivity.this, SubastaActivity.class);
                intent.putExtra("estadoLoggeado",true);
                startActivity(intent);
            }
        });
    }

    private void saveUser(User userToSave) {
        SharedPreferences  mPrefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userToSave);
        prefsEditor.putString("User", json);
        prefsEditor.apply();
    }

    private void getEstadoPassword(String mail) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://URL-de-la-API.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<User> call = as.checkPasswordUsuario(mail);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body() != null) {
                    Intent intent = new Intent(IniciarSesionActivity.this, CrearPassActivity.class);
                    intent.putExtra("usuario",response.body());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al intentar obtener los datos de la cuenta.", Toast.LENGTH_LONG);
                toast1.show();
                //para testear
                Intent intent = new Intent(IniciarSesionActivity.this, CrearPassActivity.class);
                intent.putExtra("usuario",new User("nombre","apellido","mail",1234,"address"));
                startActivity(intent);
            }
        });
    }

    private void showAlert(String titulo, String mensaje) {
        new AlertDialog.Builder(IniciarSesionActivity.this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }
}