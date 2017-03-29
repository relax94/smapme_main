package com.studio.a4kings.qr_code_app.Api.Interface;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.studio.a4kings.qr_code_app.Activities.Map;
import com.studio.a4kings.qr_code_app.Models.Response.GDirection;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Dmitry Pavlenko on 13.03.2016.
 */
public interface GServicesInterface {

    @GET("/maps/api/directions/json")
    Call<GDirection> getGmapsDirection(@QueryMap HashMap<String, String> query);


}
