package com.trotos.appsubastas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AgregarObjeto extends AppCompatActivity {

    TextInputLayout artista;
    Button cargarObjeto;

    TextInputEditText nombreBox, artistaBox, nPlazaBox, descBox;

    ImageView imagenObjetos;

    FloatingActionButton subirFoto;

    RadioGroup radioGroup;
    RadioButton botonEstandar, botonObra;

    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_objeto);

        getSupportActionBar().hide();

        imagenObjetos = findViewById(R.id.imagenObjetos);
        subirFoto = findViewById(R.id.subirFotoObjeto);


        botonEstandar = findViewById(R.id.estandarBoton);
        botonObra = findViewById(R.id.obraBoton);

        radioGroup = findViewById(R.id.radioGroupObjetos);

        artista = findViewById(R.id.nombreArtistaTextoObjetoEstandar);
        artistaBox = findViewById(R.id.nombreArtistaObjetoEstandar);

        nombreBox = findViewById(R.id.nombreObjetoEstandar);
        nPlazaBox = findViewById(R.id.nPlazaObjetoEstandar);
        descBox = findViewById(R.id.descripcionObjetoEstandar);

        cargarObjeto = findViewById(R.id.botonGargarObjeto);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.estandarBoton:
                        botonEstandar.setTextColor(Color.WHITE);
                        botonObra.setTextColor(Color.RED);
                        artista.setVisibility(View.GONE);
                        artistaBox.setVisibility(View.GONE);
                        cargarObjeto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(checkEstadoCamposObjetos()){
                                    showAlert("Exito!", "Objeto Cargado de forma exitosa. Le avisaremos si es seleccionado");
                                }else{
                                    showAlert("Error al cargar el objeto", "Hubo un error al cargar el objeto. Por favor intenta de nuevo mas tarde");
                                }
                            }
                        });
                        break;
                    case R.id.obraBoton:
                        botonEstandar.setTextColor(Color.RED);
                        botonObra.setTextColor(Color.WHITE);
                        artista.setVisibility(View.VISIBLE);
                        artistaBox.setVisibility(View.VISIBLE);
                        cargarObjeto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(checkEstadoCamposObjetos()){
                                    showAlert("Exito!", "Objeto Cargado de forma exitosa. Le avisaremos si es seleccionado");
                                }else{
                                    showAlert("Error al cargar el objeto", "Hubo un error al cargar el objeto. Por favor intenta de nuevo mas tarde");
                                }

                            }
                        });
                        break;

                }
            }
        });

        subirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeleccionImagen();
            }
        });

    }

    private boolean checkEstadoCamposObjetos(){
        String nombre = nombreBox.getText().toString();
        String nArtista = artistaBox.getText().toString();
        String nPlaza = nPlazaBox.getText().toString();
        String descripcion = descBox.getText().toString();
        if (!botonEstandar.isChecked() && botonObra.isChecked()) {
            if (!nombre.isEmpty() && !nArtista.isEmpty() && !nPlaza.isEmpty() && !descripcion.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }else{
            if (!nombre.isEmpty() && !nPlaza.isEmpty() && !descripcion.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }
    }

    private void showAlert(String titulo, String mensaje) {
        new androidx.appcompat.app.AlertDialog.Builder(AgregarObjeto.this)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", null)
                .show();
    }

    private void SeleccionImagen() {

        final CharSequence[] items={"Camara", "Galeria", "Cancelar"};

        AlertDialog.Builder builder = new AlertDialog.Builder(AgregarObjeto.this);
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
                imagenObjetos.setImageBitmap(bmp);

            }else if (requestCode == SELECT_FILE){

                Uri selectImageUri = data.getData();
                imagenObjetos.setImageURI(selectImageUri);


            }

        }
    }


}