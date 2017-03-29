package com.studio.a4kings.qr_code_app.Models.Callbacks;

/**
 * Created by Dmitry Pavlenko on 29.05.2016.
 */
public class RFBroadcastResponse<T> {

    private String requestMethod;
    private T response;

    public RFBroadcastResponse(String requestMethod, T response) {
        this.requestMethod = requestMethod;
        this.response = response;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public T getResponse() {
        return response;
    }
}
