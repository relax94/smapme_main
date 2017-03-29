package com.studio.a4kings.qr_code_app.Models.Events;

import java.lang.reflect.Type;

/**
 * Created by Dmitry Pavlenko on 28.05.2016.
 */
public class CallbackResponseEvent<T> {
    private String requestedMethod;
    private T responseModel;

    public CallbackResponseEvent(String requestedMethod, T responseModel) {
        this.requestedMethod = requestedMethod;
        this.responseModel = responseModel;
    }

    public String getRequestedMethod() {
        return requestedMethod;
    }

    public T getResponseModel() {
        return responseModel;
    }
}
