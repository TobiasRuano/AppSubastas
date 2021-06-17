package com.trotos.appsubastas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import com.trotos.appsubastas.Modelos.MPTarjeta;
import com.trotos.appsubastas.Modelos.Subasta;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MediosPagoActivity extends AppCompatActivity {

    List<MPTarjeta> tarjetas = new ArrayList<MPTarjeta>();
    RecyclerView reciclerView;
    FloatingActionButton addCardButton;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medios_pago);

        getSupportActionBar().hide();
        //testCreateTarjetas();
        getTarjetas();
        configureUI();

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediosPagoActivity.this, AgregarMedioPagoActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void configureUI() {
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


    }


    private void getTarjetas() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.111/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<List<MPTarjeta>> call = as.getTarjetas();

        call.enqueue(new Callback<List<MPTarjeta>>() {
            @Override
            public void onResponse(Call<List<MPTarjeta>> call, Response<List<MPTarjeta>> response) {
                if(response.body() != null) {
                    tarjetas.addAll(response.body());

                    reciclerView.getAdapter().notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<MPTarjeta>> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al obtener las tarjetas", Toast.LENGTH_LONG);
                Toast toast2 = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast2.show();
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
    @SuppressLint("SimpleDateFormat")
    private void testCreateTarjetas() {
        tarjetas.add( new MPTarjeta(1,"Tobias Ruano", "14237463987612376", "Visa", 111, new Date(12,12,12)));
        //tarjetas.add( new MPTarjeta("Tobias Ruano", "14237263987616652", "Amex", "123", new Date()));
        //tarjetas.add( new MPTarjeta("Tobias Ruano", "14237463987613972", "MasterCard", "123", new Date()));
        //tarjetas.add( new MPTarjeta("Tobias Ruano", "14237263987610097", "Amex", "123", new Date()));
        //tarjetas.add( new MPTarjeta("Tobias Ruano", "14237463987611132", "MasterCard", "123", new Date()));
        //tarjetas.add( new MPTarjeta("Tobias Ruano", "14237263987617625", "Discover", "123", new Date()));
        //tarjetas.add( new MPTarjeta("Tobias Ruano", "14237263967384950", "Visa", "123", new Date()));
        //tarjetas.add( new MPTarjeta("Tobias Ruano", "14237463900923618", "Discover", "123", new Date()));
        //tarjetas.add( new MPTarjeta("Tobias Ruano", "14237263983611352", "Amex", "123", new Date()));
    }
}