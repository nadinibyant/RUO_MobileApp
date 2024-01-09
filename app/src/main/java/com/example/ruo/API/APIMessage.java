package com.example.ruo.API;

import com.example.ruo.pojo.message.AccountResponse;
import com.example.ruo.pojo.message.CommentMessageResponse;
import com.example.ruo.pojo.message.DetailMessageResponse;
import com.example.ruo.pojo.message.GeneralResponse;
import com.example.ruo.pojo.message.GetAllMessageResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIMessage {

    @GET("allMessage")
    Call<GetAllMessageResponse> getAllMessage(@Header("Authorization") String token);

    @GET("getAccount/{id_message}")
    Call<AccountResponse> getAccount(@Header("Authorization") String token,
        @Path("id_message") Integer idMessage);

    @GET("detailMessage/{id_message}")
    Call<DetailMessageResponse> detailMessage(@Header("Authorization") String token,
        @Path("id_message") Integer idMessage);

    @GET("commentMessage/{id_message}")
    Call<CommentMessageResponse> commentMessage(@Header("Authorization") String token,
        @Path("id_message") Integer idMessage);

    @FormUrlEncoded
    @POST("addComment/{id_message}/{id_user}")
    Call<GeneralResponse> addComment(@Header("Authorization") String token,
        @Path("id_message") Integer idMessage, @Path("id_user") Integer idUser, @Field("isi_comment") String commentText);

    @FormUrlEncoded
    @POST("addMessage/{id_user}")
    Call<GeneralResponse> addMessage(@Header("Authorization") String token,
        @Path("id_user") Integer idUser,
        @Field("isi_message") String isiMessage,
        @Field("status_hide") boolean statusHide
    );

}