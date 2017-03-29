package com.studio.a4kings.qr_code_app.Models.Events;

import java.util.Objects;

/**
 * Created by Dmitry Pavlenko on 07.06.2016.
 */
public class SignalSentEvent {
    private String message;
    private Object arg;

    public SignalSentEvent(String message, Object arg) {
        this.arg = arg;
        this.message = message;
    }

    public Object getArg() {
        return arg;
    }

    public void setArg(Object arg) {
        this.arg = arg;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
