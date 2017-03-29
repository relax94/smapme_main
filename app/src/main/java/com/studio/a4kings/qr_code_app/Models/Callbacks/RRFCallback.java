package com.studio.a4kings.qr_code_app.Models.Callbacks;

import com.studio.a4kings.qr_code_app.Models.Events.CallbackResponseEvent;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dmitry Pavlenko on 29.05.2016.
 */
public class RRFCallback<T> implements Callback<T> {

    private String requestedMethod;
    private boolean isBroadcastRequest = false;


    public RRFCallback(String requestedMethod) {
        this.requestedMethod = requestedMethod;
    }


    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response != null) {
            if(!isBroadcastRequest)
                EventBus.getDefault().post(new CallbackResponseEvent<>(requestedMethod, response.body()));
            else
                EventBus.getDefault().post(new RFBroadcastResponse<>(requestedMethod, response.body()));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {

    }

    public Callback getGroupCallback(){
        this.isBroadcastRequest = true;
        return this;
    }

}
