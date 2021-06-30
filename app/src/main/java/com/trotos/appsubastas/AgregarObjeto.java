package com.trotos.appsubastas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.trotos.appsubastas.Modelos.Item;
import com.trotos.appsubastas.Modelos.LoginInformation;
import com.trotos.appsubastas.Modelos.MPTarjeta;
import com.trotos.appsubastas.Modelos.ResponseItemsPropuestos;
import com.trotos.appsubastas.Modelos.ResponseLogIn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AgregarObjeto extends AppCompatActivity {

    TextInputLayout artista;
    Button cargarObjeto;
    TextInputEditText nombreBox, artistaBox, nPlazaBox, descBox;
    ImageView imagenObjetos;
    FloatingActionButton subirFoto;
    RadioGroup radioGroup;
    RadioButton botonEstandar, botonObra;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    TextView labelNombreArtistaTextoObjetoEstandar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_objeto);

        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.hide();
        }

        imagenObjetos = findViewById(R.id.imagenObjetos);
        subirFoto = findViewById(R.id.subirFotoObjeto);
        botonEstandar = findViewById(R.id.estandarBoton);
        botonObra = findViewById(R.id.obraBoton);
        radioGroup = findViewById(R.id.radioGroupObjetos);
        artista = findViewById(R.id.nombreArtistaTextoObjetoEstandar);
        artista.setVisibility(View.GONE);
        artistaBox = findViewById(R.id.nombreArtistaObjetoEstandar);
        labelNombreArtistaTextoObjetoEstandar = findViewById(R.id.labelNombreArtistaTextoObjetoEstandar);
        nombreBox = findViewById(R.id.nombreObjetoEstandar);
        nPlazaBox = findViewById(R.id.nPlazaObjetoEstandar);
        descBox = findViewById(R.id.descripcionObjetoEstandar);
        cargarObjeto = findViewById(R.id.botonGargarObjeto);
        cargarObjeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEstadoCamposObjetos()){
                    proponerObjeto();
                }else{
                    showAlert("Error.", "Debe completar todos los campos pertinentes para continuar.");
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.estandarBoton:
                        botonEstandar.setTextColor(Color.WHITE);
                        botonEstandar.setBackground(getDrawable(R.drawable.radio_button_left_checked));
                        botonObra.setTextColor(Color.RED);
                        botonObra.setBackground(getDrawable(R.drawable.radio_button_right_unchecked));
                        artista.setVisibility(View.GONE);
                        labelNombreArtistaTextoObjetoEstandar.setVisibility(View.GONE);
                        break;
                    case R.id.obraBoton:
                        botonEstandar.setTextColor(Color.RED);
                        botonEstandar.setBackground(getDrawable(R.drawable.radio_button_left_unchecked));
                        botonObra.setTextColor(Color.WHITE);
                        botonObra.setBackground(getDrawable(R.drawable.radio_button_right_checked));
                        artista.setVisibility(View.VISIBLE);
                        labelNombreArtistaTextoObjetoEstandar.setVisibility(View.VISIBLE);
                        artistaBox.setVisibility(View.VISIBLE);
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
        System.out.println("Por aca");
        System.out.println(botonEstandar.isChecked());
        System.out.println(botonObra.isChecked());
        if (botonObra.isChecked()) {
            if (!nombre.isEmpty() && !nArtista.isEmpty() && !nPlaza.isEmpty() && !descripcion.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }else {
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

    private void proponerObjeto() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        String nombre = nombreBox.getText().toString();
        String nArtista = artistaBox.getText().toString();
        String nPlaza = nPlazaBox.getText().toString();
        String descripcion = descBox.getText().toString();
        String result = "";
        if(nArtista != null && !nArtista.isEmpty()) {
            result = "Nombre del Artista: " + nArtista + " \n";
        }
        System.out.println(result);
        result = result + "Numero de pieza: " + nPlaza + "\n" + descripcion;
        System.out.println(result);
        Item item = new Item(0, nombre, result, "", 0, 0, "Pending", null);
        Call<ResponseItemsPropuestos> call = as.postProducto(item, "Bearer "+ token);

        call.enqueue(new Callback<ResponseItemsPropuestos>() {
            @Override
            public void onResponse(Call<ResponseItemsPropuestos> call, Response<ResponseItemsPropuestos> response) {
                if(response.isSuccessful()) {
                    ResponseItemsPropuestos itemResponse = response.body();
                    passDataBack(itemResponse.getData());
                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Error al proponer el objeto", Toast.LENGTH_LONG);
                    toast1.show();
                    System.out.println(response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseItemsPropuestos> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error al intentar hacer la request", Toast.LENGTH_LONG);
                toast1.show();
            }
        });
    }

    private void passDataBack(Item item) {
        Intent intent = new Intent();
        intent.putExtra("nuevoObjeto", item);
        setResult(RESULT_OK, intent);
        finish();
    }
}