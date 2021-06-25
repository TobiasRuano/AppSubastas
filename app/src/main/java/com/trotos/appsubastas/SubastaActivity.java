 package com.trotos.appsubastas;

 import android.content.Intent;
 import android.content.SharedPreferences;
 import android.os.Bundle;
 import android.widget.Toast;

 import androidx.appcompat.app.AppCompatActivity;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;

 import com.trotos.appsubastas.Modelos.Auction;
 import com.trotos.appsubastas.Modelos.ResponseAuctions;

 import java.util.ArrayList;
 import java.util.List;

 import retrofit2.Call;
 import retrofit2.Callback;
 import retrofit2.Response;
 import retrofit2.Retrofit;
 import retrofit2.converter.gson.GsonConverterFactory;

 public class SubastaActivity extends AppCompatActivity {

     List<Auction> auctions = new ArrayList<>();
     Boolean estadoLoggeado;
     RecyclerView recyclerView;

     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subastas);
        init();
    }

    public void init(){
        estadoLoggeado = checkLogInStatus();
        getDatos();
        MyAdapterSubasta myAdapterSubasta = new MyAdapterSubasta(auctions, this, new MyAdapterSubasta.OnItemClickListener() {
            @Override
            public void onItemClick(Auction item) {
                moveToDescription(item);
            }
        });
        recyclerView = findViewById(R.id.listRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(  this    ));
        recyclerView.setAdapter(myAdapterSubasta);
    }

    private Boolean checkLogInStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);
        return !token.isEmpty();
    }

     private void getDatos() {
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl("http://10.0.2.2:3000/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
         ApiUtils as = retrofit.create(ApiUtils.class);
         Call<ResponseAuctions> call = as.getSubastas();

         call.enqueue(new Callback<ResponseAuctions>() {
             @Override
             public void onResponse(Call<ResponseAuctions> call, Response<ResponseAuctions> response) {
                 if(response.isSuccessful()) {
                     ResponseAuctions subastas = response.body();
                     auctions.addAll(subastas.getData());
                     recyclerView.getAdapter().notifyDataSetChanged();
                 } else {
                     Toast toast2 = Toast.makeText(getApplicationContext(), "Hubo un error al obtener los datos", Toast.LENGTH_LONG);
                     toast2.show();
                 }
             }

             @Override
             public void onFailure(Call<ResponseAuctions> call, Throwable t) {
                 Toast toast2 = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG);
                 toast2.show();
             }
         });
     }

     private void moveToDescription(Auction item) {
         Intent intent = new Intent(this, CatalogoActivity.class);
         intent.putExtra("subasta", item);
         intent.putExtra("categoria", item.getCategory());
         intent.putExtra("estadoLoggeado", estadoLoggeado);
         startActivity(intent);
     }
 }