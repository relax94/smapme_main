package com.studio.a4kings.qr_code_app.Adapters.Map;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Dmitry Pavlenko on 11.01.2016.
 */
public class LocationProvider implements LocationListener {

    private LocationManager locationManager;

    public LocationProvider(Context context){
        if(context != null)
            this.locationManager = (LocationManager)context.getSystemService(context.LOCATION_SERVICE);
    }

    public LatLng getCurrentLocation(){
        try {
            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();
            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);
            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                // Getting latitude of the current location
                double latitude = location.getLatitude();
                // Getting longitude of the current location
                double longitude = location.getLongitude();
                // Creating a LatLng object for the current location
                LatLng latLng = new LatLng(latitude, longitude);

                return latLng;
            }
            return null;
        }
        catch (SecurityException ex){
            return null;
        }

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
