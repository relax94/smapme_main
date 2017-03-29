package com.studio.a4kings.qr_code_app.Models.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.studio.a4kings.qr_code_app.Models.Yandex.YPagination;
import com.studio.a4kings.qr_code_app.Models.Yandex.YStation;

import java.util.List;

/**
 * Created by Dmitry Pavlenko on 24.03.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class YResponse {

    @JsonProperty("pagination")
    private YPagination pagination;
    @JsonProperty("stations")
    private List<YStation> stations;

    public YPagination getPagination() {
        return pagination;
    }

    public void setPagination(YPagination pagination) {
        this.pagination = pagination;
    }

    public List<YStation> getStations() {
        return stations;
    }

    public void setStations(List<YStation> stations) {
        this.stations = stations;
    }
}
