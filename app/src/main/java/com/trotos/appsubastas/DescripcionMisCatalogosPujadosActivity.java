package com.trotos.appsubastas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trotos.appsubastas.Modelos.Bid;
import com.trotos.appsubastas.Modelos.ItemCatalogo;
import com.trotos.appsubastas.Modelos.ResponseBids;
import com.trotos.appsubastas.Modelos.User;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    TextView estadosubastaTextView;
    View lineaViewEstadosubastaTextView;

    LinearLayout linearLayoutfullTitleDescripcion;

    EditText editarNumeroDeTexto5;
    Button botonOfertar5;
    Button botonRegistrar5;

    TextView valorActualOVendido5;
    TextView precioBaseView5;
    TextView historialPujasView5;

    CardView cvEnvio;
    TextView pendienteEnvio, enviadoEnvio, recibidoEnvio;

    List<CarouselItem> list = new ArrayList<>();
    List<Bid> bids = new ArrayList<>();
    ArrayList<String> aux = new ArrayList<>();
    User user;

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
        getUser();
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
        linearLayoutfullTitleDescripcion = findViewById(R.id.linearLayoutfullTitleDescripcion);

        monedaBaseTextView5 = findViewById(R.id.monedaBaseDescriptionTextView5);
        monedaActualTextView5 = findViewById(R.id.monedaActualDescriptionTextView5);
        estadosubastaTextView = findViewById(R.id.estadoDetalleDescriptionTextView5);
        lineaViewEstadosubastaTextView = findViewById(R.id.lineaViewEstadoDetalleDescriptionTextView5);

        titleTextView5.setText(element.getTitle());

        String precioBaseText5 = String.format("%,d", element.getBasePrice());
        precioBaseTextView5.setText(precioBaseText5);
        valorActualTextView5.setText("testt");
        descriptionTextView5.setText(element.getDescription());
        monedaBaseTextView5.setText(element.getCurrency());
        monedaActualTextView5.setText(element.getCurrency());

        valorActualOVendido5 = findViewById(R.id.valorActualOVendido5);
        precioBaseView5 = findViewById(R.id.precioBaseView5);
        historialPujasView5 = findViewById(R.id.historialPujasView5);

        cvEnvio = findViewById(R.id.cvEnvio);
        pendienteEnvio = findViewById(R.id.pendienteEnvio);
        enviadoEnvio = findViewById(R.id.enviadoEnvio);
        recibidoEnvio = findViewById(R.id.recibidoEnvio);


        linearLayoutfullTitleDescripcion.setOnClickListener(new View.OnClickListener() {
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
            historialPujasView5.setVisibility(View.INVISIBLE);
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
                valorActualOVendido5.setText("Precio Actual");
                valorActualTextView5.setTextColor(Color.parseColor("#FF669900"));
                break;
            case "Programmed":
                valorActualOVendido5.setVisibility(View.GONE);
                valorActualTextView5.setVisibility(View.GONE);
                monedaActualTextView5.setVisibility(View.GONE);
                //editarNumeroDeTexto5.setVisibility(View.GONE);
                //botonOfertar5.setVisibility(View.GONE);
                historialPujasView5.setVisibility(View.INVISIBLE);
                precioBaseTextView5.setTextColor(Color.parseColor("#FF669900"));
                precioBaseView5.setTextColor(Color.parseColor("#FF669900"));
                break;
            case "Ended":
                //editarNumeroDeTexto5.setVisibility(View.GONE);
                //botonOfertar5.setVisibility(View.GONE);
                valorActualOVendido5.setText("Vendido!");
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
                    Date fechaActual = new Date();
                    if(bids.get(bids.size() - 1).getUserId() == user.getId()) {

                        if(fechaActual.after(element.getEndTime())) {
                            estadosubastaTextView.setText("Ganada!\uD83D\uDE0D");
                            System.out.println("Shipping status: " + element.getShippingStatus());
                            cvEnvio.setVisibility(View.VISIBLE);
                            pendienteEnvio.setVisibility(View.VISIBLE);
                            descriptionTextView5.setMaxLines(3);
                            if (element.getShippingStatus().equals("Dispatched")){
                                enviadoEnvio.setVisibility(View.VISIBLE);
                            } else if (element.getShippingStatus().equals("Delivered")){
                                enviadoEnvio.setVisibility(View.VISIBLE);
                                recibidoEnvio.setVisibility(View.VISIBLE);
                            }
                        } else {
                            estadosubastaTextView.setText("Ganando!\uD83D\uDE0E");
                        }
                        estadosubastaTextView.setTextColor(Color.GREEN);
                        lineaViewEstadosubastaTextView.setBackgroundColor(Color.GREEN);
                    } else {
                        if(fechaActual.after(element.getEndTime())) {
                            estadosubastaTextView.setText("Perdida\uD83D\uDE2D");
                        } else {
                            estadosubastaTextView.setText("Perdiendo\uD83D\uDE27");
                        }
                        estadosubastaTextView.setTextColor(Color.RED);
                        lineaViewEstadosubastaTextView.setBackgroundColor(Color.RED);
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

    private void getUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);
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