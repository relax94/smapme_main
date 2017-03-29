package com.studio.a4kings.qr_code_app.Api.Interface;

import com.studio.a4kings.qr_code_app.Models.Activity.ActivityResponse;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by DUX on 13.06.2016.
 */
public interface ActivityInterface {
    @GET("/api/activities/get")
    Call<ActivityResponse> getAll(@Header("Authorization") String authorizationToken);

}
