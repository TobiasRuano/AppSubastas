package com.trotos.appsubastas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trotos.appsubastas.Modelos.ItemCatalogo;
import com.trotos.appsubastas.Modelos.Subasta;

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

    Subasta element;

    List<ItemCatalogo> catalogos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_objetos2);

        //estaRegistrado = getIntent().getBooleanExtra("estadoLoggeado", false);

        init();
        //getDatos();
    }


    public void init(){

        //HARDCODEADO
        catalogos = new ArrayList<>();
        catalogos.add(new ItemCatalogo("123456","En Curso","Rolex",1234,1234567,"#575457","Lorem ipsum dolor sit amet consectetur adipiscing elit aptent platea facilisi tortor nunc imperdiet.","Breve descripcion del item", "ARS",000));
        catalogos.add(new ItemCatalogo("123456","En Curso","Casio",1234,123456,"#A70447","Lorem2 ipsum dolor sit amet consectetur adipiscing elit aptent platea facilisi tortor nunc imperdiet.2","Breve descripcion del item", "ARS", 001));
        catalogos.add(new ItemCatalogo("123456","En Curso","Paddle Watch",12,123,"#075447","Lorem3 ipsum dolor sit amet consectetur adipiscing elit aptent platea facilisi tortor nunc imperdiet.3","Breve descripcion del item", "USD", 002));

        /*
        //getDatos();
        */

        MyAdapterMisObjetos myAdapterMisObjetos = new MyAdapterMisObjetos(catalogos, estaRegistrado, this, new MyAdapterMisObjetos.OnItemClickListener() {
            @Override
            public void onItemClick(ItemCatalogo item) {
                moveToDescription(item);
            }
        });

        RecyclerView recyclerView4 = findViewById(R.id.listRecyclerView4);
        recyclerView4.setHasFixedSize(true);
        recyclerView4.setLayoutManager(new LinearLayoutManager(this));
        recyclerView4.setAdapter(myAdapterMisObjetos);

        listRecyclerView4  = findViewById(R.id.listRecyclerView4);

        nameDescriptionTextView4 = findViewById(R.id.nameDescriptionTextView4);
        //stateDescriptionTextView4 = findViewById(R.id.stateDescriptionTextView4);
        //categoryDescriptionTextView4 = findViewById(R.id.categoryDescriptionTextView4);

        linearLayout4 = findViewById(R.id.linearLayout4);

    }

    private void moveToDescription(ItemCatalogo item) {
        Intent intent = new Intent(this,   DestalleMisObjetosActivity.class);
        intent.putExtra("MisObjetos",item);
        intent.putExtra("estadoLoggeado", estaRegistrado);
        startActivity(intent);
    }

    private void getDatos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.111/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);




        //System.out.println(element.getId());
        //System.out.println(element.getName());
        Call<List<ItemCatalogo>> call = as.getItemsSubasta(element.getId());

        System.out.println(element.getId());


        call.enqueue(new Callback<List<ItemCatalogo>>() {
            @Override
            public void onResponse(Call<List<ItemCatalogo>> call, Response<List<ItemCatalogo>> response) {

                List<ItemCatalogo> itemsCatalogo = response.body();


                for(ItemCatalogo itemCatalogo: itemsCatalogo){
                    catalogos.add(itemCatalogo);
                }


                //RecyclerView recyclerView2 = findViewById(R.id.listRecyclerView2);
                listRecyclerView4.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ItemCatalogo>> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al obtener los Catalogos", Toast.LENGTH_LONG);
                //Toast toast2 = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }




}