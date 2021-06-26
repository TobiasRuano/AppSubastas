package com.trotos.appsubastas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class HistorialPujasDescripcionActivity extends AppCompatActivity {

    String texto;
    TextView historialPujasDetalleDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pujas_descripcion);
        historialPujasDetalleDescriptionTextView = findViewById(R.id.historialPujasDetalleDescriptionTextView);

        Intent intent = getIntent();
        texto = intent.getStringExtra("historialPujasDetalle");
        historialPujasDetalleDescriptionTextView.setText(texto);
        historialPujasDetalleDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
    }
}