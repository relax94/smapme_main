package com.studio.a4kings.qr_code_app.Api.Interface;

import com.studio.a4kings.qr_code_app.Models.Response.AllUsersResponse;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by DUX on 24.03.2016.
 */
public interface SubscriptionInterface {

    @POST("/api/sub/add")
    Call<MainResponse> subscribe(@Header("Authorization") String authorizationToken,
                                 @Query("subId") String subId);

    @GET("/api/sub/deleteSub")
    Call<MainResponse> deleteSub(@Header("Authorization") String authorizationToken,
                                 @Query("subId") String subId);


    @GET("/api/user/getAllUsers")
    Call<AllUsersResponse> getAllUsers(@Header("Authorization") String authorizationToken);

    @GET("/api/sub/get")
    Call<AllUsersResponse> getAllUserSubscribers(@Header("Authorization") String authorizationToken);

    @GET("/api/sub/getAllById")
    Call<AllUsersResponse> getAllUserSubscribersById(@Header("Authorization") String authorizationToken, @Query("userId") String userId);
}
