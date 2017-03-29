package com.studio.a4kings.qr_code_app.Models.GDirectionModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Dmitry Pavlenko on 15.03.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GSteps {

    @JsonProperty("distance")
    private GTextValue distance;
    @JsonProperty("duration")
    private GTextValue duration;
    @JsonProperty("end_location")
    private GLocation endLocation;
    @JsonProperty("start_location")
    private GLocation startLocation;
    @JsonProperty("html_instructions")
    private String htmlInstructions;
    @JsonProperty("travel_mode")
    private String travelMode;
    @JsonProperty("maneuver")
    private String maneuver;
    @JsonProperty("polyline")
    private GPolyline polyline;

    public GTextValue getDistance() {
        return distance;
    }

    public GTextValue getDuration() {
        return duration;
    }

    public GLocation getEndLocation() {
        return endLocation;
    }

    public GLocation getStartLocation() {
        return startLocation;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public String getManeuver() {
        return maneuver;
    }

    public GPolyline getPolyline() {
        return polyline;
    }
}
