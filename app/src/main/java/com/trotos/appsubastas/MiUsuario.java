package com.trotos.appsubastas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MiUsuario extends AppCompatActivity {

    ImageView flecha;
    ImageView fotoPerfil;
    FloatingActionButton subirFoto;
    Button editarPerfilBoton;

    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_usuario);

        flecha = findViewById(R.id.flechaAbajoMiUsuario);
        subirFoto = findViewById(R.id.subirFoto);
        editarPerfilBoton = findViewById(R.id.editarPerfilBoton);


        flecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MiUsuario.this,MenuLogueado.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_bottom);
            }
        });


        subirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeleccionImagen();
            }
        });


        editarPerfilBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MiUsuario.this,EditarPerfil.class);
                startActivity(intent);
            }
        });


    }

    private void SeleccionImagen() {

        final CharSequence[] items={"Camara", "Galeria", "Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MiUsuario.this);
        builder.setTitle("Agregar Imagen");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(items[i].equals("Camara")){

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CAMERA);

                }else if(items[i].equals("Galeria")){

                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent,"Select File"),SELECT_FILE);

                }else if (items[i].equals("Cancelar")){

                    dialog.dismiss();
                }

            }
        });
        builder.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode == Activity.RESULT_OK){

            if (requestCode == REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                fotoPerfil.setImageBitmap(bmp);

            }else if (requestCode == SELECT_FILE){

                Uri selectImageUri = data.getData();
                fotoPerfil.setImageURI(selectImageUri);


            }

        }
    }


}