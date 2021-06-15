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

import java.util.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DescripcionActivity extends AppCompatActivity {

    TextView titleDescriptionTextView3;
    TextView precioBaseDescriptionTextView3;
    TextView valorActualDescriptionTextView3;
    TextView fullTitleDescriptionTextView3;
    TextView monedaBaseDescriptionTextView3;
    TextView monedaActualDescriptionTextView3;

    EditText editarNumeroDeTexto;
    Button botonOfertar;
    Button botonRegistrar;

    TextView valorActualOVendido;
    TextView precioBaseView;
    TextView historialPujasView;

    List<CarouselItem> list = new ArrayList<>();

    ItemCatalogo element;
    boolean estaRegistrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);

        element = (ItemCatalogo) getIntent().getSerializableExtra("Catalogos");
        estaRegistrado = (Boolean) getIntent().getBooleanExtra("estadoLoggeado", false);
        configureUI();
        cargar();
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
        titleDescriptionTextView3 = findViewById(R.id.titleDescriptionTextView3);
        precioBaseDescriptionTextView3 = findViewById(R.id.precioBaseDescriptionTextView3);
        valorActualDescriptionTextView3 = findViewById(R.id.valorActualDescriptionTextView3);
        fullTitleDescriptionTextView3 = findViewById(R.id.fullTitleDescriptionTextView3);
        monedaBaseDescriptionTextView3 = findViewById(R.id.monedaBaseDescriptionTextView3);
        monedaActualDescriptionTextView3 = findViewById(R.id.monedaActualDescriptionTextView3);

        titleDescriptionTextView3.setText(element.getDescripcion());
        titleDescriptionTextView3.setTextColor(Color.parseColor(element.getColor()));

        String precioBaseText = String.format("%,d", element.getPrecioBase());
        precioBaseDescriptionTextView3.setText(precioBaseText);
        String valorActualText = String.format("%,d", element.getValorActual());
        valorActualDescriptionTextView3.setText(valorActualText);
        valorActualDescriptionTextView3.setTextColor(Color.GRAY);
        fullTitleDescriptionTextView3.setText(element.getDescripcionCompleta());
        monedaBaseDescriptionTextView3.setText(element.getMoneda());
        monedaActualDescriptionTextView3.setText(element.getMoneda());

        editarNumeroDeTexto = findViewById(R.id.editarNumeroDeTexto);
        botonOfertar = findViewById(R.id.botonOfertar);
        valorActualOVendido = findViewById(R.id.valorActualOVendido);
        precioBaseView = findViewById(R.id.precioBaseView);
        botonRegistrar = findViewById(R.id.botonRegistrar);
        historialPujasView = findViewById(R.id.historialPujasView);

        if(!estaRegistrado){
            editarNumeroDeTexto.setVisibility(View.GONE);
            botonOfertar.setVisibility(View.GONE);
            valorActualOVendido.setVisibility(View.GONE);
            monedaActualDescriptionTextView3.setVisibility(View.GONE);
            valorActualDescriptionTextView3.setVisibility(View.GONE);
            precioBaseDescriptionTextView3.setVisibility(View.GONE);
            monedaBaseDescriptionTextView3.setVisibility(View.GONE);
            precioBaseView.setVisibility(View.GONE);
            historialPujasView.setVisibility(View.GONE);
        }else {
            botonRegistrar.setVisibility(View.GONE);
        }

        String estado = element.getEstado();

        switch (estado) {
            case "En Curso":
                valorActualOVendido.setText("Valor Actual:");
                valorActualDescriptionTextView3.setTextColor(Color.parseColor("#FF669900"));
                break;
            case "Programada":
                valorActualOVendido.setVisibility(View.GONE);
                valorActualDescriptionTextView3.setVisibility(View.GONE);
                monedaActualDescriptionTextView3.setVisibility(View.GONE);
                editarNumeroDeTexto.setVisibility(View.GONE);
                botonOfertar.setVisibility(View.GONE);
                historialPujasView.setVisibility(View.GONE);
                precioBaseDescriptionTextView3.setTextSize(30);
                precioBaseDescriptionTextView3.setTextColor(Color.parseColor("#FF669900"));
                precioBaseView.setTextSize(30);
                precioBaseView.setTextColor(Color.parseColor("#FF669900"));
                break;
            case "Finalizada":
                editarNumeroDeTexto.setVisibility(View.GONE);
                botonOfertar.setVisibility(View.GONE);
                valorActualOVendido.setText("Vendido:");
                valorActualDescriptionTextView3.setTextColor(Color.parseColor("#FF669900"));
                break;
        }
    }


    private void cargar() {
        ImageCarousel carousel = findViewById(R.id.carousel);
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

                int valorPrecioActual = element.getValorActual();
                int valorPrecioBase = element.getPrecioBase();
                boolean hayError = false;

                String categoria = getIntent().getStringExtra("categoria");
                if (categoria == null) {
                    categoria = "oro";
                }

                int valorPuja = 0;
                String texto = editarNumeroDeTexto.getText().toString();
                if(!texto.equals("")){
                    valorPuja = Integer.parseInt(editarNumeroDeTexto.getText().toString());
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
        historialPujasView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HARDCODEADO
                String historialPujasTexto = "\nRodríguezxxx ofertó $2.000 \n\nGómezxxx ofertó $3.000 \n\nPerezxxx ofertó $4.000 \n\nGonzalesxxx ofertó $5.000 \n\nFernándezxxx ofertó $6.000 \n\nLópezxxx ofertó $7.000 \n\nDíazxxx ofertó $8.000 \n\nMartínezxxx ofertó $9.000 \n\nTu has ofertado $10.000 \n\n ";
                showAlert("Historial de ofertas",historialPujasTexto);
            }
        });
    }


}