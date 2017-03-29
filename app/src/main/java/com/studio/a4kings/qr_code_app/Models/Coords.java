package com.studio.a4kings.qr_code_app.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Pavlenko on 30.05.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Coords {
    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("latitude")
    private double latitude;

    public Coords(){}

    public Coords(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Coords(LatLng latLng) {
        this.longitude = latLng.longitude;
        this.latitude = latLng.latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public LatLng convertToLatLng(){
        return new LatLng(this.latitude, this.longitude);
    }

    public static List<Coords> convertList(List<LatLng> list){
        List<Coords> coordses = new ArrayList<>();
        for(LatLng latLng : list)
            coordses.add(new Coords(latLng));
        return coordses;
    }

    public static List<LatLng> convertToLatLngList(List<Coords> coordses){
        List<LatLng> latLngList = new ArrayList<>();
        for(Coords coord : coordses)
            latLngList.add(coord.convertToLatLng());
        return latLngList;
    }
}
