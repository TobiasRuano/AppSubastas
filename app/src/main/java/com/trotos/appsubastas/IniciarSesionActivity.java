package com.trotos.appsubastas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.trotos.appsubastas.Modelos.LoginInformation;
import com.trotos.appsubastas.Modelos.ResponseLogIn;
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
    Button invitadoButton;
    Switch hasPassSwitch;
    TextInputLayout passTextView;

    Boolean userHasPass = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(checkLogInStatus()) {
            Intent intent = new Intent(IniciarSesionActivity.this, MenuLogueado.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.activity_iniciar_sesion);

            configureUI();

            hasPassSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean hasPass) {
                    if (hasPass) {
                        passText.setFocusableInTouchMode(true);
                        passText.setEnabled(true);
                        passText.setVisibility(View.VISIBLE);
                        passTextView.setVisibility(View.VISIBLE);
                        logInButton.setText("Iniciar Sesion");
                        userHasPass = true;
                    } else {
                        passText.setFocusable(false);
                        passText.setEnabled(false);
                        passText.setVisibility(View.GONE);
                        passTextView.setVisibility(View.GONE);
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

            invitadoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(IniciarSesionActivity.this, MenuInvitado.class));
                }
            });
        }
    }

    private Boolean checkLogInStatus() {
        SharedPreferences  sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);
        return token !=null;
    }

    private void configureUI() {
        mailText = (EditText) findViewById(R.id.mailText);
        passText = (EditText) findViewById(R.id.passText);
        logInButton = (Button) findViewById(R.id.submitButton);
        registerButton = (Button) findViewById(R.id.RegisterButton);
        invitadoButton = findViewById(R.id.InvitadoButton);
        hasPassSwitch = (Switch) findViewById(R.id.hasPassSwitch);
        passTextView = findViewById(R.id.passTextView);
    }

    private void logIn(String mail, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);

        LoginInformation logIn = new LoginInformation(mail, password);
        Call<ResponseLogIn> call = as.logIn(logIn);

        call.enqueue(new Callback<ResponseLogIn>() {
            @Override
            public void onResponse(Call<ResponseLogIn> call, Response<ResponseLogIn> response) {
                if(response.isSuccessful()) {
                    ResponseLogIn responseLogin = response.body();
                    saveUser(responseLogin.getUser());
                    saveToken(responseLogin.getToken());
                    Intent intent = new Intent(IniciarSesionActivity.this, MenuLogueado.class);
                    intent.putExtra("estadoLoggeado",true);
                    startActivity(intent);
                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Contraseña Incorrecta", Toast.LENGTH_LONG);
                    toast1.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogIn> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al Iniciar sesion", Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }

    private void saveUser(User userToSave) {
        SharedPreferences  sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userToSave);
        prefsEditor.putString("User", json);
        prefsEditor.apply();
    }

    private void saveToken(String token) {
        SharedPreferences  sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("Token", token);
        prefsEditor.apply();
    }

    private void getEstadoPassword(String mail) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<User> call = as.checkPasswordUsuario(new LoginInformation(mail, null));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    User usuario = response.body();
                    Intent intent = new Intent(IniciarSesionActivity.this, CrearPassActivity.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"No puede crear una contraseña para su cuenta", Toast.LENGTH_LONG);
                    toast1.show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al intentar obtener los datos de la cuenta.", Toast.LENGTH_LONG);
                toast1.show();
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