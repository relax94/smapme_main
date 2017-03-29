package com.studio.a4kings.qr_code_app.Models.GCluster;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.maps.android.clustering.ClusterItem;
import com.studio.a4kings.qr_code_app.Utils.Utils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Dmitry Pavlenko on 21.03.2016.
 */
public class PlaceMapMarker implements ClusterItem {

    //private MarkerOptions markerOptions;
    private Bitmap bitmap;
    private String snippetText;
    private Double lat;
    private Double lng;
    private String imageUrl;
    private String detailedDescription;
    private int markerType; /*
    0 - custom
    1 - fsq
    2 - booking
    3 - yandex
    */
    private HashMap<Integer, Integer> colors;

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public PlaceMapMarker(String snippetText, Double lat, Double lng, int markerType){
        this.snippetText = snippetText;
        this.lat = lat;
        this.lng = lng;
        this.markerType = markerType;
       // this.bitmap = BitmapDescriptorFactory.defaultMarker();
        this.setColorsStructure();
    }

    private void setColorsStructure(){
        this.colors = new HashMap<>();
        this.colors.put(0, Color.BLUE);
        this.colors.put(1, Color.RED);
        this.colors.put(2, Color.MAGENTA);
        this.colors.put(3, Color.GREEN);
    }


    public PlaceMapMarker(String snippetText,Double lat, Double lng, Bitmap icon, int markerType) {
        this(snippetText, lat, lng, markerType);

        int color = colors.get(markerType);
        if(icon != null) {
            Bitmap preferredIcon = Utils.getInstance().drawBackgroundForBitmap(icon, color);
            if (preferredIcon != null)
                this.bitmap = preferredIcon;
        }
        //this.makeMarkerOptions(bitmapDescriptor);
    }

   /* public PlaceMapMarker(String snippetText, LatLng coordinates, Bitmap iconDescriptor){
        this(snippetText, coordinates);

        if(iconDescriptor != null)
            this.bitmap = icon;
        this.makeMarkerOptions();
    }*/


    @Override
    public LatLng getPosition() {
        return new LatLng(this.lat, this.lng);
    }


    public MarkerOptions getMarkerOptions(){
        return new MarkerOptions()
                .icon(/*this.bitmap != null ? BitmapDescriptorFactory.fromBitmap(this.bitmap) : */BitmapDescriptorFactory.defaultMarker())
                .snippet(this.snippetText)
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(new LatLng(this.lat, this.lng));
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
