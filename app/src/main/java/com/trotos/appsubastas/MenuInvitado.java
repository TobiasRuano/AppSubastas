package com.trotos.appsubastas;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

public class MenuInvitado extends AppCompatActivity {

    View item;
    Dialog dialog;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_invitado);

        drawerLayout = findViewById(R.id.drawerInvitado);
        navigationView = findViewById(R.id.navinvitado);
        toolbar = findViewById(R.id.toolbarInvitado);

        item = findViewById(R.id.usuarioInvitado);

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(MenuInvitado.this);
                dialog.setContentView(R.layout.pop_up_iniciar_sesion_registrarse);

                final Button iniciarSesion = dialog.findViewById(R.id.iniciarSesionPopup);
                final Button registrarse = dialog.findViewById(R.id.registrarsePopup);



                iniciarSesion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MenuInvitado.this,IniciarSesionActivity.class);
                        startActivity(intent);
                    }
                });

                registrarse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MenuInvitado.this,CrearUsuarioActivity.class);
                        startActivity(intent);
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();





            }
        });


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


}