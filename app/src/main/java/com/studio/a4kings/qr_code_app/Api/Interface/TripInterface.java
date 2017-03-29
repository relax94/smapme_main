package com.studio.a4kings.qr_code_app.Api.Interface;

import com.studio.a4kings.qr_code_app.Models.Migration.VkMigrationPage;
import com.studio.a4kings.qr_code_app.Models.Response.ServerResponse;
import com.studio.a4kings.qr_code_app.Models.Response.SnapshotUploadResponse;
import com.studio.a4kings.qr_code_app.Models.Response.TripResponse;
import com.studio.a4kings.qr_code_app.Models.TripModel;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

/**
 * Created by Dmitry Pavlenko on 25.05.2016.
 */
public interface TripInterface {

    @POST("/trip/save")
 //   @FormUrlEncoded
    Call<TripResponse> saveTrip(@Body TripModel model);

    @GET("/trip/get")
    Call<TripResponse> getTrip(@QueryMap HashMap<String, String> stringHashMap);

    @Multipart
    @POST("/trip/uploadsnapshot")
    Call<SnapshotUploadResponse> uploadTripSnapshot(@Part("description")RequestBody description, @Part MultipartBody.Part file);

    @POST("/migrate/vk")
    @FormUrlEncoded
    Call<ServerResponse<VkMigrationPage>> vkMigration(@Field("page_id") String pageId);

}
