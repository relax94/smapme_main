package com.studio.a4kings.qr_code_app.Models.EventModels;

import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;

import java.util.List;

/**
 * Created by DUX on 20.03.2016.
 */

public class EventsResponse extends MainResponse {

    List<EventResponseModel> model;

    public List<EventResponseModel> getModel() {
        return model;
    }

    public void setModel(List<EventResponseModel> model) {
        this.model = model;
    }
}
