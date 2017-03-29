package com.studio.a4kings.qr_code_app.Models.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.studio.a4kings.qr_code_app.Models.TripModel;

/**
 * Created by Dmitry Pavlenko on 25.05.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripResponse {
    private boolean state;
    private TripModel r;

    public TripModel getTripModel() {
        return r;
    }

    public TripModel getR() {
        return r;
    }

    public void setR(TripModel r) {
        this.r = r;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
