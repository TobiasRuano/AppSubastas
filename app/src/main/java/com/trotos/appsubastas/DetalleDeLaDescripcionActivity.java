package com.trotos.appsubastas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class DetalleDeLaDescripcionActivity extends AppCompatActivity {

    String texto;
    TextView fullCompleteTitleDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_de_la_descripcion);
        fullCompleteTitleDescriptionTextView = findViewById(R.id.fullCompleteTitleDescriptionTextView);

        Intent intent = getIntent();
        texto = intent.getStringExtra("fullTitle");
        fullCompleteTitleDescriptionTextView.setText(texto);
        fullCompleteTitleDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
    }
}