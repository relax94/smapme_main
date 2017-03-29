package com.studio.a4kings.qr_code_app.Callbacks;

import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DUX on 24.05.2016.
 */
public class ResponseCallback<T extends MainResponse> implements Callback<T> {

    ResponseMethods responseMethods;

    public ResponseCallback(ResponseMethods responseMethods) {
        this.responseMethods = responseMethods;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.body() != null){
            if(response.body().getCode() != null && response.body().getCode() == 200){
                responseMethods.onSuccess(response);
            }
        }
        else responseMethods.onNullBody();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        responseMethods.onError(t);
    }
}
