package com.trotos.appsubastas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Date;

public class MediosPagoActivity extends AppCompatActivity {

    MPTarjeta[] tarjetas;
    RecyclerView reciclerView;
    Button addCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medios_pago);
        testCreateTarjetas();

        reciclerView = (RecyclerView) findViewById(R.id.cardView);
        addCardButton = (Button) findViewById(R.id.newCardButton);

        CardViewAdapter cardViewAdapter = new CardViewAdapter(this, tarjetas);
        reciclerView.setAdapter(cardViewAdapter);
        reciclerView.setLayoutManager(new LinearLayoutManager(this));

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediosPagoActivity.this, AgregarMedioPago.class);
                startActivity(intent);
            }
        });
    }

    //Funcion test sin API
    private void testCreateTarjetas() {
        tarjetas = new MPTarjeta[6];
        tarjetas[0] = new MPTarjeta("Tobias Ruano", "14237463987612376", "Visa", new Date());
        tarjetas[1] = new MPTarjeta("Tobias Ruano", "14237263987616652", "Amex", new Date());
        tarjetas[2] = new MPTarjeta("Tobias Ruano", "14237463987613972", "MasterCard", new Date());
        tarjetas[3] = new MPTarjeta("Tobias Ruano", "14237263987610097", "Amex", new Date());
        tarjetas[4] = new MPTarjeta("Tobias Ruano", "14237463987611132", "MasterCard", new Date());
        tarjetas[5] = new MPTarjeta("Tobias Ruano", "14237263987617625", "Discover", new Date());
    }
}