package com.trotos.appsubastas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TerminosyCondiciones extends AppCompatActivity {

    TextView tyc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminosy_condiciones);

        tyc = findViewById(R.id.tyc);

        tyc.setMovementMethod(new ScrollingMovementMethod());

    }
}