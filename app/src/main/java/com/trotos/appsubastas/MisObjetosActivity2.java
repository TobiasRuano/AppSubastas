package com.trotos.appsubastas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trotos.appsubastas.Modelos.Item;
import com.trotos.appsubastas.Modelos.ResponseItems;
import com.trotos.appsubastas.Modelos.Auction;
import com.trotos.appsubastas.Modelos.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MisObjetosActivity2<animFadeIn> extends AppCompatActivity {

    TextView nameDescriptionTextView4;
    TextView stateDescriptionTextView4;
    TextView categoryDescriptionTextView4;
    RecyclerView listRecyclerView4;

    Animation animFadeIn;
    boolean banderaAnimation = false;
    boolean estaRegistrado = true;
    ViewGroup.LayoutParams params;
    LinearLayout linearLayout4;

    Auction element;
    User user;

    List<Item> catalogos= new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_objetos2);

        //estaRegistrado = getIntent().getBooleanExtra("estadoLoggeado", false);
        getUser();
        getDatos();
        init();
    }


    public void init(){
        RecyclerView recyclerView4 = findViewById(R.id.listRecyclerView4);
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));
        listRecyclerView4  = findViewById(R.id.listRecyclerView4);
        MyAdapterMisObjetos myAdapterMisObjetos = new MyAdapterMisObjetos(catalogos, estaRegistrado, this, new MyAdapterMisObjetos.OnItemClickListener() {
            @Override
            public void onItemClick(Item item) {
                moveToDescription(item);
            }
        });
        recyclerView4.setAdapter(myAdapterMisObjetos);

        nameDescriptionTextView4 = findViewById(R.id.nameDescriptionTextView4);
        //stateDescriptionTextView4 = findViewById(R.id.stateDescriptionTextView4);
        //categoryDescriptionTextView4 = findViewById(R.id.categoryDescriptionTextView4);

        linearLayout4 = findViewById(R.id.linearLayout4);
    }

    private void moveToDescription(Item item) {
        Intent intent = new Intent(this,   DetalleMisObjetosActivity.class);
        intent.putExtra("MisObjetos",item);
        intent.putExtra("estadoLoggeado", estaRegistrado);
        startActivity(intent);
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
                }
            }

            @Override
            public void onFailure(Call<ResponseItems> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al obtener los Items Propuestos", Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }
}