package com.trotos.appsubastas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trotos.appsubastas.Modelos.ResponseStatisticsUser;
import com.trotos.appsubastas.Modelos.User;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiUsuario extends AppCompatActivity {

    ImageView fotoPerfil, logout;
    FloatingActionButton subirFoto;
    Button editarPerfilBoton;
    BottomNavigationView bottomNavigationView;
    TextView nombre;
    TextView categoriaUsuario;
    TextView cantGanados;
    TextView cantParticipados;
    User user;

    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_usuario);

        ActionBar bar = getSupportActionBar();
        if(bar != null) {
            bar.hide();
        }
        getUser();
        getItemsWonCount();

        subirFoto = findViewById(R.id.subirFoto);
        editarPerfilBoton = findViewById(R.id.editarPerfilBoton);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        nombre = findViewById(R.id.nombre);
        categoriaUsuario = findViewById(R.id.categoriaUsuario);
        String name = user.getName() + ' ' + user.getSurname();
        nombre.setText(name);
        String cat = user.getCategory();
        categoriaUsuario.setText(cat);
        cantGanados = findViewById(R.id.objetosGanados);
        cantParticipados = findViewById(R.id.objetosParticipados);

        logout = findViewById(R.id.logout);

        setCategory(cat);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(MiUsuario.this);
                alerta.setMessage("¿Desea cerrar sesion?")
                .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                logOutUser();
                                startActivity(new Intent(MiUsuario.this, IniciarSesionActivity.class));
                                finish();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog titulo = alerta.create();
                titulo.setTitle("Salida");
                titulo.show();
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.usuarioLogueado);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeLogueado:
                        startActivity(new Intent(MiUsuario.this, MenuLogueado.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.mpLogueado:
                        startActivity(new Intent(MiUsuario.this, MediosPagoActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.msLogueado:
                        startActivity(new Intent(MiUsuario.this, MisCatalogosPujadosActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.moLogueado:
                        startActivity(new Intent(MiUsuario.this, MisObjetos.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.usuarioLogueado:
                        break;
                }
                return false;
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


    private void logOutUser() {
        SharedPreferences  sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.clear();
        prefsEditor.apply();
    }


    private void setCategory(String category) {
        user.setCategory(category);
        saveUser(user);
        switch (category) {
            case "Plata":
                categoriaUsuario.setTextColor(Color.rgb(192,192,192));
                break;
            case "Oro": 
                categoriaUsuario.setTextColor(Color.rgb(255,215,0));
                break;
            case "Platino": 
                categoriaUsuario.setTextColor(Color.rgb(32, 211, 214));
                break;
            default:
                categoriaUsuario.setTextColor(Color.rgb(80,50,20));
                break;
        }
    }

    private void getUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", null);
        Type type = new TypeToken<User>() {}.getType();
        user = gson.fromJson(json, type);
    }

    private void saveUser(User userToSave) {
        SharedPreferences  sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userToSave);
        prefsEditor.putString("User", json);
        prefsEditor.apply();
    }

    private void getItemsWonCount() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String token = sharedPreferences.getString("Token", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUtils as = retrofit.create(ApiUtils.class);
        Call<ResponseStatisticsUser> call = as.getUserStatistics(user, "Bearer "+ token);

        call.enqueue(new Callback<ResponseStatisticsUser>() {
            @Override
            public void onResponse(Call<ResponseStatisticsUser> call, Response<ResponseStatisticsUser> response) {
                if(response.isSuccessful()) {
                    String textGanados = Integer.toString(response.body().getGanados());
                    String textParticipados = Integer.toString(response.body().getParticipados());
                    String textCategoria = response.body().getCategoria();
                    cantGanados.setText(textGanados);
                    cantParticipados.setText(textParticipados);
                    categoriaUsuario.setText(textCategoria);

                    setCategory(textCategoria);
                } else {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Error al intentar obtener los objetos ganados", Toast.LENGTH_LONG);
                    toast1.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatisticsUser> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Hubo un problema", Toast.LENGTH_LONG);
                toast1.show();
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