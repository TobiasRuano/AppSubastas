package com.trotos.appsubastas;

import android.util.Pair;

import com.trotos.appsubastas.Modelos.Bid;
import com.trotos.appsubastas.Modelos.ItemCatalogo;
import com.trotos.appsubastas.Modelos.LoginInformation;
import com.trotos.appsubastas.Modelos.MPTarjeta;
import com.trotos.appsubastas.Modelos.Item;
import com.trotos.appsubastas.Modelos.ResponseItems;
import com.trotos.appsubastas.Modelos.ResponseLogIn;
import com.trotos.appsubastas.Modelos.ResponseMPTarjetas;
import com.trotos.appsubastas.Modelos.ResponseStatisticsUser;
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

    @HTTP(method = "POST", path = "auction/bid", hasBody = true)
    Call<String> postBid(@Body Bid bid);

    @GET("Usuario")
    Call<User> getUsuario(@Query("id") int id);

    @HTTP(method = "POST", path = "user/items/won", hasBody = true)
    Call<ResponseStatisticsUser> getItemsWonCount(@Body User user, @Header("Authorization") String auth);

    @HTTP(method = "POST", path = "user/checkpass", hasBody = true)
    Call<User> checkPasswordUsuario(@Body LoginInformation logIn);

    @HTTP(method = "POST", path = "user/getitems", hasBody = true)
    Call<ResponseItems> getObjetosPropuestos(@Body User user, @Header("Authorization") String auth);
    @GET("users")
    Call<List<Subasta>> getSubastasParticipadas(@Body String userID);

    @HTTP(method = "POST", path = "user/getpayment", hasBody = true)
    Call<ResponseMPTarjetas> getTarjetas(@Body User user, @Header("Authorization") String auth);

    @HTTP(method = "POST", path = "user/sign-up", hasBody = true)
    Call<User> createAccount(@Body User user);
    @POST("users/{userId}/items")
    Call<Item> postProducto(@Body Item item);
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
