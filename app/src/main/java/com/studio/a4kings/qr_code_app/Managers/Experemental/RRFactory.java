package com.studio.a4kings.qr_code_app.Managers.Experemental;

import com.squareup.picasso.Downloader;
import com.studio.a4kings.qr_code_app.Models.Callbacks.RFBroadcastResponse;
import com.studio.a4kings.qr_code_app.Models.Callbacks.RRFCallback;
import com.studio.a4kings.qr_code_app.Models.Events.CallbackResponseEvent;

import retrofit2.Callback;

/**
 * Created by Dmitry Pavlenko on 26.05.2016.
 */
public class RRFactory {
    private static RRFactory instance = new RRFactory();

    public static RRFactory getInstance() {
        return instance;
    }


/*    public <T> Callback<T> getSingleCallback(String request) {
        return new RRFCallback<>(request);
    }

    public Callback getBroadcastCallback(String requestedMethod) {
        return new RRFCallback<RFBroadcast>(requestedMethod);
    }*/

}
