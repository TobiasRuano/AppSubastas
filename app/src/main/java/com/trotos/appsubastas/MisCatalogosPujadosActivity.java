package com.trotos.appsubastas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trotos.appsubastas.Modelos.ItemCatalogo;
import com.trotos.appsubastas.Modelos.Auction;
import com.trotos.appsubastas.Modelos.ResponseItemsCatalog;
import com.trotos.appsubastas.Modelos.User;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MisCatalogosPujadosActivity<animFadeIn> extends AppCompatActivity {

    TextView nameDescriptionTextView5;
    TextView stateDescriptionTextView5;
    TextView categoryDescriptionTextView5;
    RecyclerView listRecyclerView5;

    Animation animFadeIn;
    boolean banderaAnimation = false;
    boolean estaRegistrado = true;
    ViewGroup.LayoutParams params;
    LinearLayout linearLayout5;

    Auction element;
    User user;
    List<ItemCatalogo> catalogos = new ArrayList<ItemCatalogo>();

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_catalogos_pujados);

        //estaRegistrado = getIntent().getBooleanExtra("estadoLoggeado", false);

        getUser();
        getDatos();
        init();

        bottomNavigationView = findViewById(R.id.bottomNavigation);

        bottomNavigationView.setSelectedItemId(R.id.msLogueado);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeLogueado:
                        startActivity(new Intent(MisCatalogosPujadosActivity.this, MenuLogueado.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.mpLogueado:
                        startActivity(new Intent(MisCatalogosPujadosActivity.this, MediosPagoActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.msLogueado:
                        break;
                    case R.id.moLogueado:
                        startActivity(new Intent(MisCatalogosPujadosActivity.this, MisObjetos.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.usuarioLogueado:
                        startActivity(new Intent(MisCatalogosPujadosActivity.this, MiUsuario.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });

    }

    public void init(){
        MyAdapterMisCatalogosPujados myAdapterMisCatalogosPujados = new MyAdapterMisCatalogosPujados(catalogos, estaRegistrado, this, new MyAdapterMisCatalogosPujados.OnItemClickListener() {
            @Override
            public void onItemClick(ItemCatalogo item) {
                moveToDescription(item);
            }
        });

        listRecyclerView5  = findViewById(R.id.listRecyclerView5);
        listRecyclerView5.setHasFixedSize(true);
        listRecyclerView5.setLayoutManager(new LinearLayoutManager(this));
        listRecyclerView5.setAdapter(myAdapterMisCatalogosPujados);

        nameDescriptionTextView5 = findViewById(R.id.nameDescriptionTextView5);
        //stateDescriptionTextView5 = findViewById(R.id.stateDescriptionTextView5);
        //categoryDescriptionTextView5 = findViewById(R.id.categoryDescriptionTextView5);

        linearLayout5 = findViewById(R.id.linearLayout5);

    }

    private void getUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);
    }

    private void moveToDescription(ItemCatalogo item) {
        Intent intent = new Intent(this,   DescripcionMisCatalogosPujadosActivity.class);
        intent.putExtra("MisSubastas",item);
        intent.putExtra("estadoLoggeado", estaRegistrado);
        startActivity(intent);
    }

    private void getDatos() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<ResponseItemsCatalog> call = as.getItemsParticipados(user, "Bearer "+ token);
        call.enqueue(new Callback<ResponseItemsCatalog>() {
            @Override
            public void onResponse(Call<ResponseItemsCatalog> call, Response<ResponseItemsCatalog> response) {
                if(response.isSuccessful()) {
                    ResponseItemsCatalog itemsCatalogo = response.body();
                    catalogos.addAll(itemsCatalogo.getData());
                    listRecyclerView5.getAdapter().notifyDataSetChanged();
                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Error al obtener los Catalogos", Toast.LENGTH_LONG);
                    toast1.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseItemsCatalog> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al obtener los Catalogos", Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }
}