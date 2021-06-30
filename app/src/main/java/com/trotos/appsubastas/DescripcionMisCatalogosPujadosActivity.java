package com.trotos.appsubastas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.trotos.appsubastas.Modelos.Bid;
import com.trotos.appsubastas.Modelos.ItemCatalogo;
import com.trotos.appsubastas.Modelos.ResponseBids;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DescripcionMisCatalogosPujadosActivity extends AppCompatActivity {

    TextView titleTextView5;
    TextView precioBaseTextView5;
    TextView valorActualTextView5;
    TextView descriptionTextView5;
    TextView monedaBaseTextView5;
    TextView monedaActualTextView5;

    EditText editarNumeroDeTexto5;
    Button botonOfertar5;
    Button botonRegistrar5;

    TextView valorActualOVendido5;
    TextView precioBaseView5;
    TextView historialPujasView5;

    List<CarouselItem> list = new ArrayList<>();
    List<Bid> bids = new ArrayList<>();
    ArrayList<String> aux = new ArrayList<>();

    ItemCatalogo element;
    boolean estaRegistrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_mis_catalogos_pujados);

        element = (ItemCatalogo) getIntent().getSerializableExtra("MisSubastas");
        estaRegistrado = (Boolean) getIntent().getBooleanExtra("estadoLoggeado", false);
        configureUI();
        cargar();
        getBids();
        verHistorialPujas();
        //ofertar();
    }

    private void logIn() {
        botonRegistrar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DescripcionMisCatalogosPujadosActivity.this, IniciarSesionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void configureUI() {
        titleTextView5 = findViewById(R.id.titleDescriptionTextView5);
        precioBaseTextView5 = findViewById(R.id.precioBaseDescriptionTextView5);
        valorActualTextView5 = findViewById(R.id.valorActualDescriptionTextView5);
        descriptionTextView5 = findViewById(R.id.fullTitleDescriptionTextView5);
        monedaBaseTextView5 = findViewById(R.id.monedaBaseDescriptionTextView5);
        monedaActualTextView5 = findViewById(R.id.monedaActualDescriptionTextView5);

        titleTextView5.setText(element.getTitle());
        //titleTextView5.setTextColor(Color.parseColor(element.getColor()));

        String precioBaseText5 = String.format("%,d", element.getBasePrice());
        precioBaseTextView5.setText(precioBaseText5);
        //String valorActualText5 = String.format("%,d", element.getBasePrice());
        valorActualTextView5.setText("testt");
        //valorActualTextView5.setTextColor(Color.GRAY);
        descriptionTextView5.setText(element.getDescription());
        monedaBaseTextView5.setText(element.getCurrency());
        monedaActualTextView5.setText(element.getCurrency());

        //editarNumeroDeTexto5 = findViewById(R.id.editarNumeroDeTexto5);
        //botonOfertar5 = findViewById(R.id.botonOfertar5);
        valorActualOVendido5 = findViewById(R.id.valorActualOVendido5);
        precioBaseView5 = findViewById(R.id.precioBaseView5);
        //botonRegistrar5 = findViewById(R.id.botonRegistrar5);
        historialPujasView5 = findViewById(R.id.historialPujasView5);

        descriptionTextView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DetalleDeLaDescripcionActivity.class);
                intent.putExtra("valorDescription", descriptionTextView5.getText().toString());
                startActivity(intent);
            }
        });

        historialPujasView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DetalleDeLaDescripcionActivity.class);
                //HARDCODEADO


                intent.putExtra("historialPujasDetalle", descriptionTextView5.getText().toString());
                startActivity(intent);
            }
        });


        if(!estaRegistrado){
            //editarNumeroDeTexto5.setVisibility(View.GONE);
            //botonOfertar5.setVisibility(View.GONE);
            valorActualOVendido5.setVisibility(View.GONE);
            monedaActualTextView5.setVisibility(View.GONE);
            valorActualTextView5.setVisibility(View.GONE);
            precioBaseTextView5.setVisibility(View.GONE);
            monedaBaseTextView5.setVisibility(View.GONE);
            precioBaseView5.setVisibility(View.GONE);
            historialPujasView5.setVisibility(View.GONE);
        }else {
            //botonRegistrar5.setVisibility(View.GONE);
        }

        String estado = element.getStatus();

        if(estado == null) {
            estado = "Auctioning"; // para que no crashee
        }
        System.out.println(element.getStatus());
        switch (estado) {
            case "Auctioning":
                valorActualOVendido5.setText("Valor Actual");
                valorActualTextView5.setTextColor(Color.parseColor("#FF669900"));
                break;
            case "Programmed":
                valorActualOVendido5.setVisibility(View.GONE);
                valorActualTextView5.setVisibility(View.GONE);
                monedaActualTextView5.setVisibility(View.GONE);
                //editarNumeroDeTexto5.setVisibility(View.GONE);
                //botonOfertar5.setVisibility(View.GONE);
                historialPujasView5.setVisibility(View.GONE);
                precioBaseTextView5.setTextSize(30);
                precioBaseTextView5.setTextColor(Color.parseColor("#FF669900"));
                precioBaseView5.setTextSize(30);
                precioBaseView5.setTextColor(Color.parseColor("#FF669900"));
                break;
            case "Ended":
                //editarNumeroDeTexto5.setVisibility(View.GONE);
                //botonOfertar5.setVisibility(View.GONE);
                valorActualOVendido5.setText("Vendido:");
                valorActualTextView5.setTextColor(Color.parseColor("#FF669900"));
                break;
        }
    }


    private void cargar() {
        ImageCarousel carousel = findViewById(R.id.carousel5);
        carousel.registerLifecycle(getLifecycle());

        list.add(
                new CarouselItem(
                        element.getUrlImage()
                )
        );
        carousel.setData(list);
    }

    private void showAlert(String titulo, String mensaje) {
        new AlertDialog.Builder(DescripcionMisCatalogosPujadosActivity.this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }

    /*
        private void ofertar() {
            botonOfertar5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int valorPrecioActual = element.getBasePrice();
                    int valorPrecioBase = element.getBasePrice();
                    boolean hayError = false;

                    String categoria = getIntent().getStringExtra("categoria");
                    if (categoria == null) {
                        categoria = "oro";
                    }

                    int valorPuja = 0;
                    String texto = editarNumeroDeTexto5.getText().toString();
                    if(!texto.equals("")){
                        valorPuja = Integer.parseInt(editarNumeroDeTexto5.getText().toString());
                    }


                    if(valorPuja == 0){
                        showAlert("Monto vacio","Debe ingresar un Monto para poder Ofertar.");
                    } else if(valorPuja > (valorPrecioActual * 1.2) && (categoria.equals("Oro") || categoria.equals("Platino")) ){
                        showAlert("Monto inválido","La oferta no puede exceder el 20 % de la última oferta realizada.");
                    } else if(valorPuja <= (valorPrecioBase * 0.01 + valorPrecioActual)){
                        showAlert("Monto inválido","La oferta debe ser 1 % mayor al valor base del bien.");
                    } else if(valorPuja <= valorPrecioActual){
                        showAlert("Monto inválido","La oferta no puede ser menor o igual a la última oferta realizada.");
                    } else{
                        pujar(valorPuja);
                    }
                }
            });
        }

        private void pujar(int offer) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://URL-de-la-API.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiUtils as = retrofit.create(ApiUtils.class);

            element.setValorActual(offer);

            Call<ItemCatalogo> call = as.postBid(element);

            call.enqueue(new Callback<ItemCatalogo>() {
                @Override
                public void onResponse(Call<ItemCatalogo> call, Response<ItemCatalogo> response) {
                    if(response.body() != null) {
                        Toast toast1 = Toast.makeText(getApplicationContext(),"Oferta realizada con exito!", Toast.LENGTH_LONG);
                        toast1.show();
                    }
                }

                @Override
                public void onFailure(Call<ItemCatalogo> call, Throwable t) {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Error al ofertar!", Toast.LENGTH_LONG);
                    toast1.show();
                }
            });
        }
    */
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
                    bids.addAll(responseBids.getData());
                    int valorActual = bids.get(bids.size() - 1).getAmount();
                    valorActualTextView5.setText(String.valueOf(valorActual));
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
        historialPujasView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HistorialPujasDescripcionActivity.class);
                intent.putExtra("bids", (Serializable) bids);
                startActivity(intent);
            }
        });
    }
}