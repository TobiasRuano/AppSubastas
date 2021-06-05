package com.trotos.appsubastas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;


public class MenuLogueado extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    View item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_logueado);

        getSupportActionBar().hide();


        drawerLayout = findViewById(R.id.drawerLogueado);
        navigationView = findViewById(R.id.navLogueado);
        toolbar = findViewById(R.id.toolbarLogueado);

        item = findViewById(R.id.usuarioLogueado);

        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuLogueado.this,MiUsuario.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_bottom,R.anim.slide_out_top);
            }
        });



        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);


        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setCheckedItem(R.id.homeLogueado);


    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeLogueado:
                break;
            case R.id.mpLogueado:
                Intent intent = new Intent(MenuLogueado.this, MediosPagoActivity.class);
                startActivity(intent);
                break;
            case R.id.msLogueado:
                break;
            case R.id.moLogueado:
                Intent intent2 = new Intent(MenuLogueado.this, MisObjetos.class);
                startActivity(intent2);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}