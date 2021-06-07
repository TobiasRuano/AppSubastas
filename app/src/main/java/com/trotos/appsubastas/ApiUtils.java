package com.trotos.appsubastas;

import com.trotos.appsubastas.Modelos.ItemCatalogo;
import com.trotos.appsubastas.Modelos.MPTarjeta;
import com.trotos.appsubastas.Modelos.Producto;
import com.trotos.appsubastas.Modelos.Subasta;
import com.trotos.appsubastas.Modelos.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface ApiUtils {

    @GET("Subastas")
    Call<List<Subasta>> getSubastas();
    @GET("ItemCatalogo")
    Call<List<ItemCatalogo>> getItemsSubasta(@Query("subastaid") int subastaid);
    @POST("auctions/{auctionId}/bid")
    Call<ItemCatalogo> postBid(@Body ItemCatalogo item);



    @GET("Usuario")
    Call<List<User>> getUsuarios();
    @GET("Usuario")
    Call<User> getUsuario(@Query("id") int id);

    @HTTP(method = "GET", path = "/users", hasBody = true)
    Call<User> checkPasswordUsuario(@Body String mail);
    @GET("users/{userId}/items")
    Call<List<Producto>> getObjetosPropuestos();
    @GET("users")
    Call<List<Subasta>> getSubastasParticipadas(@Body String userID);

    @GET("Tarjetas")
    Call<List<MPTarjeta>> getTarjetas();

    @POST("users")
    Call<User> createAccount(@Body User user);
    @POST("users/{userId}/items")
    Call<Producto> postProducto(@Body Producto producto);
    @POST("users/{userId}/payment_methods")
    Call<MPTarjeta> postTarjeta(@Body MPTarjeta tarjeta);

    @PATCH("users/{userId}")
    Call<User> modifyUser(@Body User user);

    @HTTP(method = "DELETE", path = "users/{userId}/payment_methods/{paymentMethodId}", hasBody = true)
    Call<MPTarjeta> deleteTarjeta(@Body MPTarjeta tarjeta);

    @POST("password")
    Call<User> createPassword(@Body String password);

    @POST("login")
    Call<User> logIn();

}
