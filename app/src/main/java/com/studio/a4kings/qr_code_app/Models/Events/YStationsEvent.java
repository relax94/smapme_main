package com.studio.a4kings.qr_code_app.Models.Events;

import com.studio.a4kings.qr_code_app.Models.Response.YResponse;

/**
 * Created by Dmitry Pavlenko on 24.03.2016.
 */
public class YStationsEvent {

    private YResponse response;

    public YStationsEvent(YResponse response) {
        this.response = response;
    }

    public YResponse getResponse() {
        return response;
    }
}
