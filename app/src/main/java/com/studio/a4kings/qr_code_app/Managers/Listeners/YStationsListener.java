package com.studio.a4kings.qr_code_app.Managers.Listeners;

import com.studio.a4kings.qr_code_app.Models.Events.YStationsEvent;
import com.studio.a4kings.qr_code_app.Models.Response.YResponse;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dmitry Pavlenko on 24.03.2016.
 */
public class YStationsListener implements Callback<YResponse> {
    @Override
    public void onResponse(Call<YResponse> call, Response<YResponse> response) {
        if(response != null)
            EventBus.getDefault().post(new YStationsEvent(response.body()));
    }

    @Override
    public void onFailure(Call<YResponse> call, Throwable t) {

    }
}
