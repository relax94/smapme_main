package com.studio.a4kings.qr_code_app.Api.Interface;

import com.studio.a4kings.qr_code_app.Models.Response.CountryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by DUX on 16.02.2016.
 */
public interface CountryInterface {
    @GET("/api/country/getCountries")
    Call<CountryResponse> getCountries(@Header("Authorization") String authorizationToken);
}
