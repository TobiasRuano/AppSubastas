package com.trotos.appsubastas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trotos.appsubastas.Modelos.Auction;
import com.trotos.appsubastas.Modelos.ResponseAuctions;
import com.trotos.appsubastas.Modelos.User;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MenuLogueado extends AppCompatActivity implements SearchView.OnQueryTextListener{

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    List<Auction> auctions = new ArrayList<>();
    Boolean estadoLoggeado;
    User user;
    SearchView buscadorMenu;
    MyAdapterSubasta adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_logueado);

        getUser();
        init();

        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.hide();
        }

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
        adapter = new MyAdapterSubasta(auctions, this, new MyAdapterSubasta.OnItemClickListener() {
            @Override
            public void onItemClick(Auction item) {
                moveToDescription(item);
            }
        });
        recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        buscadorMenu = findViewById(R.id.buscadorMenu);
        buscadorMenu.setOnQueryTextListener(this);
    }

    private Boolean checkLogInStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);
        return !token.isEmpty();
    }

    private void getUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);
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
                    if (subastas != null) {
                        auctions.addAll(subastas.getData());
                        auctions.sort(Comparator.comparing(Auction::getEndTime));
                        adapter.setItems(auctions);
                    }
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
        intent.putExtra("category", item.getCategory());
        intent.putExtra("estadoLoggeado", estadoLoggeado);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filtrado(newText);
        return false;
    }
}