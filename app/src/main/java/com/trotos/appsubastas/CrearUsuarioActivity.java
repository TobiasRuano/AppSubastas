package com.trotos.appsubastas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

        nameText = (EditText) findViewById(R.id.nameEditText);
        surnameText = (EditText) findViewById(R.id.apellidoEditText);
        mailText = (EditText) findViewById(R.id.mailEditText);
        dniText = (EditText) findViewById(R.id.dniEditText);
        addressText = (EditText) findViewById(R.id.direccionEditText);
        createAccount = (Button) findViewById(R.id.createAccountButton);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEstadoCampos()) {
                    // TODO: a la API!
                    showAlert("Exito!", "Usuario creado de forma satisfactoria!");
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
                if (!name.isEmpty() && !surname.isEmpty() && !mail.isEmpty() && !dni.isEmpty() && !address.isEmpty()) {
                    return true;
                } else {
                    return false;
                }
            }

            private void showAlert(String titulo, String mensaje) {
                new AlertDialog.Builder(CrearUsuarioActivity.this)
                        .setTitle(titulo)
                        .setMessage(mensaje)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
        });
    }
}
