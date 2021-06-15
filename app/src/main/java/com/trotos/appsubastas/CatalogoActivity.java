package com.trotos.appsubastas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class CatalogoActivity<animFadeIn> extends AppCompatActivity {

    TextView nameDescriptionTextView;
    TextView stateDescriptionTextView;
    TextView categoryDescriptionTextView;
    RecyclerView listRecyclerView2;

    Animation animFadeIn;
    boolean banderaAnimation = false;
    boolean estaRegistrado;
    ViewGroup.LayoutParams params;
    LinearLayout linearLayout1;

    Subasta element;

    List<ItemCatalogo> catalogos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        element = (Subasta) getIntent().getSerializableExtra("subasta");
        estaRegistrado = getIntent().getBooleanExtra("estadoLoggeado", false);
        nameDescriptionTextView = findViewById(R.id.nameDescriptionTextView);
        stateDescriptionTextView = findViewById(R.id.stateDescriptionTextView);
        categoryDescriptionTextView = findViewById(R.id.categoryDescriptionTextView);

        nameDescriptionTextView.setText(element.getName());
        nameDescriptionTextView.setTextColor(Color.parseColor(element.getColor()));

        stateDescriptionTextView.setText(element.getStatus());

        categoryDescriptionTextView.setText(element.getCategory());
        categoryDescriptionTextView.setTextColor(Color.GRAY);

        //HARDCODEADO
        catalogos = new ArrayList<>();
        catalogos = element.getCatalogos();

        init();
        //getDatos();
    }


    public void init(){

        /*

        catalogos = new ArrayList<>();
        //getDatos();

         */

        MyAdapterCatalogo myAdapterCatalogo = new MyAdapterCatalogo(catalogos, estaRegistrado, this, new MyAdapterCatalogo.OnItemClickListener() {
            @Override
            public void onItemClick(ItemCatalogo item) {
                moveToDescription(item);
            }
        });

        RecyclerView recyclerView2 = findViewById(R.id.listRecyclerView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(myAdapterCatalogo);

        listRecyclerView2  = findViewById(R.id.listRecyclerView2);

        nameDescriptionTextView = findViewById(R.id.nameDescriptionTextView);
        stateDescriptionTextView = findViewById(R.id.stateDescriptionTextView);
        categoryDescriptionTextView = findViewById(R.id.categoryDescriptionTextView);

        linearLayout1 = findViewById(R.id.linearLayout1);

    }

    private void moveToDescription(ItemCatalogo item) {
        Intent intent = new Intent(this,   com.trotos.appsubastas.DescripcionActivity.class);
        intent.putExtra("Catalogos",item);
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
                listRecyclerView2.getAdapter().notifyDataSetChanged();
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