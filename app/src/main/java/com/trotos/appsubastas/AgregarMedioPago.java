package com.trotos.appsubastas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AgregarMedioPago extends AppCompatActivity {

    EditText cardNumber;
    EditText cardName;
    EditText expDate;
    EditText cvcNumber;
    Button addCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_medio_pago);

        cardNumber = (EditText) findViewById(R.id.cardNumberEditText);
        cardName = (EditText) findViewById(R.id.cardNameEditText);
        expDate = (EditText) findViewById(R.id.expDateEditText);
        cvcNumber = (EditText) findViewById(R.id.cvcEditText);
        addCardButton = (Button) findViewById(R.id.addCardButton);

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEstadoCampos()) {
                    // TODO: a la API!
                    showAlert("Exito!", "Tarjeta agregada exitosamente.");
                } else {
                    showAlert("Error al agregar la tarjeta", "Por favor, completa todos los campos correctamente para continuar");
                }
            }

            private boolean checkEstadoCampos() {
                String name = cardName.getText().toString();
                String number = cardNumber.getText().toString();
                String exp = expDate.getText().toString();
                String cvc = cvcNumber.getText().toString();
                if (!name.isEmpty() && !number.isEmpty() && !exp.isEmpty() && !cvc.isEmpty()) {
                    return true;
                } else {
                    return false;
                }
            }

            private void showAlert(String titulo, String mensaje) {
                new AlertDialog.Builder(AgregarMedioPago.this)
                        .setTitle(titulo)
                        .setMessage(mensaje)
                        .setPositiveButton("Aceptar", null)
                        .show();
            }
        });
    }
}