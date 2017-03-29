package com.studio.a4kings.qr_code_app.Models.EventModels;

import com.studio.a4kings.qr_code_app.Models.Response.*;

/**
 * Created by DUX on 24.03.2016.
 */
public class EventDetailResponse extends MainResponse {
    EventResponseModel model;

    public EventResponseModel getModel() {
        return model;
    }

    public void setModel(EventResponseModel model) {
        this.model = model;
    }
}
