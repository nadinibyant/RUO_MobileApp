package com.example.ruo.API;

import androidx.annotation.IntegerRes;

import com.example.ruo.pojo.Therapy.AddTherapyResponse;
import com.example.ruo.pojo.Therapy.AllTherapyResponse;
import com.example.ruo.pojo.Therapy.DelOtomatisResponse;
import com.example.ruo.pojo.Therapy.DeleteTherapyResponse;
import com.example.ruo.pojo.Therapy.DetailTherapyResponse;
import com.example.ruo.pojo.Therapy.DislikeResponse;
import com.example.ruo.pojo.Therapy.EditTherapyResponse;
import com.example.ruo.pojo.Therapy.FindLikeResponse;
import com.example.ruo.pojo.Therapy.LikeResponse;
import com.example.ruo.pojo.Therapy.MyTherapyResponse;
import com.example.ruo.pojo.peta.PetaResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
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
                                        @Part("spesialis_psikolog") RequestBody spesialis_psikolog,
                                               @Part("alamat_lengkap") RequestBody alamat_lengkap);
    @GET("detailTherapy/{id_therapy}")
    Call<DetailTherapyResponse> getDetailTherapyResp(@Header("Authorization") String token, @Path("id_therapy") Integer id_therapy);

    @Multipart
    @POST("editTherapy/{id_therapy}")
    Call<EditTherapyResponse> getEditTherapyResp(@Header("Authorization") String token, @Path("id_therapy") Integer id_therapy, @Part MultipartBody.Part file,
                                                @Part("nama_psikolog") RequestBody nama_psikolog,
                                                @Part("lama_karir") RequestBody lama_karir,
                                                @Part("no_telp_psikolog") RequestBody no_telp_psikolog,
                                                @Part("medsos_psikolog") RequestBody medsos_psikolog,
                                                @Part("spesialis_psikolog") RequestBody spesialis_psikolog,
                                                 @Part("alamat_lengkap") RequestBody alamat_lengkap);

    @Multipart
    @POST("editTherapy/{id_therapy}")
    Call<EditTherapyResponse> getEditTherapyRespNo(@Header("Authorization") String token, @Path("id_therapy") Integer id_therapy,
                                                 @Part("nama_psikolog") RequestBody nama_psikolog,
                                                 @Part("lama_karir") RequestBody lama_karir,
                                                 @Part("no_telp_psikolog") RequestBody no_telp_psikolog,
                                                 @Part("medsos_psikolog") RequestBody medsos_psikolog,
                                                 @Part("spesialis_psikolog") RequestBody spesialis_psikolog,
                                                   @Part("alamat_lengkap") RequestBody alamat_lengkap);

    @DELETE("deleteTherapy/{id_therapy}")
    Call<DeleteTherapyResponse> getDeleteTherapyResp(@Header("Authorization") String token, @Path("id_therapy") Integer id_therapy);

    @POST("dislike/{id_therapy}/{id_user}")
    Call<DislikeResponse> getDislikeResp(@Header("Authorization") String token, @Path("id_therapy") Integer id_therapy, @Path("id_user") Integer id_user);


    @POST("like/{id_therapy}/{id_user}")
    Call<LikeResponse> getLikeResp(@Header("Authorization") String token, @Path("id_therapy") Integer id_therapy, @Path("id_user") Integer id_user);


    @GET("readyLike/{id_user}/{id_therapy}")
    Call<FindLikeResponse> getFindLikeResp(@Header("Authorization") String token, @Path("id_user") Integer id_user, @Path("id_therapy")Integer id_therapy);

    @GET("geocode/{id_therapy}")
    Call<PetaResponse> getPetaResp(@Header("Authorization") String token, @Path("id_therapy") Integer id_therapy);


}
