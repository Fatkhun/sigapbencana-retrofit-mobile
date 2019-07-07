package com.example.bahroel.sigapbencana.network;

import com.example.bahroel.sigapbencana.network.model.Bencana;
import com.example.bahroel.sigapbencana.network.model.BencanaResponse;
import com.example.bahroel.sigapbencana.network.model.Berita;
import com.example.bahroel.sigapbencana.network.model.Kategori;
import com.example.bahroel.sigapbencana.network.model.LoginResponse;
import com.example.bahroel.sigapbencana.network.model.Lurah;
import com.example.bahroel.sigapbencana.network.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("user/login")
    Single<LoginResponse> login(@Field("email") String email,
                                @Field("password") String password);

    @FormUrlEncoded
    @POST("bencana/lapor")
    Single<BencanaResponse> setBencana(@Field("alamat") String alamat,
                                       @Field("users_id") String usersId,
                                       @Field("kategori_id") String kategoriId);

    @FormUrlEncoded
    @POST("user/update/password")
    Single<LoginResponse> updatePassword(@Field("old_password") String oldPassword,
                                         @Field("password") String newPassword,
                                         @Field("api_token") String apiToken);


    @GET("berita")
    Single<List<Berita>> getBerita();

    @GET("user/detail/{id}")
    Single<LoginResponse> getLurah(@Path("id") String id);



}
