package com.studio.a4kings.qr_code_app.Models.Events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Created by Dmitry Pavlenko on 07.06.2016.
 */
public class SignalEvent {
    private String method;
    private String body;

    public SignalEvent(String method, String body) {
        this.method = method;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public Object getBody() {
        return body;
    }

    public <T> T convertBodyTo(Class<T> type){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(this.body, type);
        }
        catch (Exception ex){
            return null;
        }
    }
}
