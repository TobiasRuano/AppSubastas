package com.trotos.appsubastas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        mailText = (EditText) findViewById(R.id.mailText);
        passText = (EditText) findViewById(R.id.passText);
        logInButton = (Button) findViewById(R.id.submitButton);
        registerButton = (Button) findViewById(R.id.RegisterButton);
        hasPassSwitch = (Switch) findViewById(R.id.hasPassSwitch);

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
                    if (userHasPass) {
                        if (checkPassword()) {
                            //TODO: a la API!
                        } else {
                            showAlert("Usuario o Contraseña invalidos", "Por favor, chequea que los datos ingresados sean correctos.");
                        }
                    } else {
                        // TODO: a la API.
                        Intent intent = new Intent(IniciarSesionActivity.this, CrearPassActivity.class);
                        startActivity(intent);
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
                if (pass.isEmpty()) {
                    return false;
                } else {
                    return true;
                }
            }

            private boolean isEmailValid(String email) {
                String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(email);
                return matcher.matches();
            }

            private void showAlert(String titulo, String mensaje) {
                new AlertDialog.Builder(IniciarSesionActivity.this)
                        .setTitle(titulo)
                        .setMessage(mensaje)
                        .setPositiveButton("Aceptar", null)
                        .show();
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
}