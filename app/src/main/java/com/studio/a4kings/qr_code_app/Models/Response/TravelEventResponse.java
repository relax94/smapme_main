package com.studio.a4kings.qr_code_app.Models.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.studio.a4kings.qr_code_app.Models.TravelEventModel;

import java.util.ArrayList;

/**
 * Created by DUX on 26.02.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TravelEventResponse extends MainResponse {
    private ArrayList<TravelEventModel> events;

    public ArrayList<TravelEventModel> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<TravelEventModel> events) {
        this.events = events;
    }
}
