package com.studio.a4kings.qr_code_app.Presenters.Interfaces;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by Dmitry Pavlenko on 26.05.2016.
 */
public interface MapView {

    void initMap();

    void snapshotCurrentMapImage();

    void drawTrip(PolylineOptions tripPolyLine);

    Context getContext();

    GoogleMap getMap();

    void moveCamera(LatLng location, int zoom);

    void bindGMapsEventsAndProps(GoogleMap map);

    void hideButtons();

    int getLineColor();

    void setLineColor(int color);

    int getLineThickness();

    void showSnackbarMessage(String text);
}
