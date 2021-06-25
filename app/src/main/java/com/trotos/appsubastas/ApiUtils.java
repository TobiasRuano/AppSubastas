package com.trotos.appsubastas;

import android.util.Pair;

import com.trotos.appsubastas.Modelos.ItemCatalogo;
import com.trotos.appsubastas.Modelos.LoginInformation;
import com.trotos.appsubastas.Modelos.MPTarjeta;
import com.trotos.appsubastas.Modelos.Producto;
import com.trotos.appsubastas.Modelos.ResponseLogIn;
import com.trotos.appsubastas.Modelos.ResponseMPTarjetas;
import com.trotos.appsubastas.Modelos.Subasta;
import com.trotos.appsubastas.Modelos.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface ApiUtils {

    @GET("subastas/")
    Call<List<Subasta>> getSubastas();
    @GET("ItemCatalogo")
    Call<List<ItemCatalogo>> getItemsSubasta(@Query("subastaid") int subastaid);
    @POST("auctions/{auctionId}/bid")
    Call<ItemCatalogo> postBid(@Body ItemCatalogo item);

    @GET("Usuario")
    Call<User> getUsuario(@Query("id") int id);

    @HTTP(method = "POST", path = "user/checkpass", hasBody = true)
    Call<User> checkPasswordUsuario(@Body LoginInformation logIn);
    @GET("users/{userId}/items")
    Call<List<Producto>> getObjetosPropuestos();
    @GET("users")
    Call<List<Subasta>> getSubastasParticipadas(@Body String userID);

    @HTTP(method = "POST", path = "user/getpayment", hasBody = true)
    Call<ResponseMPTarjetas> getTarjetas(@Body User user, @Header("Authorization") String auth);

    @HTTP(method = "POST", path = "user/sign-up", hasBody = true)
    Call<User> createAccount(@Body User user);
    @POST("users/{userId}/items")
    Call<Producto> postProducto(@Body Producto producto);
    @POST("user/payment")
    Call<MPTarjeta> postTarjeta(@Body MPTarjeta tarjeta, @Header("Authorization") String auth);

    @HTTP(method = "PATCH", path = "user/update", hasBody = true)
    Call<Pair<String, String>> modifyUser(@Body User user, @Header("Authorization") String auth);

    @HTTP(method = "DELETE", path = "user/payment", hasBody = true)
    Call<MPTarjeta> deleteTarjeta(@Body MPTarjeta tarjeta, @Header("Authorization") String auth);

    @POST("password")
    Call<User> createPassword(@Body LoginInformation logIn);

    @HTTP(method = "POST", path = "login/", hasBody = true)
    Call<ResponseLogIn> logIn(@Body LoginInformation logIn);

}
