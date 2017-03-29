package com.studio.a4kings.qr_code_app.Managers.Listeners;

import com.studio.a4kings.qr_code_app.Managers.Api.Services.BookingService;
import com.studio.a4kings.qr_code_app.Models.BookingResponse;
import com.studio.a4kings.qr_code_app.Models.Events.BookingHotelsEvent;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dmitry Pavlenko on 18.03.2016.
 */
public class BookingResponseListener implements Callback<BookingResponse> {
    @Override
    public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
        if(response.body() != null && response.body().getBookingData() != null){
            EventBus.getDefault().post(new BookingHotelsEvent(response.body().getBookingData()));
        }
    }

    @Override
    public void onFailure(Call<BookingResponse> call, Throwable t) {

    }
}
