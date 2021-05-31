 package com.trotos.appsubastas;

 import android.content.Intent;
 import android.os.Bundle;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import java.util.ArrayList;
 import java.util.List;

 public class SubastaActivity extends AppCompatActivity {

     List<Subastas> subastas;

     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subastas);
        init();
    }

    public void init(){
        subastas = new ArrayList<>();


        //HARDCODEADO
        subastas.add(new Subastas("#775447","RELOJES","Activa - Restante 15:32 min","Común",  null  ));
        subastas.add(new Subastas("#215826","AUTOS","Comienza en 20:00 min","Especial", null    ));
        subastas.add(new Subastas("#455668","CELULARES","Activa - Restante 15:32 min","Plata", null    ));
        subastas.add(new Subastas("#798999","COMPUTACION","Comienza en 20:00 min","Oro", null    ));
        subastas.add(new Subastas("#335435","ROPA","Activa - Restante 15:32 min","Platino", null    ));
        subastas.add(new Subastas("#486648","CAMARAS","Comienza en 20:00 min","Oro", null    ));


        MyAdapterSubasta myAdapterSubasta = new MyAdapterSubasta(subastas, this, new MyAdapterSubasta.OnItemClickListener() {
            @Override
            public void onItemClick(Subastas item) {
                moveToDescription(item);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(  this    ));
        recyclerView.setAdapter(myAdapterSubasta);
    }

     private void moveToDescription(Subastas item) {
         Intent intent = new Intent(this,   com.trotos.appsubastas.CatalogoActivity.class);
         intent.putExtra("Subastas",item);
         startActivity(intent);
     }


 }