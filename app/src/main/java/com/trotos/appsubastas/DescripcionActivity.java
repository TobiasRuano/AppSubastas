package com.trotos.appsubastas;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.*;


public class DescripcionActivity extends AppCompatActivity {

    TextView titleDescriptionTextView3;
    TextView precioBaseDescriptionTextView3;
    TextView valorActualDescriptionTextView3;
    TextView fullTitleDescriptionTextView3;

    EditText editarNumeroDeTexto;
    Button botonOfertar;
    Button botonRegistrar;

    TextView valorActualOVendido;
    TextView precioBaseView;
    TextView historialPujasView;

    List<CarouselItem> list = new ArrayList<>();

    //HARDCODEADO
    boolean estaRegistrado = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);


        itemCatalogo element = (itemCatalogo) getIntent().getSerializableExtra("Catalogos");
        titleDescriptionTextView3 = findViewById(R.id.titleDescriptionTextView3);
        precioBaseDescriptionTextView3 = findViewById(R.id.precioBaseDescriptionTextView3);
        valorActualDescriptionTextView3 = findViewById(R.id.valorActualDescriptionTextView3);
        fullTitleDescriptionTextView3 = findViewById(R.id.fullTitleDescriptionTextView3);

        titleDescriptionTextView3.setText(element.getDescripcion());
        titleDescriptionTextView3.setTextColor(Color.parseColor(element.getColor()));
        precioBaseDescriptionTextView3.setText(element.getPrecioBase());
        valorActualDescriptionTextView3.setText(element.getValorActual());
        valorActualDescriptionTextView3.setTextColor(Color.GRAY);
        fullTitleDescriptionTextView3.setText(element.getDescripcionCompleta());


        editarNumeroDeTexto = findViewById(R.id.editarNumeroDeTexto);
        botonOfertar = findViewById(R.id.botonOfertar);
        valorActualOVendido = findViewById(R.id.valorActualOVendido);
        precioBaseView = findViewById(R.id.precioBaseView);
        botonRegistrar = findViewById(R.id.botonRegistrar);
        historialPujasView = findViewById(R.id.historialPujasView);


        //HARDCODEADO
        if(estaRegistrado == false){
            editarNumeroDeTexto.setVisibility(View.GONE);
            botonOfertar.setVisibility(View.GONE);
            valorActualOVendido.setVisibility(View.GONE);
            valorActualDescriptionTextView3.setVisibility(View.GONE);
            precioBaseDescriptionTextView3.setVisibility(View.GONE);
            precioBaseView.setVisibility(View.GONE);
            historialPujasView.setVisibility(View.GONE);
        }else {
            botonRegistrar.setVisibility(View.GONE);
        }

        //En Curso
        if(element.getEstado().equals("En Curso")){
            valorActualOVendido.setText("Valor Actual:");
            valorActualDescriptionTextView3.setTextColor(Color.parseColor("#FF669900"));
        }
        //Programada
        if(element.getEstado().equals("Programada")){
            valorActualOVendido.setVisibility(View.GONE);
            valorActualDescriptionTextView3.setVisibility(View.GONE);
            editarNumeroDeTexto.setVisibility(View.GONE);
            botonOfertar.setVisibility(View.GONE);
            historialPujasView.setVisibility(View.GONE);
            precioBaseDescriptionTextView3.setTextSize(30);
            precioBaseDescriptionTextView3.setTextColor(Color.parseColor("#FF669900"));

            precioBaseView.setTextSize(30);
            precioBaseView.setTextColor(Color.parseColor("#FF669900"));

        }
        //Finalizada
        if(element.getEstado().equals("Finalizada")){
            editarNumeroDeTexto.setVisibility(View.GONE);
            botonOfertar.setVisibility(View.GONE);
            valorActualOVendido.setText("Vendido:");
            valorActualDescriptionTextView3.setTextColor(Color.parseColor("#FF669900"));
        }

        cargar();

        verHistorialPujas();

        ofertar();

    }


    private void cargar() {

        // Java
        ImageCarousel carousel = findViewById(R.id.carousel);

// Register lifecycle. For activity this will be lifecycle/getLifecycle() and for fragments it will be viewLifecycleOwner/getViewLifecycleOwner().
        carousel.registerLifecycle(getLifecycle());

        // Image URL with caption
        list.add(
                new CarouselItem(
                        "https://www.uade.edu.ar/media/pfqfuh4i/monserrat.jpeg?center=0.70307819516856551,0.54415877733959017&mode=crop&width=1240&height=910&rnd=132386837151700000",
                        "Edificio UADE"
                )
        );


// Image URL with caption
        list.add(
                new CarouselItem(
                        "https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080",
                        "FOTO"
                )
        );


// Image URL with header
        Map<String, String> headers = new HashMap<>();
        headers.put("header_key", "header_value");

        list.add(
                new CarouselItem(
                        "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080",
                        headers
                )
        );

        // Image URL with caption
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

                //HARDCODEADO
                Integer valorPrecioActual = 10000;
                Integer valorPrecioBase = 2000;
                boolean hayError = false;
                String categoria = "oro";
                //HARDCODEADO

                Integer valorPuja = 0;
                String texto = editarNumeroDeTexto.getText().toString();
                if(texto.equals("") == false){
                    valorPuja = Integer.parseInt(editarNumeroDeTexto.getText().toString());
                }


                if(valorPuja == 0){
                    showAlert("Monto vacio","Debe ingresar un Monto para poder Ofertar.");
                }
                else if(valorPuja > (valorPrecioActual * 1.2) && (categoria.equals("oro") || categoria.equals("platino")) ){
                    showAlert("Monto inválido","La oferta no puede exceder el 20 % de la última oferta realizada.");
                }
                else if(valorPuja <= (valorPrecioBase * 0.01 + valorPrecioActual)){
                    showAlert("Monto inválido","La oferta debe ser 1 % mayor al valor base del bien.");
                }
                else if(valorPuja <= valorPrecioActual){
                    showAlert("Monto inválido","La oferta no puede ser menor o igual a la última oferta realizada.");
                }
                else if(hayError == true){
                    showAlert("Error al realizar la oferta","Hubo un problema con la realización de la oferta. Por favor intenta mas tarde.");
                }
                else{
                    showAlert("Éxito!","Oferta realizada correctamente");
                }


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