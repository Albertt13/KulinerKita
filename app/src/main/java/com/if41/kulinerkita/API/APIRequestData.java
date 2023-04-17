package com.if41.kulinerkita.API;

import com.if41.kulinerkita.Model.ModelResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRequestData {
    @GET("retrieve.php")
    Call<ModelResponse> ardRetrieve();
    //Create
    @FormUrlEncoded
    @POST("create.php")
    Call<ModelResponse> ardCreate(
            @Field("nama") String nama,
            @Field("asal") String asal,
            @Field("deskripsi_singkat") String deskripsi_singkat
    );
    //Update
    @FormUrlEncoded
    @POST("update.php")
    Call<ModelResponse> ardUpdate(
            @Field("id")  String id,
            @Field("nama") String nama,
            @Field("asal") String asal,
            @Field("deskripsi_singkat") String deskripsi_singkat
    );
    //Delete
    @FormUrlEncoded
    @POST("delete.php")
    Call<ModelResponse> ardDelete(
            @Field("id")  String id
    );
}
