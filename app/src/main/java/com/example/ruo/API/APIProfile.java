package com.example.ruo.API;

import com.example.ruo.pojo.Profile.AddMyMessageResponse;
import com.example.ruo.pojo.Profile.CommentMessageResponse;
import com.example.ruo.pojo.Profile.DataUserResponse;
import com.example.ruo.pojo.Profile.DeleteMyMessageResponse;
import com.example.ruo.pojo.Profile.DetailMyMessageResponse;
import com.example.ruo.pojo.Profile.EditMyMessageResponse;
import com.example.ruo.pojo.Profile.MyMessageResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIProfile {
    @GET("dataUser/{id_user}")
    Call<DataUserResponse> getDataUSerResp(@Header("Authorization") String token, @Path("id_user") int id_user);

    @GET("myMessage/{id_user}")
    Call<MyMessageResponse> getMyMessageResp (@Header("Authorization") String token, @Path("id_user") int id_user);

    @FormUrlEncoded
    @POST("addMessage/{id_user}")
    Call<AddMyMessageResponse> postAddMyMessageResp (@Header("Authorization") String token, @Path("id_user") int id_user,
                                                     @Field("isi_message") String isi_message);

    @GET("detailMessage/{id_message}")
    Call<DetailMyMessageResponse> getDetailMessageKu (@Header("Authorization") String token, @Path("id_message") int id_message);

    @FormUrlEncoded
    @POST("editMyMessage/{id_message}")
    Call<EditMyMessageResponse> postMyMessageResp (@Header("Authorization") String token, @Path("id_message") int id_message,
                                                   @Field("isi_message") String isi_message);
//                                                   @Part("id_hide") RequestBody idHide)
    @DELETE ("deleteMyMessage/{id_message}")
    Call<DeleteMyMessageResponse> delMessageResp (@Header("Authorization") String token, @Path("id_message") int id_message);


//    @POST("editMyMessage/{id_message}")
//    Call<EditMyMessageResponse> postEditMessageResp (@Header("Authorization") String token, @Path("id_message") int id_message);



//    @DELETE("deleteMyMessage/{id_message}")
//    Call<DeleteMyMessageResponse> delMessageResp (@Header("Authorization") String token, @Path("id_message") int id_message);

//    Call<MyMessageResponse> getMyMessageResp (@Header("Authorization") String token, @Path("id_user") int id_user);

//    @POST("editProfile/{id_user}")
//    Call<EditProfileResponse> getEditProfileResp(@Header("Authorization") String token, @Path("id_user") int id_user);
//    @GET("detailMessage/{id_message}")
//    Call<DetailMessageResponse> getDetailMessageResp (@Header("Authorization") String token, @Path("id_user") int id_user);
//    //comment get
//    @GET("commentMessage/{id_message}")
//    Call<CommentMessageResponse> getCommentMessageResp (@Header("Authorization") String token, @Path("id_message") int id_message);
//    //edit post
//    //del dele
//    //add post
//    @POST("addMessage/{id_user}")
//    Call<AddMessageResponse> addMessageResp (@Header("Authorization") String token, @Path("id_user") int id_user);

    @GET ("commentMessage/{id_message}")
    Call<CommentMessageResponse> getCommentMessageResp (@Header("Authorization") String token, @Path("id_message") int id_message);

}
