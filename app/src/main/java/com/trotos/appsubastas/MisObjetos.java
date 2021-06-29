package com.trotos.appsubastas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trotos.appsubastas.Modelos.Item;
import com.trotos.appsubastas.Modelos.MPTarjeta;
import com.trotos.appsubastas.Modelos.ResponseItems;
import com.trotos.appsubastas.Modelos.Auction;
import com.trotos.appsubastas.Modelos.User;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MisObjetos<animFadeIn> extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FloatingActionButton newObjectButton;

    TextView nameDescriptionTextView4;
    TextView stateDescriptionTextView4;
    TextView categoryDescriptionTextView4;
    RecyclerView listRecyclerView4;

    Animation animFadeIn;
    boolean banderaAnimation = false;
    boolean estaRegistrado = true;
    ViewGroup.LayoutParams params;
    LinearLayout linearLayout4;

    User user;

    List<Item> catalogos = new ArrayList<Item>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_objetos);

        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.hide();
        }
        getUser();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.moLogueado);
        newObjectButton = findViewById(R.id.newObjectButton);

        getDatos();
        init();

        newObjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MisObjetos.this,AgregarObjeto.class);
                startActivityForResult(intent, 1);
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.moLogueado);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeLogueado:
                        startActivity(new Intent(MisObjetos.this, MenuLogueado.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.mpLogueado:
                        startActivity(new Intent(MisObjetos.this, MediosPagoActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.msLogueado:
                        startActivity(new Intent(MisObjetos.this, MisCatalogosPujadosActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.moLogueado:
                        break;
                    case R.id.usuarioLogueado:
                        startActivity(new Intent(MisObjetos.this, MiUsuario.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });
    }


    public void init(){

        MyAdapterMisObjetos myAdapterMisObjetos = new MyAdapterMisObjetos(catalogos, estaRegistrado, this, new MyAdapterMisObjetos.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                moveToDescription(item);
            }
        });

        RecyclerView recyclerView4 = findViewById(R.id.listRecyclerView4);
        //recyclerView4.setHasFixedSize(true);
        //recyclerView4.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView4.setAdapter(myAdapterMisObjetos);

        listRecyclerView4  = findViewById(R.id.listRecyclerView4);
        listRecyclerView4.setHasFixedSize(true);
        listRecyclerView4.setAdapter(myAdapterMisObjetos);
        listRecyclerView4.setLayoutManager(new LinearLayoutManager(this));

        nameDescriptionTextView4 = findViewById(R.id.nameDescriptionTextView4);
        //stateDescriptionTextView4 = findViewById(R.id.stateDescriptionTextView4);
        //categoryDescriptionTextView4 = findViewById(R.id.categoryDescriptionTextView4);

        linearLayout4 = findViewById(R.id.linearLayout4);
    }

    private void moveToDescription(Item item) {
        Intent intent = new Intent(this,   DescripcionMisObjetosActivity.class);
        intent.putExtra("MisObjetos", item);
        intent.putExtra("estadoLoggeado", estaRegistrado);
        startActivityForResult(intent, 2);
    }

    private void getUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);
    }

    private void getDatos() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);

        Call<ResponseItems> call = as.getObjetosPropuestos(user, "Bearer "+ token);

        call.enqueue(new Callback<ResponseItems>() {
            @Override
            public void onResponse(Call<ResponseItems> call, Response<ResponseItems> response) {
                if(response.isSuccessful()) {
                    ResponseItems items = response.body();
                    catalogos.addAll(items.getData());
                    listRecyclerView4.getAdapter().notifyDataSetChanged();
                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Respuesta con error", Toast.LENGTH_LONG);
                    toast1.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseItems> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al obtener los Catalogos", Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Item item = (Item) data.getSerializableExtra("nuevoObjeto");
                catalogos.add(item);
                listRecyclerView4.getAdapter().notifyDataSetChanged();
            }
        } else if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                Item item = (Item) data.getSerializableExtra("item");
                int index = -1;
                for(int i = 0; i < catalogos.size(); i++) {
                    if(catalogos.get(i).getId() == item.getId()) {
                        index = i;
                    }
                }
                if(index != -1) {
                    catalogos.set(index, item);
                }
                listRecyclerView4.getAdapter().notifyDataSetChanged();
            }
        }
    }
}