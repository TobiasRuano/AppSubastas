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
import com.trotos.appsubastas.Modelos.Auction;
import com.trotos.appsubastas.Modelos.ResponseItemsCatalog;

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

    Auction element;

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
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<ResponseItemsCatalog> call = as.getItemsSubasta(element);

        System.out.println(element.getId());


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
                //Toast toast2 = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }




}