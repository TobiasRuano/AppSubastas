package com.trotos.appsubastas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

interface ApiUtils {

    @GET("auctions")
    Call<List<Subasta>> getSubastas();
    @GET("auctions")
    Call<List<ItemCatalogo>> getItemsSubasta(@Body String idSubasta);
    @POST("auctions")
    Call<ItemCatalogo> postBid(@Body ItemCatalogo item);

    @GET("users")
    Call<User> checkContraseñaUsuario(@Body String mail);
    @GET("users")
    Call<List<Producto>> getObjetosPropuestos();
    @GET("users")
    Call<List<Subasta>> getSubastasParticipadas(@Body String userID);
    @GET("users")
    Call<List<MPTarjeta>> getTarjetas();

    @POST("users")
    Call<User> createAccount(@Body User user);
    @POST("users")
    Call<Producto> postProducto(@Body Producto producto);
    @POST("users")
    Call<MPTarjeta> postTarjeta(@Body MPTarjeta tarjeta);

    @PATCH("users")
    Call<User> modifyUser(@Body User user);
    @DELETE("users")
    Call<MPTarjeta> deleteTarjeta(@Body MPTarjeta tarjeta);

    @POST("password")
    Call<User> createPassword(@Body String password);

    @POST("login")
    Call<User> logIn(@Body String mail, @Body String password);
}
