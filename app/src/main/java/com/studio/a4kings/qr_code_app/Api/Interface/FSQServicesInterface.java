package com.studio.a4kings.qr_code_app.Api.Interface;

import com.studio.a4kings.qr_code_app.Models.FSQResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Dmitry Pavlenko on 15.03.2016.
 */
public interface FSQServicesInterface {

    @GET("/v2/venues/search")
    Call<FSQResponse> fourSquareSearchByCoords(@QueryMap HashMap<String,String> query);

    @GET("/v2/venues/search")
    Observable<FSQResponse> fourSquareSearchByCoordsRx(@QueryMap HashMap<String,String> query);

}
