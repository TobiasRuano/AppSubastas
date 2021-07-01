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
        bids = (List<Bid>) getIntent().getSerializableExtra("bids");

        formatText();
    }

    private void formatText() {
        if(bids != null) {
            for (int i = 0; i < bids.size(); i++) {
                if(i == bids.size() - 1) {
                    aux.add("Oferta Maxima de: " + bids.get(i).getAmount());
                } else {
                    aux.add("Nueva puja de: " + bids.get(i).getAmount());
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}