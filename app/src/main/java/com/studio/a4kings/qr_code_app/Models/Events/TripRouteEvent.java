package com.studio.a4kings.qr_code_app.Models.Events;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.studio.a4kings.qr_code_app.Models.GDirectionModels.GSteps;
import com.studio.a4kings.qr_code_app.Models.Response.GDirection;

import java.util.List;

/**
 * Created by Dmitry Pavlenko on 15.03.2016.
 */
public class TripRouteEvent {
    private PolylineOptions polyline;
    private List<GSteps> gDirection;

    public TripRouteEvent(PolylineOptions polyline, List<GSteps> gDirection) {
        this.polyline = polyline;
        this.gDirection = gDirection;
    }

    public PolylineOptions getPolyline() {
        return polyline;
    }

    public void setPolyline(PolylineOptions polyline) {
        this.polyline = polyline;
    }

    public List<GSteps> getgDirection() {
        return gDirection;
    }
}
