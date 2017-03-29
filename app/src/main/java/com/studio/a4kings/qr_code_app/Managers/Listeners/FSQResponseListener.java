package com.studio.a4kings.qr_code_app.Managers.Listeners;
import com.studio.a4kings.qr_code_app.Models.Events.FSQPlacesEvent;
import com.studio.a4kings.qr_code_app.Models.FSQResponse;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dmitry Pavlenko on 16.03.2016.
 */
public class FSQResponseListener implements Callback<FSQResponse> {

    @Override
    public void onResponse(Call<FSQResponse> call, Response<FSQResponse> response) {
        if(response != null && response.body() != null)
            EventBus.getDefault().post(new FSQPlacesEvent(response.body()));
    }

    @Override
    public void onFailure(Call<FSQResponse> call, Throwable t) {

    }
}
