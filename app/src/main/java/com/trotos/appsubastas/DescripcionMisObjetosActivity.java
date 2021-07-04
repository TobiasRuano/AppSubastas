package com.trotos.appsubastas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.trotos.appsubastas.Modelos.Bid;
import com.trotos.appsubastas.Modelos.ItemCatalogo;
import com.trotos.appsubastas.Modelos.ResponseBids;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DescripcionMisObjetosActivity extends AppCompatActivity {

    TextView titleTextView4;
    TextView precioBaseTextView4;
    TextView valorActualTextView4;
    TextView descriptionTextView4;
    TextView monedaBaseTextView4;
    TextView monedaActualTextView4;
    TextView estadoItem;
    TextView precioBaseWR;
    TextView comisionWR;

    LinearLayout linearLayoutfullTitleDescripcion;

    CardView cvPrecioComision;

    //EditText editarNumeroDeTexto4;
    //Button botonOfertar4;
    Button botonRegistrar4;
    Button aceptarPendienteBoton, rechazarPendienteBoton;

    TextView valorActualOVendido4;
    TextView precioBaseView4;
    TextView historialPujasView4;

    List<CarouselItem> list = new ArrayList<>();
    List<Bid> bids = new ArrayList<>();

    ItemCatalogo element;
    boolean estaRegistrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_mis_objetos);

        element = (ItemCatalogo) getIntent().getSerializableExtra("MisObjetos");
        System.out.println(element.getTitle());
        estaRegistrado = (Boolean) getIntent().getBooleanExtra("estadoLoggeado", false);
        configureUI();
        cargar();
        getBids();
        logIn();
        cambiarEstado();
    }

    private void cambiarEstado(){
        aceptarPendienteBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element.setStatus("Accepted");
                actOnOffer();
            }
        });

        rechazarPendienteBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element.setStatus("Denied");
                actOnOffer();
                System.out.println("Algo anda bien");
            }
        });
    }

    private void actOnOffer() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);

        Call<ItemCatalogo> call = as.actOnOffer(element, "Bearer "+ token);

        call.enqueue(new Callback<ItemCatalogo>() {
            @Override
            public void onResponse(Call<ItemCatalogo> call, Response<ItemCatalogo> response) {
                if(response.isSuccessful()) {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Exito!", Toast.LENGTH_LONG);
                    toast1.show();
                    if(element.getStatus().equals("Accepted")){
                        element.setStatus("Pending Auction");
                    }
                    configureViewByStatus();
                }
            }

            @Override
            public void onFailure(Call<ItemCatalogo> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al intentar Aceptar o rechazar la oferta", Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(DescripcionMisObjetosActivity.this, MisObjetos.class);
        mainIntent.putExtra("item", element);
        setResult(RESULT_OK, mainIntent);
        finish();
    }

    private void logIn() {
        botonRegistrar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DescripcionMisObjetosActivity.this, IniciarSesionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void configureUI() {
        titleTextView4 = findViewById(R.id.titleDescriptionTextView4);
        precioBaseTextView4 = findViewById(R.id.precioBaseDescriptionTextView4);
        valorActualTextView4 = findViewById(R.id.valorActualDescriptionTextView4);
        descriptionTextView4 = findViewById(R.id.fullTitleDescriptionTextView4);
        estadoItem = findViewById(R.id.estadoDetalleDescriptionTextView5);
        linearLayoutfullTitleDescripcion = findViewById(R.id.linearLayoutfullTitleDescripcion);
        monedaBaseTextView4 = findViewById(R.id.monedaBaseDescriptionTextView4);
        monedaActualTextView4 = findViewById(R.id.monedaActualDescriptionTextView4);
        aceptarPendienteBoton = findViewById(R.id.aceptarPendienteBoton);
        rechazarPendienteBoton = findViewById(R.id.rechazarPendienteBoton);

        titleTextView4.setText(element.getTitle());

        valorActualTextView4.setTextColor(Color.GRAY);
        descriptionTextView4.setText(element.getDescription());
        monedaBaseTextView4.setText(element.getCurrency());
        monedaActualTextView4.setText(element.getCurrency());
        estadoItem.setText(element.getStatus());

        //editarNumeroDeTexto4 = findViewById(R.id.editarNumeroDeTexto4);
        //botonOfertar4 = findViewById(R.id.botonOfertar4);
        valorActualOVendido4 = findViewById(R.id.valorActualOVendido4);
        precioBaseView4 = findViewById(R.id.precioBaseView4);
        botonRegistrar4 = findViewById(R.id.botonRegistrar4);
        historialPujasView4 = findViewById(R.id.historialPujasView4);

        cvPrecioComision = findViewById(R.id.cvPrecioComision);

        precioBaseWR = findViewById(R.id.precioBaseWR);
        comisionWR = findViewById(R.id.comisionWR);



        //String valorActualText4 = String.format("%,d", element.getBasePrice()); // no deberia estar
        //valorActualOVendido4.setText(valorActualText4);
        String precioBaseText4 = String.format("%,d", element.getBasePrice());
        precioBaseView4.setText(precioBaseText4);


        String precioDeWR = String.format("%,d", element.getBasePrice());
        precioBaseWR.setText(precioDeWR);

        String comision = String.format("%,d", element.getCommission());
        comisionWR.setText(comision);

        linearLayoutfullTitleDescripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DetalleDeLaDescripcionActivity.class);
                intent.putExtra("valorDescription", descriptionTextView4.getText().toString());
                startActivity(intent);
            }
        });

        descriptionTextView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),DetalleDeLaDescripcionActivity.class);
                intent.putExtra("valorDescription", descriptionTextView4.getText().toString());
                startActivity(intent);
            }
        });

        if(!estaRegistrado){
            //editarNumeroDeTexto4.setVisibility(View.GONE);
            //botonOfertar4.setVisibility(View.GONE);
            valorActualOVendido4.setVisibility(View.GONE);
            monedaActualTextView4.setVisibility(View.GONE);
            valorActualTextView4.setVisibility(View.GONE);
            precioBaseTextView4.setVisibility(View.GONE);
            monedaBaseTextView4.setVisibility(View.GONE);
            precioBaseView4.setVisibility(View.GONE);
            historialPujasView4.setVisibility(View.GONE);
        }else {
            botonRegistrar4.setVisibility(View.GONE);
        }
        configureViewByStatus();
    }

    private void configureViewByStatus() {
        String estado = element.getStatus();
        if(estado == null) {
            estado = "Pending";
        }

        switch (estado) {
            case "Auctioning":
                valorActualOVendido4.setText("Valor Actual:");
                valorActualTextView4.setTextColor(Color.parseColor("#FF669900"));
                cvPrecioComision.setVisibility(View.GONE);
                break;
            case "Programmed":
                valorActualOVendido4.setVisibility(View.GONE);
                valorActualTextView4.setVisibility(View.GONE);
                monedaActualTextView4.setVisibility(View.GONE);
                //editarNumeroDeTexto4.setVisibility(View.GONE);
                //botonOfertar4.setVisibility(View.GONE);
                historialPujasView4.setVisibility(View.GONE);
                precioBaseTextView4.setTextColor(Color.parseColor("#FF669900"));
                precioBaseView4.setTextColor(Color.parseColor("#FF669900"));
                cvPrecioComision.setVisibility(View.GONE);
                break;
            case "Ended":
                //editarNumeroDeTexto4.setVisibility(View.GONE);
                //botonOfertar4.setVisibility(View.GONE);
                valorActualOVendido4.setText("Vendido:");
                valorActualTextView4.setTextColor(Color.parseColor("#FF669900"));
                cvPrecioComision.setVisibility(View.GONE);
                break;
            case "Waiting Response":
                //botonOfertar4.setVisibility(View.GONE);
                //editarNumeroDeTexto4.setVisibility(View.GONE);
                historialPujasView4.setVisibility(View.GONE);
                valorActualOVendido4.setVisibility(View.GONE);
                monedaActualTextView4.setVisibility(View.GONE);
                valorActualTextView4.setVisibility(View.GONE);
                precioBaseTextView4.setVisibility(View.GONE);
                precioBaseView4.setVisibility(View.GONE);
                monedaBaseTextView4.setVisibility(View.GONE);
                //comisionWR.setText(element.getCommission());
                System.out.println(element.getCommission());
                break;
            case "Pending Auction":
                historialPujasView4.setVisibility(View.GONE);
                //botonOfertar4.setVisibility(View.GONE);
                //editarNumeroDeTexto4.setVisibility(View.GONE);
                cvPrecioComision.setVisibility(View.GONE);
                break;
            case "Pending":
                monedaBaseTextView4.setVisibility(View.GONE);
                historialPujasView4.setVisibility(View.GONE);
                valorActualOVendido4.setVisibility(View.GONE);
                //editarNumeroDeTexto4.setVisibility(View.GONE);
                monedaActualTextView4.setVisibility(View.GONE);
                valorActualTextView4.setVisibility(View.GONE);
                precioBaseTextView4.setVisibility(View.GONE);
                precioBaseView4.setVisibility(View.GONE);
                //botonOfertar4.setVisibility(View.GONE);
                cvPrecioComision.setVisibility(View.GONE);
                break;
            case "Denied":
                aceptarPendienteBoton.setVisibility(View.GONE);
                rechazarPendienteBoton.setVisibility(View.GONE);
                monedaBaseTextView4.setVisibility(View.GONE);
                historialPujasView4.setVisibility(View.GONE);
                valorActualOVendido4.setVisibility(View.GONE);
                //editarNumeroDeTexto4.setVisibility(View.GONE);
                monedaActualTextView4.setVisibility(View.GONE);
                valorActualTextView4.setVisibility(View.GONE);
                precioBaseTextView4.setVisibility(View.GONE);
                precioBaseView4.setVisibility(View.GONE);
                //botonOfertar4.setVisibility(View.GONE);
                cvPrecioComision.setVisibility(View.GONE);
                break;
            default:
                break;
        }
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
                    if (responseBids != null) {
                        bids.addAll(responseBids.getData());
                        if(bids.size() > 0) {
                            int valorActual = bids.get(bids.size() - 1).getAmount();
                            valorActualOVendido4.setText(String.valueOf(valorActual));
                        }
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


    private void cargar() {
        ImageCarousel carousel = findViewById(R.id.carousel4);
        carousel.registerLifecycle(getLifecycle());
        list.add(
                new CarouselItem(
                        element.getUrlImage()
                )
        );
        carousel.setData(list);
    }

    private void showAlert(String titulo, String mensaje) {
        new AlertDialog.Builder(DescripcionMisObjetosActivity.this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }
}