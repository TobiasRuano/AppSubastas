package com.trotos.appsubastas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Inicio extends AppCompatActivity {


    Button iniciarSesion;
    Button registrarse;
    Button ingresarComoInvitado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        configureUI();

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, IniciarSesionActivity.class);
                startActivity(intent);
            }
        });

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, CrearUsuarioActivity.class);
                startActivity(intent);
            }
        });

        ingresarComoInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, MenuInvitado.class);
                startActivity(intent);
            }
        });

    }





    private void configureUI() {
        iniciarSesion = (Button) findViewById(R.id.iniciarSesion);
        registrarse = (Button) findViewById(R.id.registrarse);
        ingresarComoInvitado = (Button) findViewById(R.id.ingresarComoInvitado);
    }

}


