package com.studio.a4kings.qr_code_app.Models.GDirectionModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Dmitry Pavlenko on 15.03.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GLegs {

    @JsonProperty("distance")
    private GTextValue distance;
    @JsonProperty("duration")
    private GTextValue duration;
    @JsonProperty("end_address")
    private String endAddress;
    @JsonProperty("end_location")
    private GLocation endLocation;
    @JsonProperty("start_address")
    private String startAddress;
    @JsonProperty("start_location")
    private GLocation startLocation;

    @JsonProperty("steps")
    private List<GSteps> steps;

    public List<GSteps> getSteps() {
        return steps;
    }

    public GLocation getStartLocation() {
        return startLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public GLocation getEndLocation() {
        return endLocation;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public GTextValue getDuration() {
        return duration;
    }

    public GTextValue getDistance() {
        return distance;
    }
}
