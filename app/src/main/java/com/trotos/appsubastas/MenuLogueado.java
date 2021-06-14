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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;


public class MenuLogueado extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_logueado);

        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.homeLogueado);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeLogueado:
                        break;
                    case R.id.mpLogueado:
                        startActivity(new Intent(MenuLogueado.this, MediosPagoActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.msLogueado:
                        break;
                    case R.id.moLogueado:
                        startActivity(new Intent(MenuLogueado.this, MisObjetos.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.usuarioLogueado:
                        startActivity(new Intent(MenuLogueado.this, MiUsuario.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });


    }

}