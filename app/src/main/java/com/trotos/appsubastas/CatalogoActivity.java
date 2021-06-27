package com.trotos.appsubastas;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trotos.appsubastas.Modelos.ItemCatalogo;
import com.trotos.appsubastas.Modelos.Auction;
import com.trotos.appsubastas.Modelos.ResponseItemsCatalog;
import com.trotos.appsubastas.Modelos.User;

import java.lang.reflect.Type;
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
    String category;
    String currency;
    User user;
    List<ItemCatalogo> catalogos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        element = (Auction) getIntent().getSerializableExtra("subasta");
        category = (String) getIntent().getSerializableExtra("category");
        currency = (String) getIntent().getSerializableExtra("currency");
        estaRegistrado = getIntent().getBooleanExtra("estadoLoggeado", false);
        nameDescriptionTextView = findViewById(R.id.nameDescriptionTextView);
        stateDescriptionTextView = findViewById(R.id.stateDescriptionTextView);
        categoryDescriptionTextView = findViewById(R.id.categoryDescriptionTextView);
        nameDescriptionTextView.setText(element.getTitle());
        //nameDescriptionTextView.setTextColor(Color.parseColor(element.getColor()));
        stateDescriptionTextView.setText(element.getStatus());
        categoryDescriptionTextView.setText(element.getCategory());
        init();
        getUser();
        getDatos();
    }

    public void init(){
        MyAdapterCatalogo myAdapterCatalogo = new MyAdapterCatalogo(currency, catalogos, estaRegistrado, this, new MyAdapterCatalogo.OnItemClickListener() {
            @Override
            public void onItemClick(ItemCatalogo item) {
                moveToDescription(item);
            }
        });

        listRecyclerView2  = findViewById(R.id.listRecyclerView2);
        listRecyclerView2.setHasFixedSize(true);
        listRecyclerView2.setLayoutManager(new LinearLayoutManager(this));
        listRecyclerView2.setAdapter(myAdapterCatalogo);

        nameDescriptionTextView = findViewById(R.id.nameDescriptionTextView);
        stateDescriptionTextView = findViewById(R.id.stateDescriptionTextView);
        categoryDescriptionTextView = findViewById(R.id.categoryDescriptionTextView);

        linearLayout1 = findViewById(R.id.linearLayout1);
    }

    private void moveToDescription(ItemCatalogo item) {
        Intent intent = new Intent(this, DescripcionActivity.class);
        intent.putExtra("Catalogos",item);
        intent.putExtra("estadoLoggeado", estaRegistrado);

        switch (user.getCategory()){
            case "Comun":
                if(category.equals("Comun"))
                    startActivity(intent);
                break;
            case "Especial":
                if(category.equals("Comun") || category.equals("Bronce"))
                    startActivity(intent);
                break;
            case "Plata":
                if(category.equals("Comun") || category.equals("Bronce") || category.equals("Plata"))
                    startActivity(intent);
                break;
            case "Oro":
                if(!category.equals("Platino"))
                    startActivity(intent);
                break;
            case "Platino":
                startActivity(intent);
                break;
            default:
        }
    }

    private void getUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);
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