package com.trotos.appsubastas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.trotos.appsubastas.Modelos.ItemCatalogo;
import com.trotos.appsubastas.Modelos.Subasta;

import java.util.ArrayList;
import java.util.List;

public class CatalogoActivity<animFadeIn> extends AppCompatActivity {

    TextView nameDescriptionTextView;
    TextView stateDescriptionTextView;
    TextView categoryDescriptionTextView;
    RecyclerView listRecyclerView2;

    Animation animFadeIn;
    boolean banderaAnimation = false;
    boolean estaRegistrado;
    ViewGroup.LayoutParams params;
    LinearLayout linearLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        Subasta element = (Subasta) getIntent().getSerializableExtra("Subastas");
        if (getIntent().getSerializableExtra("estadoLoggeado") != null) {
            estaRegistrado = (Boolean) getIntent().getSerializableExtra("estadoLoggeado");
        } else {
            estaRegistrado = false;
        }
        nameDescriptionTextView = findViewById(R.id.nameDescriptionTextView);
        stateDescriptionTextView = findViewById(R.id.stateDescriptionTextView);
        categoryDescriptionTextView = findViewById(R.id.categoryDescriptionTextView);

        nameDescriptionTextView.setText(element.getName());
        nameDescriptionTextView.setTextColor(Color.parseColor(element.getColor()));

        stateDescriptionTextView.setText(element.getState());

        categoryDescriptionTextView.setText(element.getCategory());
        categoryDescriptionTextView.setTextColor(Color.GRAY);

        init();

    }

    List<ItemCatalogo> catalogos;


    public void init(){

        catalogos = new ArrayList<>();


        //HARDCODEADO
        catalogos.add(new ItemCatalogo("123456","En Curso","Rolex",8000,2100000,"#775447","Lorem ipsum dolor sit amet consectetur adipiscing elit aptent platea facilisi tortor nunc imperdiet.","Breve descripcion del item", "ARS"));
        catalogos.add(new ItemCatalogo("123456","En Curso","Casio",2000,10000,"#775447","Lorem ipsum dolor sit amet consectetur adipiscing elit aptent platea facilisi tortor nunc imperdiet.2","Breve descripcion del item", "ARS"));
        catalogos.add(new ItemCatalogo("123456","En Curso","Paddle Watch",200,7000,"#775447","Lorem ipsum dolor sit amet consectetur adipiscing elit aptent platea facilisi tortor nunc imperdiet.3","Breve descripcion del item", "USD"));
        catalogos.add(new ItemCatalogo("123456","Programada","Rolex",8000,2100000,"#775447","Lorem ipsum dolor sit amet consectetur adipiscing elit aptent platea facilisi tortor nunc imperdiet.","Breve descripcion del item", "ARS"));
        catalogos.add(new ItemCatalogo("123456","Programada","Casio",2000,10000,"#775447","Lorem ipsum dolor sit amet consectetur adipiscing elit aptent platea facilisi tortor nunc imperdiet.2","Breve descripcion del item", "ARS"));
        catalogos.add(new ItemCatalogo("123456","Programada","Paddle Watch",200,7000,"#775447","Lorem ipsum dolor sit amet consectetur adipiscing elit aptent platea facilisi tortor nunc imperdiet.3","Breve descripcion del item", "USD"));
        catalogos.add(new ItemCatalogo("123456","Finalizada","Rolex",8000,2100000,"#775447","Lorem ipsum dolor sit amet consectetur adipiscing elit aptent platea facilisi tortor nunc imperdiet.","Breve descripcion del item", "ARS"));
        catalogos.add(new ItemCatalogo("123456","Finalizada","Casio",2000,10000,"#775447","Lorem ipsum dolor sit amet consectetur adipiscing elit aptent platea facilisi tortor nunc imperdiet.2","Breve descripcion del item", "ARS"));
        catalogos.add(new ItemCatalogo("123456","Finalizada","Paddle Watch",200,7000,"#775447","Lorem ipsum dolor sit amet consectetur adipiscing elit aptent platea facilisi tortor nunc imperdiet.3","Breve descripcion del item", "USD"));

        MyAdapterCatalogo myAdapterCatalogo = new MyAdapterCatalogo(catalogos, estaRegistrado, this, new MyAdapterCatalogo.OnItemClickListener() {
            @Override
            public void onItemClick(ItemCatalogo item) {
                moveToDescription(item);
            }
        });

        RecyclerView recyclerView2 = findViewById(R.id.listRecyclerView2);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(  this    ));
        recyclerView2.setAdapter(myAdapterCatalogo);

        listRecyclerView2  = findViewById(R.id.listRecyclerView2);

        nameDescriptionTextView = findViewById(R.id.nameDescriptionTextView);
        stateDescriptionTextView = findViewById(R.id.stateDescriptionTextView);
        categoryDescriptionTextView = findViewById(R.id.categoryDescriptionTextView);

        linearLayout1 = findViewById(R.id.linearLayout1);

        //ANIMACION VISTA
        animFadeIn=AnimationUtils.loadAnimation(this, R.anim.fadeout);
            listRecyclerView2.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if(banderaAnimation == false) {
                        banderaAnimation = true;
                        linearLayout1.startAnimation(animFadeIn);
                        linearLayout1.setVisibility(View.VISIBLE);
                                        params =  linearLayout1.getLayoutParams();
                                        // Changes the height and width to the specified *pixels*
                                        params.height = 320;
                                        linearLayout1.setLayoutParams(params);
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        linearLayout1.clearAnimation();
                                        nameDescriptionTextView.setTextSize(26);
                                        stateDescriptionTextView.setTextSize(15);
                                        categoryDescriptionTextView.setTextSize(12);
                                    }
                                }, 400);
                    }
                    return false;
                }
            });

    }

    private void moveToDescription(ItemCatalogo item) {
        Intent intent = new Intent(this,   com.trotos.appsubastas.DescripcionActivity.class);
        intent.putExtra("Catalogos",item);
        intent.putExtra("estadoLoggeado", estaRegistrado);
        startActivity(intent);
    }



}