package com.studio.a4kings.qr_code_app.Presenters.Interfaces;

import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.studio.a4kings.qr_code_app.Models.Events.BookingHotelsEvent;
import com.studio.a4kings.qr_code_app.Models.Events.FSQPlacesEvent;
import com.studio.a4kings.qr_code_app.Models.Events.TripRouteEvent;
import com.studio.a4kings.qr_code_app.Models.Events.YStationsEvent;
import com.studio.a4kings.qr_code_app.Models.FSQResponse;

/**
 * Created by Dmitry Pavlenko on 26.05.2016.
 */
public interface MapPresenterInterface {
    void initCoreModules();

    GoogleMap.SnapshotReadyCallback getSnapshotCallback();

    void saveTrip(String url);

    void createTrip(String ownerId, String eventId);

    void getTrip(String ownerId, String eventId);

    void onMapClickEvent(LatLng point);

    void onMapLongClickEvent(LatLng point);

    void mapSuccessLoaded();

    void checkIncomeMode(Intent intent);

    void handleBookingEvent(BookingHotelsEvent bookingHotelsEvent);

    void handleFSQEvent(FSQResponse fsqPlacesEvent);

    void handleYandexEvent(YStationsEvent yResponse);

    void handleTripRouteEvent(TripRouteEvent tripRouteEvent);

    void buildTripAction();

    void pickColor(FragmentManager supportFragmentManager, int defaultColor);


}
