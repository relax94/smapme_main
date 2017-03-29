package com.studio.a4kings.qr_code_app.Models.EventModels;

import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;

import java.util.List;

/**
 * Created by Dmitry Pavlenko on 07.06.2016.
 */
public class EventFullResponse extends MainResponse {

    EventResponseModel model;

    public EventResponseModel getModel() {
        return model;
    }

    public void setModel(EventResponseModel model) {
        this.model = model;
    }
}
