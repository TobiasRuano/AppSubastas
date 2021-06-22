package com.trotos.appsubastas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trotos.appsubastas.Modelos.MPTarjeta;
import com.trotos.appsubastas.Modelos.ResponseMPTarjetas;
import com.trotos.appsubastas.Modelos.User;

import java.lang.reflect.Type;
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
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medios_pago);

        getSupportActionBar().hide();
        getUser();
        getTarjetas();
        configureUI();

        addCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediosPagoActivity.this, AgregarMedioPagoActivity.class);
                intent.putExtra("userId", user.getId());
                startActivityForResult(intent, 1);
            }
        });
    }

    private void getUser() {
        SharedPreferences  sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);
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
        SharedPreferences  sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<ResponseMPTarjetas> call = as.getTarjetas(user, "Bearer "+ token);

        call.enqueue(new Callback<ResponseMPTarjetas>() {
            @Override
            public void onResponse(Call<ResponseMPTarjetas> call, Response<ResponseMPTarjetas> response) {
                if(response.isSuccessful()) {
                    ResponseMPTarjetas responseMP = response.body();
                    tarjetas.addAll(responseMP.getResults());
                    reciclerView.getAdapter().notifyDataSetChanged();
                } else {
                    Toast toast2 = Toast.makeText(getApplicationContext(), "Error al obtener las tarjetas", Toast.LENGTH_LONG);
                    toast2.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMPTarjetas> call, Throwable t) {
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
}