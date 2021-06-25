package com.trotos.appsubastas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.trotos.appsubastas.Modelos.Auction;
import com.trotos.appsubastas.Modelos.ResponseAuctions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MenuLogueado extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;


    List<Auction> auctions = new ArrayList<>();
    Boolean estadoLoggeado;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_logueado);

        init();

        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottomNavigation);


        bottomNavigationView.setSelectedItemId(R.id.homeLogueado);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeLogueado:
                        break;
                    case R.id.mpLogueado:
                        startActivity(new Intent(MenuLogueado.this, MediosPagoActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.msLogueado:
                        startActivity(new Intent(MenuLogueado.this, MisCatalogosPujadosActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.moLogueado:
                        startActivity(new Intent(MenuLogueado.this, MisObjetos.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.usuarioLogueado:
                        startActivity(new Intent(MenuLogueado.this, MiUsuario.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });


    }

    public void init(){
        estadoLoggeado = checkLogInStatus();
        getDatos();
        MyAdapterSubasta myAdapterSubasta = new MyAdapterSubasta(auctions, this, new MyAdapterSubasta.OnItemClickListener() {
            @Override
            public void onItemClick(Auction item) {
                moveToDescription(item);
            }
        });
        recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(  this    ));
        recyclerView.setAdapter(myAdapterSubasta);
    }

    private Boolean checkLogInStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);
        return !token.isEmpty();
    }

    private void getDatos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<ResponseAuctions> call = as.getSubastas();

        call.enqueue(new Callback<ResponseAuctions>() {
            @Override
            public void onResponse(Call<ResponseAuctions> call, Response<ResponseAuctions> response) {
                if(response.isSuccessful()) {
                    ResponseAuctions subastas = response.body();
                    auctions.addAll(subastas.getData());
                    recyclerView.getAdapter().notifyDataSetChanged();
                } else {
                    Toast toast2 = Toast.makeText(getApplicationContext(), "Hubo un error al obtener los datos", Toast.LENGTH_LONG);
                    toast2.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAuctions> call, Throwable t) {
                Toast toast2 = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast2.show();
            }
        });
    }

    private void moveToDescription(Auction item) {
        Intent intent = new Intent(this, CatalogoActivity.class);
        intent.putExtra("subasta", item);
        intent.putExtra("categoria", item.getCategory());
        intent.putExtra("estadoLoggeado", estadoLoggeado);
        startActivity(intent);
    }

}