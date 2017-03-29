package com.studio.a4kings.qr_code_app.Models.Response;

/**
 * Created by DUX on 26.02.2016.
 */

public class MainResponse {
    Integer code;
    String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
