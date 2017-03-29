package com.studio.a4kings.qr_code_app.Api.Interface;

import com.studio.a4kings.qr_code_app.Models.Response.ImageEventResponse;
import com.studio.a4kings.qr_code_app.Models.Response.PhotoResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ProfileImagesResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ProfileResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ProfileStatusResponse;
import com.studio.a4kings.qr_code_app.Models.Response.UserResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hack on 25.01.16.
 */
public interface ProfileInterface {

    @GET("/api/profile/getUser")
    Call<UserResponse> getUser(@Header("Authorization") String authorizationToken);

    @GET("/api/profile/getProfile")
    Call<ProfileResponse> getProfile(@Header("Authorization") String authorizationToken);

    @GET("/api/profile/getImages")
    Call<ProfileImagesResponse> getImages(@Header("Authorization") String authorizationToken);

    @GET("/api/profile/getImagesById")
    Call<ProfileImagesResponse> getImages(@Header("Authorization") String authorizationToken, @Query("userId") String userId);

    @POST("/api/profile/update")
    @FormUrlEncoded
    Call<ProfileResponse> updateProfile(@Header("Authorization") String authorizationToken, @FieldMap Map<String, String> args);

    @POST("/api/profile/updatePhoto")
    @FormUrlEncoded
    Call<PhotoResponse> updatePhoto(@Header("Authorization") String authorizationToken, @Field("upload") String upload);

    @POST("/api/profile/updateWallpaper")
    @FormUrlEncoded
    Call<PhotoResponse> updateWallPaper(@Header("Authorization") String authorizationToken, @Field("upload") String upload);

    @GET("/api/profile/getProfileStatus")
    Call<ProfileStatusResponse> getProfileStatus(@Header("Authorization") String authorizationToken);

    @GET("/api/profile/getProfileById")
    Call<ProfileResponse> getProfileById(@Header("Authorization") String authorizationToken, @Query("id") String userId);
}
