package com.trotos.appsubastas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.trotos.appsubastas.Modelos.MPTarjeta;
import com.trotos.appsubastas.Modelos.ResponseCreateMP;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AgregarMedioPagoActivity extends AppCompatActivity {

    EditText cardNumber;
    EditText cardName;
    EditText expDate;
    EditText cvcNumber;
    Button addCardButton;
    Date expiracionTarjeta;

    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_medio_pago);

        userId = (int) getIntent().getSerializableExtra("userId");

        configureUI();

        expDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.expDateEditText) {
                    showDatePickerDialog();
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
                return !name.isEmpty() && !number.isEmpty() && !exp.isEmpty() && !cvc.isEmpty() && number.length() == 16;
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                expDate.setText(selectedDate);

                expiracionTarjeta = new Date(year - 1900, month, day);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void addCard() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);

        String name = cardName.getText().toString();
        String number = cardNumber.getText().toString();
        String cvc1 = cvcNumber.getText().toString();
        int cvc2 = Integer.parseInt(cvc1);

        MPTarjeta tarjetaBody = new MPTarjeta(0, userId, name,false, number, cvc2, expiracionTarjeta, null);

        Call<ResponseCreateMP> call = as.postTarjeta(tarjetaBody, "Bearer "+ token);

        call.enqueue(new Callback<ResponseCreateMP>() {
            @Override
            public void onResponse(Call<ResponseCreateMP> call, Response<ResponseCreateMP> response) {
                if(response.isSuccessful()) {
                    MPTarjeta tarjeta = response.body().getData();
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Tarjeta agregada correctamente!", Toast.LENGTH_LONG);
                    toast1.show();
                    passDataBack(tarjeta);
                }
            }

            private void passDataBack(MPTarjeta tarjeta) {
                Intent intent = new Intent();
                intent.putExtra("nuevaTarjeta", tarjeta);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseCreateMP> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al intentar agregar la tarjeta", Toast.LENGTH_LONG);
                toast1.show();
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
        new AlertDialog.Builder(AgregarMedioPagoActivity.this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }
}