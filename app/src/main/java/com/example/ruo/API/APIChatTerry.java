package com.example.ruo.API;

import com.example.ruo.pojo.chatTerry.Answer1Response;
import com.example.ruo.pojo.chatTerry.Answer2Response;
import com.example.ruo.pojo.chatTerry.QuestionResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIChatTerry {
    @FormUrlEncoded
    @POST("answer1/{id_user}")
    Call<Answer1Response> getAnswer1Resp(@Header("Authorization") String token,
                                         @Field("answer1") String answer1, @Path("id_user")Integer id_user);

    @FormUrlEncoded
    @POST("answer2/{id_user}")
    Call<Answer2Response> getAnswer2Resp(@Header("Authorization") String token, @Field("answer2") String answer2, @Path("id_user")Integer id_user);

    @GET("getQuest")
    Call<QuestionResponse> getQuestResp(@Header("Authorization") String token);

}
