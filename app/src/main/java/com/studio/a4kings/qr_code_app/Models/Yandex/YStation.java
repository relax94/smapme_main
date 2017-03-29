package com.studio.a4kings.qr_code_app.Models.Yandex;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Dmitry Pavlenko on 24.03.2016.
 */

@JsonIgnoreProperties(ignoreUnknown =  true)
public class YStation {

    @JsonProperty("distance")
    private double distance;
    @JsonProperty("code")
    private String code;
    @JsonProperty("title")
    private String title;
    @JsonProperty("station_type")
    private String stationType;
    @JsonProperty("popular_title")
    private String popularTitle;
    @JsonProperty("short_title")
    private String shortTitle;
    @JsonProperty("transport_type")
    private String transportType;
    @JsonProperty("lat")
    private double lat;
    @JsonProperty("lng")
    private double lng;
    @JsonProperty("type")
    private String type;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public String getPopularTitle() {
        return popularTitle;
    }

    public void setPopularTitle(String popularTitle) {
        this.popularTitle = popularTitle;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
