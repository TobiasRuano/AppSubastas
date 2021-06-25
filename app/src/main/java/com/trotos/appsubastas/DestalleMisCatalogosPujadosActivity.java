package com.trotos.appsubastas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.trotos.appsubastas.Modelos.ItemCatalogo;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DestalleMisCatalogosPujadosActivity extends AppCompatActivity {

    TextView titleDescriptionTextView5;
    TextView precioBaseDescriptionTextView5;
    TextView valorActualDescriptionTextView5;
    TextView fullTitleDescriptionTextView5;
    TextView monedaBaseDescriptionTextView5;
    TextView monedaActualDescriptionTextView5;

    EditText editarNumeroDeTexto5;
    Button botonOfertar5;
    Button botonRegistrar5;

    TextView valorActualOVendido5;
    TextView precioBaseView5;
    TextView historialPujasView5;

    List<CarouselItem> list = new ArrayList<>();

    ItemCatalogo element;
    boolean estaRegistrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_mis_catalogos_pujados);

        element = (ItemCatalogo) getIntent().getSerializableExtra("MisSubastas");
        estaRegistrado = (Boolean) getIntent().getBooleanExtra("estadoLoggeado", false);
        configureUI();
        cargar();
        //verHistorialPujas();
        //ofertar();
        //logIn();
    }

    private void logIn() {
        botonRegistrar5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DestalleMisCatalogosPujadosActivity.this, IniciarSesionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void configureUI() {
        titleDescriptionTextView5 = findViewById(R.id.titleDescriptionTextView5);
        precioBaseDescriptionTextView5 = findViewById(R.id.precioBaseDescriptionTextView5);
        valorActualDescriptionTextView5 = findViewById(R.id.valorActualDescriptionTextView5);
        fullTitleDescriptionTextView5 = findViewById(R.id.fullTitleDescriptionTextView5);
        monedaBaseDescriptionTextView5 = findViewById(R.id.monedaBaseDescriptionTextView5);
        monedaActualDescriptionTextView5 = findViewById(R.id.monedaActualDescriptionTextView5);

        titleDescriptionTextView5.setText(element.getDescription());
        //titleDescriptionTextView5.setTextColor(Color.parseColor(element.getColor()));

        String precioBaseText5 = String.format("%,d", element.getBasePrice());
        precioBaseDescriptionTextView5.setText(precioBaseText5);
        String valorActualText5 = String.format("%,d", element.getBasePrice());
        valorActualDescriptionTextView5.setText(valorActualText5);
        valorActualDescriptionTextView5.setTextColor(Color.GRAY);
        fullTitleDescriptionTextView5.setText(element.getDescription());
        monedaBaseDescriptionTextView5.setText("USD");
        monedaActualDescriptionTextView5.setText("USD");

        //editarNumeroDeTexto5 = findViewById(R.id.editarNumeroDeTexto5);
        //botonOfertar5 = findViewById(R.id.botonOfertar5);
        valorActualOVendido5 = findViewById(R.id.valorActualOVendido5);
        precioBaseView5 = findViewById(R.id.precioBaseView5);
        //botonRegistrar5 = findViewById(R.id.botonRegistrar5);
        historialPujasView5 = findViewById(R.id.historialPujasView5);

        if(!estaRegistrado){
            //editarNumeroDeTexto5.setVisibility(View.GONE);
            //botonOfertar5.setVisibility(View.GONE);
            valorActualOVendido5.setVisibility(View.GONE);
            monedaActualDescriptionTextView5.setVisibility(View.GONE);
            valorActualDescriptionTextView5.setVisibility(View.GONE);
            precioBaseDescriptionTextView5.setVisibility(View.GONE);
            monedaBaseDescriptionTextView5.setVisibility(View.GONE);
            precioBaseView5.setVisibility(View.GONE);
            historialPujasView5.setVisibility(View.GONE);
        }else {
            //botonRegistrar5.setVisibility(View.GONE);
        }

        String estado = element.getStatus();

        if(estado == null) {
            estado = "En curso"; // para que no crashee
        }
        switch (estado) {
            case "En Curso":
                valorActualOVendido5.setText("Valor Actual:");
                valorActualDescriptionTextView5.setTextColor(Color.parseColor("#FF669900"));
                break;
            case "Programada":
                valorActualOVendido5.setVisibility(View.GONE);
                valorActualDescriptionTextView5.setVisibility(View.GONE);
                monedaActualDescriptionTextView5.setVisibility(View.GONE);
                //editarNumeroDeTexto5.setVisibility(View.GONE);
                //botonOfertar5.setVisibility(View.GONE);
                historialPujasView5.setVisibility(View.GONE);
                precioBaseDescriptionTextView5.setTextSize(30);
                precioBaseDescriptionTextView5.setTextColor(Color.parseColor("#FF669900"));
                precioBaseView5.setTextSize(30);
                precioBaseView5.setTextColor(Color.parseColor("#FF669900"));
                break;
            case "Finalizada":
                //editarNumeroDeTexto5.setVisibility(View.GONE);
                //botonOfertar5.setVisibility(View.GONE);
                valorActualOVendido5.setText("Vendido:");
                valorActualDescriptionTextView5.setTextColor(Color.parseColor("#FF669900"));
                break;
        }
    }


    private void cargar() {
        ImageCarousel carousel = findViewById(R.id.carousel5);
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
        new AlertDialog.Builder(DestalleMisCatalogosPujadosActivity.this)
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

    private void verHistorialPujas() {
        historialPujasView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HARDCODEADO
                String historialPujasTexto = "\nRodríguezxxx ofertó $2.000 \n\nGómezxxx ofertó $3.000 \n\nPerezxxx ofertó $4.000 \n\nGonzalesxxx ofertó $5.000 \n\nFernándezxxx ofertó $6.000 \n\nLópezxxx ofertó $7.000 \n\nDíazxxx ofertó $8.000 \n\nMartínezxxx ofertó $9.000 \n\nTu has ofertado $10.000 \n\n ";
                showAlert("Historial de ofertas",historialPujasTexto);
            }
        });
    }
*/

}