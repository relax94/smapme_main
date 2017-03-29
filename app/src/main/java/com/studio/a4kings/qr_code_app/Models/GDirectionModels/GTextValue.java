package com.studio.a4kings.qr_code_app.Models.GDirectionModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Dmitry Pavlenko on 15.03.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GTextValue {
    @JsonProperty("text")
    private String text;
    @JsonProperty("value")
    private Double value;

}
