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

import com.trotos.appsubastas.Modelos.Item;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DescripcionMisObjetosActivity extends AppCompatActivity {

    TextView titleDescriptionTextView4;
    TextView precioBaseDescriptionTextView4;
    TextView valorActualDescriptionTextView4;
    TextView fullTitleDescriptionTextView4;
    TextView monedaBaseDescriptionTextView4;
    TextView monedaActualDescriptionTextView4;

    EditText editarNumeroDeTexto4;
    Button botonOfertar4;
    Button botonRegistrar4;
    Button aceptarPendienteBoton, rechazarPendienteBoton;

    TextView valorActualOVendido4;
    TextView precioBaseView4;
    TextView historialPujasView4;

    List<CarouselItem> list = new ArrayList<>();

    Item element;
    boolean estaRegistrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_mis_objetos);

        element = (Item) getIntent().getSerializableExtra("MisObjetos");
        System.out.println(element.getTitle());
        estaRegistrado = (Boolean) getIntent().getBooleanExtra("estadoLoggeado", false);
        configureUI();
        cargar();
        logIn();
        cambiarEstado();
    }

    private void cambiarEstado(){
        aceptarPendienteBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element.setStatus("Aceptado");
                //startActivity(new Intent(DestalleMisObjetosActivity.this, MisObjetos.class));
            }
        });

        rechazarPendienteBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                element.setStatus("Rechazado");
                //startActivity(new Intent(DestalleMisObjetosActivity.this, MisObjetos.class));
            }
        });
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
        titleDescriptionTextView4 = findViewById(R.id.titleDescriptionTextView4);
        precioBaseDescriptionTextView4 = findViewById(R.id.precioBaseDescriptionTextView4);
        valorActualDescriptionTextView4 = findViewById(R.id.valorActualDescriptionTextView4);
        fullTitleDescriptionTextView4 = findViewById(R.id.fullTitleDescriptionTextView4);
        monedaBaseDescriptionTextView4 = findViewById(R.id.monedaBaseDescriptionTextView4);
        monedaActualDescriptionTextView4 = findViewById(R.id.monedaActualDescriptionTextView4);

        aceptarPendienteBoton = findViewById(R.id.aceptarPendienteBoton);
        rechazarPendienteBoton = findViewById(R.id.rechazarPendienteBoton);

        titleDescriptionTextView4.setText(element.getDescription());
        //titleDescriptionTextView4.setTextColor(Color.parseColor(element.getColor()));

        String precioBaseText4 = String.format("%,d", element.getBasePrice());
        precioBaseDescriptionTextView4.setText(precioBaseText4);
        String valorActualText4 = String.format("%,d", element.getBasePrice()); // no deberia estar
        valorActualDescriptionTextView4.setText(valorActualText4);
        valorActualDescriptionTextView4.setTextColor(Color.GRAY);
        fullTitleDescriptionTextView4.setText(element.getDescription());
        monedaBaseDescriptionTextView4.setText("USD"); // harcodeado, se obtiene de subasta
        monedaActualDescriptionTextView4.setText("USD");

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

        String estado = element.getStatus();
        if(estado == null) {
            estado = "test";
        }

        switch (estado) {
            case "Auctioning":
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
            case "Aceptado":
                break;
            case "Pendiente":
                monedaBaseDescriptionTextView4.setVisibility(View.GONE);
                historialPujasView4.setVisibility(View.GONE);
                valorActualOVendido4.setVisibility(View.GONE);
                editarNumeroDeTexto4.setVisibility(View.GONE);
                monedaActualDescriptionTextView4.setVisibility(View.GONE);
                valorActualDescriptionTextView4.setVisibility(View.GONE);
                precioBaseDescriptionTextView4.setVisibility(View.GONE);
                precioBaseView4.setVisibility(View.GONE);
                botonOfertar4.setVisibility(View.GONE);
                aceptarPendienteBoton.setVisibility(View.VISIBLE);
                rechazarPendienteBoton.setVisibility(View.VISIBLE);
                break;
            case "Rechazado":
                break;
            default:
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
        new AlertDialog.Builder(DescripcionMisObjetosActivity.this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }
}