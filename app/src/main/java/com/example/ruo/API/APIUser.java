package com.example.ruo.API;

import com.example.ruo.pojo.LoginResponse;
import com.example.ruo.pojo.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIUser {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> getLoginResp(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> getRegistResp(@Field("username") String username, @Field("email") String email, @Field("password") String password);

}
