package com.trotos.appsubastas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.trotos.appsubastas.Modelos.Bid;
import com.trotos.appsubastas.Modelos.ItemCatalogo;
import com.trotos.appsubastas.Modelos.ResponseBids;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HistorialPujasDescripcionActivity extends AppCompatActivity {

    ItemCatalogo item;
    List<Bid> bids = new ArrayList<>();
    ArrayList<String> aux = new ArrayList<>();
    ListView list;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pujas_descripcion);

        list = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, aux);
        list.setAdapter(adapter);
        item = (ItemCatalogo) getIntent().getSerializableExtra("itemCatalogo");

        getBids();
    }

    private void getBids() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<ResponseBids> call = as.getBids(item, "Bearer "+ token);

        call.enqueue(new Callback<ResponseBids>() {
            @Override
            public void onResponse(Call<ResponseBids> call, Response<ResponseBids> response) {
                if(response.isSuccessful()) {
                    ResponseBids responseBids = response.body();
                    bids.addAll(responseBids.getData());

                    for (int i = 0; i < bids.size(); i++) {
                        aux.add("Nueva puja de: " + bids.get(i).getAmount());
                    }
                    System.out.println(aux.size());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Error al obtener las ofertas", Toast.LENGTH_LONG);
                    toast1.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBids> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }
}