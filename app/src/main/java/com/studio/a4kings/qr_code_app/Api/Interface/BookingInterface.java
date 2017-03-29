package com.studio.a4kings.qr_code_app.Api.Interface;

import com.studio.a4kings.qr_code_app.Models.BookingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Dmitry Pavlenko on 18.03.2016.
 */
public interface BookingInterface {

    @GET("/api/booking/loadRecords/{criteriaKey}/{criteriaVal}")
    Call<BookingResponse> loadRecordsByCriteria(@Path("criteriaKey") String criteriaKey, @Path("criteriaVal") String criteriaVal);
}
