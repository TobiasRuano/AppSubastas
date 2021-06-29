package com.trotos.appsubastas;

import android.util.Pair;

import com.trotos.appsubastas.Modelos.Bid;
import com.trotos.appsubastas.Modelos.ItemCatalogo;
import com.trotos.appsubastas.Modelos.LoginInformation;
import com.trotos.appsubastas.Modelos.MPTarjeta;
import com.trotos.appsubastas.Modelos.Item;
import com.trotos.appsubastas.Modelos.ResponseAuctions;
import com.trotos.appsubastas.Modelos.ResponseBids;
import com.trotos.appsubastas.Modelos.ResponseCreateMP;
import com.trotos.appsubastas.Modelos.ResponseItems;
import com.trotos.appsubastas.Modelos.ResponseItemsCatalog;
import com.trotos.appsubastas.Modelos.ResponseItemsPropuestos;
import com.trotos.appsubastas.Modelos.ResponseLogIn;
import com.trotos.appsubastas.Modelos.ResponseMPTarjetas;
import com.trotos.appsubastas.Modelos.ResponseStatisticsUser;
import com.trotos.appsubastas.Modelos.Auction;
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

    @HTTP(method = "GET", path = "auction")
    Call<ResponseAuctions> getSubastas();

    @HTTP(method = "POST", path = "auction/items", hasBody = true)
    Call<ResponseItemsCatalog> getItemsSubasta(@Body Auction auction);

    @HTTP(method = "POST", path = "auction/bid", hasBody = true)
    Call<String> postBid(@Body Bid bid, @Header("Authorization") String auth);

    @HTTP(method = "POST", path = "auction/items/bids", hasBody = true)
    Call<ResponseBids> getBids(@Body ItemCatalogo item, @Header("Authorization") String auth);

    @HTTP(method = "POST", path = "user/items/won", hasBody = true)
    Call<ResponseStatisticsUser> getUserStatistics(@Body User user, @Header("Authorization") String auth);

    @HTTP(method = "POST", path = "user/checkpass", hasBody = true)
    Call<User> checkPasswordUsuario(@Body LoginInformation logIn);

    @HTTP(method = "POST", path = "user/getitems", hasBody = true)
    Call<ResponseItems> getObjetosPropuestos(@Body User user, @Header("Authorization") String auth);

    @HTTP(method = "POST", path = "user/items/bidded", hasBody = true)
    Call<ResponseItemsCatalog> getItemsParticipados(@Body User user, @Header("Authorization") String auth);

    @HTTP(method = "POST", path = "user/getpayment", hasBody = true)
    Call<ResponseMPTarjetas> getTarjetas(@Body User user, @Header("Authorization") String auth);

    @HTTP(method = "POST", path = "user/sign-up", hasBody = true)
    Call<User> createAccount(@Body User user);

    @HTTP(method = "POST", path = "user/items", hasBody = true)
    Call<ResponseItemsPropuestos> postProducto(@Body Item item, @Header("Authorization") String auth);

    @HTTP(method = "PATCH", path = "user/items/offer", hasBody = true)
    Call<Item> actOnOffer(@Body Item item, @Header("Authorization") String auth);

    @POST("user/payment")
    Call<ResponseCreateMP> postTarjeta(@Body MPTarjeta tarjeta, @Header("Authorization") String auth);

    @HTTP(method = "PATCH", path = "user/update", hasBody = true)
    Call<Pair<String, String>> modifyUser(@Body User user, @Header("Authorization") String auth);

    @HTTP(method = "DELETE", path = "user/payment", hasBody = true)
    Call<MPTarjeta> deleteTarjeta(@Body MPTarjeta tarjeta, @Header("Authorization") String auth);

    @POST("password")
    Call<User> createPassword(@Body LoginInformation logIn);

    @HTTP(method = "POST", path = "login/", hasBody = true)
    Call<ResponseLogIn> logIn(@Body LoginInformation logIn);

}
