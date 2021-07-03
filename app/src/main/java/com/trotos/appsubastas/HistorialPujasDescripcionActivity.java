package com.trotos.appsubastas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.trotos.appsubastas.Modelos.Bid;

import java.util.ArrayList;
import java.util.List;

public class HistorialPujasDescripcionActivity extends AppCompatActivity {

    List<Bid> bids = new ArrayList<>();
    ArrayList<String> aux = new ArrayList<>();
    ListView list;
    ArrayAdapter adapter;
    Button volverButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pujas_descripcion);
        volverButton = findViewById(R.id.salirHistorialPujas);

        list = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, aux);
        list.setAdapter(adapter);
        bids = (List<Bid>) getIntent().getSerializableExtra("bids");

        formatText();

        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void formatText() {
        if(bids != null) {
            for (int i = 0; i < bids.size(); i++) {
                if(i == bids.size() - 1) {
                    aux.add("Oferta Maxima de: " + bids.get(i).getAmount());
                } else {
                    aux.add("Anonimo a ofertado: " + bids.get(i).getAmount());
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}