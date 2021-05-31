package com.trotos.appsubastas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditarPerfil extends AppCompatActivity {

    EditText mail, direccion;
    Button salvarCambios;



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
                    showAlert("Exito!", "Perfil actualizado correctamente.");
                }else{
                    showAlert("Error al editar perfil", "Por favor intente de nuevo mas tarde.");
                }
            }

            private boolean checkEstadoCampos() {
                String stringMail = mail.getText().toString();
                String stringDireccion = direccion.getText().toString();
                if (!stringMail.isEmpty() && !stringDireccion.isEmpty() && isEmailValid(stringMail)){
                    return true;
                }else{
                    return false;
                }
            }

            private boolean isEmailValid(String email) {
                String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(email);
                return matcher.matches();
            }

            private void showAlert(String titulo, String mensaje) {
                new AlertDialog.Builder(EditarPerfil.this)
                        .setTitle(titulo)
                        .setMessage(mensaje)
                        .setPositiveButton("Aceptar", null)
                        .show();
            }


        });


    }

}