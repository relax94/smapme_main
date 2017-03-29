package com.studio.a4kings.qr_code_app.Callbacks;

import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;

import retrofit2.Response;

/**
 * Created by DUX on 24.05.2016.
 */
public abstract class ResponseMethods {

    public abstract void onSuccess(Response<? extends MainResponse> response);
    public void onNullBody(){

    }
    public void onError(Throwable t){

    }
}
