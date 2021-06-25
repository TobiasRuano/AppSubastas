package com.trotos.appsubastas;

import android.content.Intent;
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

public class MisSubastasActivity<animFadeIn> extends AppCompatActivity {

    TextView nameDescriptionTextView5;
    TextView stateDescriptionTextView5;
    TextView categoryDescriptionTextView5;
    RecyclerView listRecyclerView5;

    Animation animFadeIn;
    boolean banderaAnimation = false;
    boolean estaRegistrado = true;
    ViewGroup.LayoutParams params;
    LinearLayout linearLayout5;

    Subasta element;

    List<ItemCatalogo> catalogos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_subastas);

        //estaRegistrado = getIntent().getBooleanExtra("estadoLoggeado", false);

        init();
        //getDatos();
    }


    public void init(){
        /*
        //getDatos();
        */

        MyAdapterMisSubastas myAdapterMisSubastas = new MyAdapterMisSubastas(catalogos, estaRegistrado, this, new MyAdapterMisSubastas.OnItemClickListener() {
            @Override
            public void onItemClick(ItemCatalogo item) {
                moveToDescription(item);
            }
        });

        RecyclerView recyclerView5 = findViewById(R.id.listRecyclerView5);
        recyclerView5.setHasFixedSize(true);
        recyclerView5.setLayoutManager(new LinearLayoutManager(this));
        recyclerView5.setAdapter(myAdapterMisSubastas);

        listRecyclerView5  = findViewById(R.id.listRecyclerView5);

        nameDescriptionTextView5 = findViewById(R.id.nameDescriptionTextView5);
        //stateDescriptionTextView5 = findViewById(R.id.stateDescriptionTextView5);
        //categoryDescriptionTextView5 = findViewById(R.id.categoryDescriptionTextView5);

        linearLayout5 = findViewById(R.id.linearLayout5);

    }

    private void moveToDescription(ItemCatalogo item) {
        Intent intent = new Intent(this,   DestalleMisSubastasActivity.class);
        intent.putExtra("MisSubastas",item);
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
                listRecyclerView5.getAdapter().notifyDataSetChanged();
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