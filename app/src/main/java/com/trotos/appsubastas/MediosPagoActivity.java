package com.trotos.appsubastas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class MediosPagoActivity extends AppCompatActivity {

    MPTarjeta[] tarjetas;
    RecyclerView reciclerView;
    FloatingActionButton addCardButton;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medios_pago);
        testCreateTarjetas();

        getSupportActionBar().hide();

        reciclerView = (RecyclerView) findViewById(R.id.cardView);
        addCardButton = (FloatingActionButton) findViewById(R.id.newCardButton);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        CardViewAdapter cardViewAdapter = new CardViewAdapter(this, tarjetas);
        reciclerView.setAdapter(cardViewAdapter);
        reciclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomNavigationView.setSelectedItemId(R.id.mpLogueado);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeLogueado:
                        startActivity(new Intent(MediosPagoActivity.this, MenuLogueado.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.mpLogueado:
                        break;
                    case R.id.msLogueado:
                        break;
                    case R.id.moLogueado:
                        startActivity(new Intent(MediosPagoActivity.this, MisObjetos.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.usuarioLogueado:
                        startActivity(new Intent(MediosPagoActivity.this, MiUsuario.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });



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