package com.studio.a4kings.qr_code_app.Models.GDirectionModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Dmitry Pavlenko on 15.03.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GRoutes {
    @JsonProperty("legs")
    private List<GLegs> legs;

    public List<GLegs> getLegs() {
        return legs;
    }
}
