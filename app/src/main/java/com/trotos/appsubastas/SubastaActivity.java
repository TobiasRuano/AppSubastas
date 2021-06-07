 package com.trotos.appsubastas;

 import android.content.Intent;
 import android.os.Bundle;
 import android.widget.Toast;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import com.trotos.appsubastas.Modelos.Subasta;

 import java.util.ArrayList;
 import java.util.List;

 import retrofit2.Call;
 import retrofit2.Callback;
 import retrofit2.Response;
 import retrofit2.Retrofit;
 import retrofit2.converter.gson.GsonConverterFactory;

 public class SubastaActivity extends AppCompatActivity {

     List<Subasta> subastas;
     Boolean estadoLoggeado;

     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subastas);
        init();
    }

    public void init(){
        subastas = new ArrayList<>();
        estadoLoggeado = (Boolean) getIntent().getSerializableExtra("estadoLoggeado");

        //HARDCODEADO
        //testCreateSubastas();
        getDatos();

        MyAdapterSubasta myAdapterSubasta = new MyAdapterSubasta(subastas, this, new MyAdapterSubasta.OnItemClickListener() {
            @Override
            public void onItemClick(Subasta item) {
                moveToDescription(item);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(  this    ));
        recyclerView.setAdapter(myAdapterSubasta);
        //recyclerView.getAdapter().
    }

     private void getDatos() {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl("http://192.168.1.111/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         ApiUtils as = retrofit.create(ApiUtils.class);
         Call<List<Subasta>> call = as.getSubastas();

         call.enqueue(new Callback<List<Subasta>>() {
             @Override
             public void onResponse(Call<List<Subasta>> call, Response<List<Subasta>> response) {

                 List<Subasta> subastas2 = response.body();
                 for(Subasta subasta: subastas2){
                    subastas.add(subasta);
                 }


                 RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
                 recyclerView.getAdapter().notifyDataSetChanged();
             }

                     //subastas.addAll(response.body());
                 //}
             //}

             @Override
             public void onFailure(Call<List<Subasta>> call, Throwable t) {
                 //Toast toast1 = Toast.makeText(getApplicationContext(),"Error al obtener las subastas", Toast.LENGTH_LONG);
                 Toast toast2 = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                 toast2.show();
             }
         });
     }

     //Funcion test sin API
     //private void testCreateSubastas() {
         //subastas.add(new Subasta("#775447","RELOJES","Activa - Restante 15:32 min","Común",  null  ));
         //subastas.add(new Subasta("#215826","AUTOS","Comienza en 20:00 min","Especial", null    ));
         //subastas.add(new Subasta("#455668","CELULARES","Activa - Restante 15:32 min","Plata", null    ));
         //subastas.add(new Subasta("#798999","COMPUTACION","Comienza en 20:00 min","Oro", null    ));
         //subastas.add(new Subasta("#335435","ROPA","Activa - Restante 15:32 min","Platino", null    ));
         //subastas.add(new Subasta("#486648","CAMARAS","Comienza en 20:00 min","Oro", null    ));
     //}

     private void moveToDescription(Subasta item) {
         Intent intent = new Intent(this, CatalogoActivity.class);
         intent.putExtra("subasta",item);
         intent.putExtra("categoria", item.getCategory());
         intent.putExtra("estadoLoggeado", estadoLoggeado);
         startActivity(intent);
     }


 }