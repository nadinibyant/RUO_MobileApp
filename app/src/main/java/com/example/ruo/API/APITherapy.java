package com.example.ruo.API;

import com.example.ruo.pojo.Therapy.AllTherapyResponse;
import com.example.ruo.pojo.Therapy.MyTherapyResponse;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface APITherapy {

    @GET("allTherapy")
    Call<AllTherapyResponse> getAllTherapyResp(@Header("Authorization") String token);

    @GET("myTherapy/{id_user}")
    Call<MyTherapyResponse> getAllMyTherapyResp(@Header("Authorization") String token, @Path("id_user") Integer id_user);
}
