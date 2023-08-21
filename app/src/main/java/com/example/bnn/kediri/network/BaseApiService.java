package com.example.bnn.kediri.network;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface BaseApiService {

    @Multipart
    @POST("abuse")
    Call<ResponseBody> postAbuse(@Part("phone") RequestBody phoneNumber,
                                 @Part("kecamatan") RequestBody kecamatan,
                                 @Part("alamat") RequestBody address,
                                 @Part MultipartBody.Part photo);

    @FormUrlEncoded
    @POST("socialize")
    Call<ResponseBody> postSocialize(@Field("nama") String fullName,
                                     @Field("instansi") String company,
                                     @Field("keperluan") String requested,
                                     @Field("tanggal") String date,
                                     @Field("waktu") String time);

    @FormUrlEncoded
    @POST("rehabilitate")
    Call<ResponseBody> postRehab(@Field("nama") String fullName,
                                 @Field("alamat") String address,
                                 @Field("jenis_kelamin") String gender,
                                 @Field("tanggal_lahir") String date,
                                 @Field("zat") String zat,
                                 @Field("durasi") String duration,
                                 @Field("phone") String phoneNumber);

    @FormUrlEncoded
    @POST("consult")
    Call<ResponseBody> postCounseling(@Field("nama") String fullName,
                                 @Field("alamat") String address,
                                 @Field("jenis_kelamin") String gender,
                                 @Field("tanggal_lahir") String date,
                                 @Field("zat") String zat,
                                 @Field("durasi") String duration,
                                 @Field("phone") String phoneNumber);

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginAdmin(@Field("nik") String nik,
                                  @Field("password") String password);

    @POST("logout")
    Call<ResponseBody> logout(@Header("Authorization") String authToken);

    @GET("admin/abuse")
    Call<ResponseBody> getAllAbuse(@Header("Authorization") String authToken);

    @GET("admin/abuse/{id}")
    Call<ResponseBody> getSingleAbuse(@Header("Authorization") String authToken,
                                      @Path("id") int id);

    @PUT("admin/abuse/{id}")
    Call<ResponseBody> confirmationAbuse(@Header("Authorization") String authToken,
                                         @Path("id") int id);

    @GET("admin/socialize")
    Call<ResponseBody> getAllSocialize(@Header("Authorization") String authToken);

    @GET("admin/socialize/{id}")
    Call<ResponseBody> getSingleSocialize(@Header("Authorization") String authToken,
                                          @Path("id") int id);

    @PUT("admin/socialize/{id}")
    Call<ResponseBody> confirmationSocialize(@Header("Authorization") String authToken,
                                             @Path("id") int id);

    @GET("admin/rehabilitate")
    Call<ResponseBody> getAllRehab(@Header("Authorization") String authToken);


    @GET("admin/rehabilitate/{id}")
    Call<ResponseBody> getSingleRehab(@Header("Authorization") String authToken,
                                      @Path("id") int id);

    @PUT("admin/rehabilitate/{id}")
    Call<ResponseBody> confirmationRehab(@Header("Authorization") String authToken,
                                         @Path("id") int id);

    @GET("admin/consult")
    Call<ResponseBody> getAllCounseling(@Header("Authorization") String authToken);


    @GET("admin/consult/{id}")
    Call<ResponseBody> getSingleCounseling(@Header("Authorization") String authToken,
                                      @Path("id") int id);

    @PUT("admin/consult/{id}")
    Call<ResponseBody> confirmationCounseling(@Header("Authorization") String authToken,
                                         @Path("id") int id);
}
