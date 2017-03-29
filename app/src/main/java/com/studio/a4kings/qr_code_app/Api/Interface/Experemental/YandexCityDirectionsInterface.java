package com.studio.a4kings.qr_code_app.Api.Interface.Experemental;

import com.studio.a4kings.qr_code_app.Models.Response.YResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Dmitry Pavlenko on 24.03.2016.
 */
public interface YandexCityDirectionsInterface {

    @GET("/v1.0/nearest_stations/")
    Call<YResponse> getNearestStations(@QueryMap Map<String, String> query);

}
