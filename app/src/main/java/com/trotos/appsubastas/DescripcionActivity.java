package com.trotos.appsubastas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trotos.appsubastas.Modelos.Bid;
import com.trotos.appsubastas.Modelos.ItemCatalogo;
import com.trotos.appsubastas.Modelos.ResponseBids;
import com.trotos.appsubastas.Modelos.ResponseSuccessfulBid;
import com.trotos.appsubastas.Modelos.User;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DescripcionActivity extends AppCompatActivity {

    TextView titleTextView3;
    TextView precioBaseDescriptionTextView3;
    TextView valorActualDescriptionTextView3;
    TextView descriptionTextView3;
    TextView monedaBaseDescriptionTextView3;
    TextView monedaActualDescriptionTextView3;
    TextView estadoSubasta;

    EditText editarNumeroDeTexto;
    Button botonOfertar;
    Button botonRegistrar;

    View divider2, divider3;
    LinearLayout linearLayoutfullTitleDescripcion;

    TextView valorActualOVendido;
    TextView precioBaseView;
    TextView historialPujasView;
    int valorPuja = 0;
    User user;

    List<CarouselItem> list = new ArrayList<>();
    List<Bid> bids = new ArrayList<>();

    ItemCatalogo element;
    boolean estaRegistrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);

        element = (ItemCatalogo) getIntent().getSerializableExtra("Catalogos");
        getUser();
        estaRegistrado = (Boolean) getIntent().getBooleanExtra("estadoLoggeado", false);
        configureUI();
        cargar();
        getBids();
        verHistorialPujas();
        ofertar();
        logIn();
    }

    private void logIn() {
        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DescripcionActivity.this, IniciarSesionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void configureUI() {
        titleTextView3 = findViewById(R.id.titleTextView3);
        precioBaseDescriptionTextView3 = findViewById(R.id.precioBaseDescriptionTextView3);
        valorActualDescriptionTextView3 = findViewById(R.id.valorActualDescriptionTextView3);
        descriptionTextView3 = findViewById(R.id.descriptionTextView3);
        monedaBaseDescriptionTextView3 = findViewById(R.id.monedaBaseDescriptionTextView3);
        monedaActualDescriptionTextView3 = findViewById(R.id.monedaActualDescriptionTextView3);
        estadoSubasta = findViewById(R.id.estadoDetalleDescriptionTextView5);

        titleTextView3.setText(element.getTitle());
        descriptionTextView3.setText(element.getDescription());
        monedaBaseDescriptionTextView3.setText(element.getCurrency());
        monedaActualDescriptionTextView3.setText(element.getCurrency());
        estadoSubasta.setText(element.getTimeStatus());

        valorActualDescriptionTextView3.setTextColor(Color.GRAY);

        editarNumeroDeTexto = findViewById(R.id.editarNumeroDeTexto);
        botonOfertar = findViewById(R.id.botonOfertar);
        valorActualOVendido = findViewById(R.id.valorActualOVendido);
        divider2 = findViewById(R.id.divider2);
        divider3 = findViewById(R.id.divider3);

        precioBaseView = findViewById(R.id.precioBaseView);
        botonRegistrar = findViewById(R.id.botonRegistrar);
        historialPujasView = findViewById(R.id.historialPujasView);
        linearLayoutfullTitleDescripcion = findViewById(R.id.linearLayoutfullTitleDescripcion);

        precioBaseView.setText(String.valueOf(element.getBasePrice()));
        valorActualOVendido.setText(String.valueOf(element.getBasePrice()));

        linearLayoutfullTitleDescripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DetalleDeLaDescripcionActivity.class);
                intent.putExtra("valorDescription", descriptionTextView3.getText());
                startActivity(intent);
            }
        });

        if(!estaRegistrado){
            editarNumeroDeTexto.setVisibility(View.GONE);
            botonOfertar.setVisibility(View.GONE);
            valorActualOVendido.setVisibility(View.GONE);
            divider2.setVisibility(View.GONE);
            monedaActualDescriptionTextView3.setVisibility(View.GONE);
            valorActualDescriptionTextView3.setVisibility(View.GONE);
            precioBaseDescriptionTextView3.setVisibility(View.GONE);
            monedaBaseDescriptionTextView3.setVisibility(View.GONE);
            precioBaseView.setVisibility(View.GONE);
            divider3.setVisibility(View.GONE);

            historialPujasView.setVisibility(View.INVISIBLE);
        }else {
            botonRegistrar.setVisibility(View.GONE);
        }

        String estado = element.getStatus();
        if(estado == null) {
            estado = "Programed";
        }

        switch (estado) {
            case "Auctioning":
                valorActualDescriptionTextView3.setTextColor(Color.parseColor("#FF669900"));
                break;
            case "Programmed":
                valorActualOVendido.setVisibility(View.GONE);
                divider2.setVisibility(View.GONE);
                valorActualDescriptionTextView3.setVisibility(View.GONE);
                monedaActualDescriptionTextView3.setVisibility(View.GONE);
                editarNumeroDeTexto.setVisibility(View.GONE);
                botonOfertar.setVisibility(View.GONE);
                historialPujasView.setVisibility(View.INVISIBLE);
                precioBaseDescriptionTextView3.setTextColor(Color.parseColor("#FF669900"));
                precioBaseView.setTextColor(Color.parseColor("#FF669900"));
                break;
            case "Ended":
                editarNumeroDeTexto.setVisibility(View.GONE);
                botonOfertar.setVisibility(View.GONE);
                valorActualDescriptionTextView3.setTextColor(Color.parseColor("#FF669900"));
                break;
        }
    }


    private void cargar() {
        ImageCarousel carousel = findViewById(R.id.carousel);
        carousel.registerLifecycle(getLifecycle());

        list.add(
                new CarouselItem(
                        element.getUrlImage()
                )
        );
        carousel.setData(list);
    }

    private void showAlert(String titulo, String mensaje) {
        new AlertDialog.Builder(DescripcionActivity.this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }


    private void ofertar() {
        botonOfertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto = editarNumeroDeTexto.getText().toString();
                if(!texto.isEmpty()){
                    valorPuja = Integer.parseInt(editarNumeroDeTexto.getText().toString());
                }

                if(valorPuja == 0){
                    showAlert("Monto vacio","Debe ingresar un Monto para poder Ofertar.");
                } else if(valorPuja <= element.getBasePrice()) {
                    showAlert("Monto Incorrecto","Debe ingresar un Monto mayor al precio base.");
                }else{
                    placeBid();
                }
            }
        });
    }

    private void getUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);
    }

    private void placeBid() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);

        Bid bid = new Bid(element.getId(), valorPuja, user.getId());
        Call<ResponseSuccessfulBid> call = as.postBid(bid, "Bearer "+ token);

        call.enqueue(new Callback<ResponseSuccessfulBid>() {
            @Override
            public void onResponse(Call<ResponseSuccessfulBid> call, Response<ResponseSuccessfulBid> response) {
                Toast toast1;
                if(response.isSuccessful()) {
                    toast1 = Toast.makeText(getApplicationContext(), "Oferta realizada con exito!", Toast.LENGTH_LONG);
                    toast1.show();
                    getBids();
                } else {
                    if(response.code() == 410) {
                        toast1 = Toast.makeText(getApplicationContext(), "Debes agregar un medio de pago.", Toast.LENGTH_LONG);
                        toast1.show();
                    } else if(response.code() == 406) {
                        toast1 = Toast.makeText(getApplicationContext(), "La oferta no entra dentro de los paramtros validos para esta categoria.", Toast.LENGTH_LONG);
                        toast1.show();
                    } else if(response.code() == 400) {
                        toast1 = Toast.makeText(getApplicationContext(), "Hubo un error en el servidor.", Toast.LENGTH_LONG);
                        toast1.show();
                    } else if(response.code() == 409) {
                        toast1 = Toast.makeText(getApplicationContext(), "La subasta ha terminado!.", Toast.LENGTH_LONG);
                        toast1.show();
                    } else {
                        toast1 = Toast.makeText(getApplicationContext(), "Hubo un error en el servidor.", Toast.LENGTH_LONG);
                        toast1.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseSuccessfulBid> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),t.getLocalizedMessage(), Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }

    private void getBids() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<ResponseBids> call = as.getBids(element, "Bearer "+ token);

        call.enqueue(new Callback<ResponseBids>() {
            @Override
            public void onResponse(Call<ResponseBids> call, Response<ResponseBids> response) {
                if(response.isSuccessful()) {
                    ResponseBids responseBids = response.body();
                    bids.clear();
                    bids.addAll(responseBids.getData());
                    if(bids.size() != 0) {
                        valorActualOVendido.setText(String.valueOf(bids.get(bids.size() - 1).getAmount()));
                    }
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

    private void verHistorialPujas() {
        historialPujasView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bids != null) {
                    Intent intent = new Intent(getApplicationContext(), HistorialPujasDescripcionActivity.class);
                    intent.putExtra("bids", (Serializable) bids);
                    startActivity(intent);
                }
            }
        });
    }
}