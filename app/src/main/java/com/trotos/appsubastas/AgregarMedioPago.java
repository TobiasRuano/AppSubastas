package com.trotos.appsubastas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AgregarMedioPago extends AppCompatActivity {

    EditText cardNumber;
    EditText cardName;
    EditText expDate;
    EditText cvcNumber;
    Button addCardButton;
    Date expiracionTarjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_medio_pago);

        configureUI();

        expDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.expDateEditText:
                        showDatePickerDialog();
                        break;
                }
            }
        });

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEstadoCampos()) {
                    addCard();
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
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 porque enero es cero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                expDate.setText(selectedDate);
                System.out.println(expDate.getText().toString());

                expiracionTarjeta = new Date(year - 1900, month, day);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void addCard() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://URL-de-la-API.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);

        String name = cardName.getText().toString();
        String number = cardNumber.getText().toString();
        String cvc = cvcNumber.getText().toString();

        MPTarjeta tarjeta = new MPTarjeta(name, number, "Visa", cvc, expiracionTarjeta);

        Call<MPTarjeta> call = as.postTarjeta(tarjeta);

        call.enqueue(new Callback<MPTarjeta>() {
            @Override
            public void onResponse(Call<MPTarjeta> call, Response<MPTarjeta> response) {
                if(response.body() != null) {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Tarjeta agregada correctamente!", Toast.LENGTH_LONG);
                    toast1.show();
                    passDataBack();
                }
            }

            private void passDataBack() {
                Intent intent = new Intent();
                intent.putExtra("nuevaTarjeta", tarjeta);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailure(Call<MPTarjeta> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al intentar agregar la tarjeta", Toast.LENGTH_LONG);
                toast1.show();

                //passDataBack(); // Solo para debug sin API!
            }
        });
    }

    private void configureUI() {
        cardNumber = (EditText) findViewById(R.id.cardNumberEditText);
        cardName = (EditText) findViewById(R.id.cardNameEditText);
        expDate = (EditText) findViewById(R.id.expDateEditText);
        cvcNumber = (EditText) findViewById(R.id.cvcEditText);
        addCardButton = (Button) findViewById(R.id.addCardButton);
    }

    private void showAlert(String titulo, String mensaje) {
        new AlertDialog.Builder(AgregarMedioPago.this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }
}