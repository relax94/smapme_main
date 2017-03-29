package com.studio.a4kings.qr_code_app.Models.FSQModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Dmitry Pavlenko on 15.03.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FSQResult {
    @JsonProperty("confident")
    private String confident;

    @JsonProperty("venues")
    private List<FSQVenue> venues;

    public String getConfident() {
        return confident;
    }

    public void setConfident(String confident) {
        this.confident = confident;
    }

    public List<FSQVenue> getVenues() {
        return venues;
    }

    public void setVenues(List<FSQVenue> venues) {
        this.venues = venues;
    }
}
