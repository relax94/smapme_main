package com.studio.a4kings.qr_code_app.Models.GDirectionModels;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Dmitry Pavlenko on 14.03.2016.
 */

public class GeocodedWaypoints {

    @JsonProperty("geocoder_status")
    public String geocoderStatus;

    @JsonProperty("place_id")
    public String placeId;

    @JsonProperty("types")
    public List<String> types;

    public String getGeocoderStatus() {
        return geocoderStatus;
    }

    public void setGeocoderStatus(String geocoderStatus) {
        this.geocoderStatus = geocoderStatus;
    }
}
