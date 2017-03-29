package com.studio.a4kings.qr_code_app.Models.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.studio.a4kings.qr_code_app.Models.GDirectionModels.GRoutes;
import com.studio.a4kings.qr_code_app.Models.GDirectionModels.GeocodedWaypoints;

import java.util.List;

/**
 * Created by Dmitry Pavlenko on 13.03.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class GDirection {

    @JsonProperty("geocoded_waypoints")
    public List<GeocodedWaypoints> geocodedWaypoints;

    @JsonProperty("routes")
    public  List<GRoutes> routes;

}
