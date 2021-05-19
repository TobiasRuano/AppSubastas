package com.trotos.appsubastas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CrearPassActivity extends AppCompatActivity {

    TextView titleText;
    EditText firstPasswordText;
    EditText secondPasswordText;
    Button createPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_pass);

        titleText = (TextView) findViewById(R.id.titleText);
        // TODO: obtener el nombre del usuario desde la API!
        firstPasswordText = (EditText) findViewById(R.id.passwordEditText);
        secondPasswordText = (EditText) findViewById(R.id.secondPasswordEditText);
        createPasswordButton = (Button) findViewById(R.id.createPasswordButton);

        createPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {
                    // TODO: A la API!
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

            private void showAlert(String titulo, String mensaje) {
                new AlertDialog.Builder(CrearPassActivity.this)
                        .setTitle(titulo)
                        .setMessage(mensaje)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        });
    }
}