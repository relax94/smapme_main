package com.studio.a4kings.qr_code_app.Models.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Dmitry Pavlenko on 14.06.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerResponse<T> {
    @JsonProperty("state")
    private boolean state;
    @JsonProperty("r")
    private T r;

    public ServerResponse() {
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public T getR() {
        return r;
    }

    public void setR(T r) {
        this.r = r;
    }
}
