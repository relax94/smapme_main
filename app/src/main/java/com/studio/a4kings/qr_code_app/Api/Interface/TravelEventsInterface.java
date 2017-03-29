package com.studio.a4kings.qr_code_app.Api.Interface;

import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;
import com.studio.a4kings.qr_code_app.Models.Response.TravelEventResponse;
import com.studio.a4kings.qr_code_app.Models.TravelEventModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by DUX on 26.02.2016.
 */
public interface TravelEventsInterface {
    @POST("/api/te/addEvent")
    Call<MainResponse> addEvent(@Header("Authorization") String authorizationToken, @Body TravelEventModel travelEvent);

    @GET("/api/te/getEventsList")
    Call<TravelEventResponse> getEventsList(@Header("Authorization") String authorizationToken);

    @POST("/api/te/getUserEventsList")
    @FormUrlEncoded
    Call<TravelEventResponse> getUserEventsList(@Header("Authorization") String authorizationToken, @Field("userId") String userId);

}
