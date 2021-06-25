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
import com.trotos.appsubastas.Modelos.Auction;
import com.trotos.appsubastas.Modelos.ResponseItemsCatalog;

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

    Auction element;

    List<ItemCatalogo> catalogos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        element = (Auction) getIntent().getSerializableExtra("subasta");
        estaRegistrado = getIntent().getBooleanExtra("estadoLoggeado", false);
        nameDescriptionTextView = findViewById(R.id.nameDescriptionTextView);
        stateDescriptionTextView = findViewById(R.id.stateDescriptionTextView);
        categoryDescriptionTextView = findViewById(R.id.categoryDescriptionTextView);

        nameDescriptionTextView.setText(element.getTitle());
        //nameDescriptionTextView.setTextColor(Color.parseColor(element.getColor()));

        stateDescriptionTextView.setText(element.getStatus());

        categoryDescriptionTextView.setText(element.getCategory());
        categoryDescriptionTextView.setTextColor(Color.GRAY);

        //HARDCODEADO

        init();
        getDatos();
    }


    public void init(){
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
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<ResponseItemsCatalog> call = as.getItemsSubasta(element);
        call.enqueue(new Callback<ResponseItemsCatalog>() {
            @Override
            public void onResponse(Call<ResponseItemsCatalog> call, Response<ResponseItemsCatalog> response) {
                if(response.isSuccessful()) {
                    ResponseItemsCatalog itemsCatalogo = response.body();
                    catalogos.addAll(itemsCatalogo.getData());
                    listRecyclerView2.getAdapter().notifyDataSetChanged();
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