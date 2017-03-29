package com.studio.a4kings.qr_code_app.Models;

import com.google.android.gms.maps.model.LatLng;
import com.studio.a4kings.qr_code_app.Utils.Utils;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.internal.Util;

/**
 * Created by Dmitry Pavlenko on 02.01.2016.
 */
public class MapMarker /*extends RealmObject */{
    private String title;
    private String description;
    private String snippet;
    private int markerDrawable;
    private String markerIconUrl;
    private double longitude;

    public Member getOwner() {
        return owner;
    }

    public void setOwner(Member owner) {
        this.owner = owner;
    }

    private Member owner;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    private double latitude;

    @PrimaryKey
    private String recordId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public int getMarkerDrawable() {
        return markerDrawable;
    }

    public void setMarkerDrawable(int markerDrawable) {
        this.markerDrawable = markerDrawable;
    }

    public String getMarkerIconUrl() {
        return markerIconUrl;
    }

    public void setMarkerIconUrl(String markerIconUrl) {
        this.markerIconUrl = markerIconUrl;
    }

    public MapMarker(){
        this.recordId = Utils.getInstance().getCurrentTimestamp();
    }

    public MapMarker(String title, String description)
    {
        this.recordId = Utils.getInstance().getCurrentTimestamp();
        this.title = title;
        this.description = description;
    }

    public MapMarker(String title, String description, int drawableIcon, String markerIconUrl){
        this(title, description);
        this.markerDrawable = drawableIcon;
        this.markerIconUrl = markerIconUrl;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
