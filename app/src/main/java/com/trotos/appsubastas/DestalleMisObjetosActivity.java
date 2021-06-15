package com.trotos.appsubastas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.trotos.appsubastas.Modelos.ItemCatalogo;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DestalleMisObjetosActivity extends AppCompatActivity {

    TextView titleDescriptionTextView4;
    TextView precioBaseDescriptionTextView4;
    TextView valorActualDescriptionTextView4;
    TextView fullTitleDescriptionTextView4;
    TextView monedaBaseDescriptionTextView4;
    TextView monedaActualDescriptionTextView4;

    EditText editarNumeroDeTexto4;
    Button botonOfertar4;
    Button botonRegistrar4;

    TextView valorActualOVendido4;
    TextView precioBaseView4;
    TextView historialPujasView4;

    List<CarouselItem> list = new ArrayList<>();

    ItemCatalogo element;
    boolean estaRegistrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_mis_objetos);

        element = (ItemCatalogo) getIntent().getSerializableExtra("MisObjetos");
        estaRegistrado = (Boolean) getIntent().getBooleanExtra("estadoLoggeado", false);
        configureUI();
        cargar();
        verHistorialPujas();
        ofertar();
        logIn();
    }

    private void logIn() {
        botonRegistrar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DestalleMisObjetosActivity.this, IniciarSesionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void configureUI() {
        titleDescriptionTextView4 = findViewById(R.id.titleDescriptionTextView4);
        precioBaseDescriptionTextView4 = findViewById(R.id.precioBaseDescriptionTextView4);
        valorActualDescriptionTextView4 = findViewById(R.id.valorActualDescriptionTextView4);
        fullTitleDescriptionTextView4 = findViewById(R.id.fullTitleDescriptionTextView4);
        monedaBaseDescriptionTextView4 = findViewById(R.id.monedaBaseDescriptionTextView4);
        monedaActualDescriptionTextView4 = findViewById(R.id.monedaActualDescriptionTextView4);

        titleDescriptionTextView4.setText(element.getDescripcion());
        titleDescriptionTextView4.setTextColor(Color.parseColor(element.getColor()));

        String precioBaseText4 = String.format("%,d", element.getPrecioBase());
        precioBaseDescriptionTextView4.setText(precioBaseText4);
        String valorActualText4 = String.format("%,d", element.getValorActual());
        valorActualDescriptionTextView4.setText(valorActualText4);
        valorActualDescriptionTextView4.setTextColor(Color.GRAY);
        fullTitleDescriptionTextView4.setText(element.getDescripcionCompleta());
        monedaBaseDescriptionTextView4.setText(element.getMoneda());
        monedaActualDescriptionTextView4.setText(element.getMoneda());

        editarNumeroDeTexto4 = findViewById(R.id.editarNumeroDeTexto4);
        botonOfertar4 = findViewById(R.id.botonOfertar4);
        valorActualOVendido4 = findViewById(R.id.valorActualOVendido4);
        precioBaseView4 = findViewById(R.id.precioBaseView4);
        botonRegistrar4 = findViewById(R.id.botonRegistrar4);
        historialPujasView4 = findViewById(R.id.historialPujasView4);

        if(!estaRegistrado){
            editarNumeroDeTexto4.setVisibility(View.GONE);
            botonOfertar4.setVisibility(View.GONE);
            valorActualOVendido4.setVisibility(View.GONE);
            monedaActualDescriptionTextView4.setVisibility(View.GONE);
            valorActualDescriptionTextView4.setVisibility(View.GONE);
            precioBaseDescriptionTextView4.setVisibility(View.GONE);
            monedaBaseDescriptionTextView4.setVisibility(View.GONE);
            precioBaseView4.setVisibility(View.GONE);
            historialPujasView4.setVisibility(View.GONE);
        }else {
            botonRegistrar4.setVisibility(View.GONE);
        }

        String estado = element.getEstado();

        switch (estado) {
            case "En Curso":
                valorActualOVendido4.setText("Valor Actual:");
                valorActualDescriptionTextView4.setTextColor(Color.parseColor("#FF669900"));
                break;
            case "Programada":
                valorActualOVendido4.setVisibility(View.GONE);
                valorActualDescriptionTextView4.setVisibility(View.GONE);
                monedaActualDescriptionTextView4.setVisibility(View.GONE);
                editarNumeroDeTexto4.setVisibility(View.GONE);
                botonOfertar4.setVisibility(View.GONE);
                historialPujasView4.setVisibility(View.GONE);
                precioBaseDescriptionTextView4.setTextSize(30);
                precioBaseDescriptionTextView4.setTextColor(Color.parseColor("#FF669900"));
                precioBaseView4.setTextSize(30);
                precioBaseView4.setTextColor(Color.parseColor("#FF669900"));
                break;
            case "Finalizada":
                editarNumeroDeTexto4.setVisibility(View.GONE);
                botonOfertar4.setVisibility(View.GONE);
                valorActualOVendido4.setText("Vendido:");
                valorActualDescriptionTextView4.setTextColor(Color.parseColor("#FF669900"));
                break;
        }
    }


    private void cargar() {
        ImageCarousel carousel = findViewById(R.id.carousel4);
        carousel.registerLifecycle(getLifecycle());

        list.add(
                new CarouselItem(
                        "https://www.uade.edu.ar/media/pfqfuh4i/monserrat.jpeg?center=0.70307819516856551,0.54415877733959017&mode=crop&width=1240&height=910&rnd=132386837151700000",
                        "Edificio UADE"
                )
        );
        list.add(
                new CarouselItem(
                        "https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080",
                        "FOTO"
                )
        );
        Map<String, String> headers = new HashMap<>();
        headers.put("header_key", "header_value");
        list.add(
                new CarouselItem(
                        "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080",
                        headers
                )
        );
        list.add(
                new CarouselItem(
                        "https://resizer.iproimg.com/unsafe/880x495/filters:format(webp)/https://assets.iprofesional.com/assets/jpg/2016/01/427212.jpg?7.1.0",
                        "Pinamar UADE"
                )
        );
        carousel.setData(list);
    }

    private void showAlert(String titulo, String mensaje) {
        new AlertDialog.Builder(DestalleMisObjetosActivity.this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }


    private void ofertar() {
        botonOfertar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int valorPrecioActual = element.getValorActual();
                int valorPrecioBase = element.getPrecioBase();
                boolean hayError = false;

                String categoria = getIntent().getStringExtra("categoria");
                if (categoria == null) {
                    categoria = "oro";
                }

                int valorPuja = 0;
                String texto = editarNumeroDeTexto4.getText().toString();
                if(!texto.equals("")){
                    valorPuja = Integer.parseInt(editarNumeroDeTexto4.getText().toString());
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

    private void verHistorialPujas() {
        historialPujasView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HARDCODEADO
                String historialPujasTexto = "\nRodríguezxxx ofertó $2.000 \n\nGómezxxx ofertó $3.000 \n\nPerezxxx ofertó $4.000 \n\nGonzalesxxx ofertó $5.000 \n\nFernándezxxx ofertó $6.000 \n\nLópezxxx ofertó $7.000 \n\nDíazxxx ofertó $8.000 \n\nMartínezxxx ofertó $9.000 \n\nTu has ofertado $10.000 \n\n ";
                showAlert("Historial de ofertas",historialPujasTexto);
            }
        });
    }


}