package com.trotos.appsubastas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MediosPagoActivity extends AppCompatActivity {

    List<MPTarjeta> tarjetas = new ArrayList<MPTarjeta>();
    RecyclerView reciclerView;
    Button addCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medios_pago);

        testCreateTarjetas();
        //getTarjetas();
        configureUI();

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediosPagoActivity.this, AgregarMedioPago.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void configureUI() {
        reciclerView = (RecyclerView) findViewById(R.id.cardView);
        addCardButton = (Button) findViewById(R.id.newCardButton);

        CardViewAdapter cardViewAdapter = new CardViewAdapter(this, tarjetas);
        reciclerView.setAdapter(cardViewAdapter);
        reciclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getTarjetas() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://URL-de-la-API.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<List<MPTarjeta>> call = as.getTarjetas();

        call.enqueue(new Callback<List<MPTarjeta>>() {
            @Override
            public void onResponse(Call<List<MPTarjeta>> call, Response<List<MPTarjeta>> response) {
                if(response.body() != null) {
                    for (MPTarjeta tarjeta : response.body()) {
                        tarjetas.add(tarjeta);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MPTarjeta>> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al obtener las tarjetas", Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                MPTarjeta tarjeta = (MPTarjeta) data.getSerializableExtra("nuevaTarjeta");
                tarjetas.add(tarjeta);
            }
        }
    }

    //Funcion test sin API
    private void testCreateTarjetas() {
        tarjetas.add( new MPTarjeta("Tobias Ruano", "14237463987612376", "Visa", "123", new Date()));
        tarjetas.add( new MPTarjeta("Tobias Ruano", "14237263987616652", "Amex", "123", new Date()));
        tarjetas.add( new MPTarjeta("Tobias Ruano", "14237463987613972", "MasterCard", "123", new Date()));
        tarjetas.add( new MPTarjeta("Tobias Ruano", "14237263987610097", "Amex", "123", new Date()));
        tarjetas.add( new MPTarjeta("Tobias Ruano", "14237463987611132", "MasterCard", "123", new Date()));
        tarjetas.add( new MPTarjeta("Tobias Ruano", "14237263987617625", "Discover", "123", new Date()));
        tarjetas.add( new MPTarjeta("Tobias Ruano", "14237263967384950", "Visa", "123", new Date()));
        tarjetas.add( new MPTarjeta("Tobias Ruano", "14237463900923618", "Discover", "123", new Date()));
        tarjetas.add( new MPTarjeta("Tobias Ruano", "14237263983611352", "Amex", "123", new Date()));
    }
}