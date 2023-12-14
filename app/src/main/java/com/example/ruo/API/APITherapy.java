package com.example.ruo.API;

import com.example.ruo.pojo.Therapy.AddTherapyResponse;
import com.example.ruo.pojo.Therapy.AllTherapyResponse;
import com.example.ruo.pojo.Therapy.DeleteTherapyResponse;
import com.example.ruo.pojo.Therapy.DetailTherapyResponse;
import com.example.ruo.pojo.Therapy.EditTherapyResponse;
import com.example.ruo.pojo.Therapy.MyTherapyResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APITherapy {

    @GET("allTherapy")
    Call<AllTherapyResponse> getAllTherapyResp(@Header("Authorization") String token);

    @GET("myTherapy/{id_user}")
    Call<MyTherapyResponse> getAllMyTherapyResp(@Header("Authorization") String token, @Path("id_user") Integer id_user);



    @Multipart
    @POST("addTherapy/{id_user}")
    Call<AddTherapyResponse> getAddTherapyResp(@Header("Authorization") String token, @Path("id_user") Integer id_user, @Part MultipartBody.Part file,
                                        @Part("nama_psikolog") RequestBody nama_psikolog,
                                        @Part("lama_karir") RequestBody lama_karir,
                                        @Part("no_telp_psikolog") RequestBody no_telp_psikolog,
                                        @Part("medsos_psikolog") RequestBody medsos_psikolog,
                                        @Part("spesialis_psikolog") RequestBody spesialis_psikolog);
    @GET("detailTherapy/{id_therapy}")
    Call<DetailTherapyResponse> getDetailTherapyResp(@Header("Authorization") String token, @Path("id_therapy") Integer id_therapy);

    @Multipart
    @POST("editTherapy/{id_therapy}")
    Call<EditTherapyResponse> getEditTherapyResp(@Header("Authorization") String token, @Path("id_therapy") Integer id_therapy, @Part MultipartBody.Part file,
                                                @Part("nama_psikolog") RequestBody nama_psikolog,
                                                @Part("lama_karir") RequestBody lama_karir,
                                                @Part("no_telp_psikolog") RequestBody no_telp_psikolog,
                                                @Part("medsos_psikolog") RequestBody medsos_psikolog,
                                                @Part("spesialis_psikolog") RequestBody spesialis_psikolog);

    @Multipart
    @POST("editTherapy/{id_therapy}")
    Call<EditTherapyResponse> getEditTherapyRespNo(@Header("Authorization") String token, @Path("id_therapy") Integer id_therapy,
                                                 @Part("nama_psikolog") RequestBody nama_psikolog,
                                                 @Part("lama_karir") RequestBody lama_karir,
                                                 @Part("no_telp_psikolog") RequestBody no_telp_psikolog,
                                                 @Part("medsos_psikolog") RequestBody medsos_psikolog,
                                                 @Part("spesialis_psikolog") RequestBody spesialis_psikolog);

    @DELETE("deleteTherapy/{id_therapy}")
    Call<DeleteTherapyResponse> getDeleteTherapyResp(@Header("Authorization") String token, @Path("id_therapy") Integer id_therapy);

}
