package com.studio.a4kings.qr_code_app.Managers.Api.Services;

import com.facebook.GraphRequest;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.studio.a4kings.qr_code_app.Api.Interface.GServicesInterface;
import com.studio.a4kings.qr_code_app.Managers.Api.Interfaces.ApiService;
import com.studio.a4kings.qr_code_app.Managers.ApiManager;
import com.studio.a4kings.qr_code_app.Models.Response.GDirection;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Callback;

/**
 * Created by Dmitry Pavlenko on 14.03.2016.
 */
public class GService extends ApiManager implements ApiService {

    private GServicesInterface gServicesInterface;

    public GService(String baseUrl) {
        super(baseUrl);
        this.gServicesInterface = getApiInterface(GServicesInterface.class);
    }

    @Override
    public <T> T getApiInterface(Class<T> clazz) {
        return super.getApiInterface(clazz);
    }

    @Override
    public <T> void setApiManager(T apiManager) {

    }

    public void getDirectionsAsync(String key, LatLng start, LatLng end, Callback<GDirection> callback){
        this.gServicesInterface.getGmapsDirection(getDirectionServiceQuery(key, start, end, null)).enqueue(callback);
    }

    public void getDirectionsAsyncWithWaypoints(String key, LatLng start, LatLng end, List<LatLng> waypoints, Callback<GDirection> callback){
        this.gServicesInterface.getGmapsDirection(getDirectionServiceQuery(key, start, end, waypoints)).enqueue(callback);
    }


    private HashMap<String, String> getDirectionServiceQuery(String key, LatLng start, LatLng end, List<LatLng> waypoints){
        HashMap<String, String> query = new HashMap<>();
        query.put("origin", String.format(Locale.US,"%f,%f", start.latitude, start.longitude));
        query.put("destination", String.format(Locale.US,"%f,%f", end.latitude, end.longitude));
        if(waypoints != null && waypoints.size() > 0) {
            String way = "optimize:true";
            for(int i = 0; i < waypoints.size(); i++)
                way += String.format(Locale.US, "|%f,%f", waypoints.get(i).latitude, waypoints.get(i).longitude);
            query.put("waypoints", way);
        }
        query.put("sensor", "false");
        query.put("units", "metric");
        query.put("mode", "driving");
        query.put("key", key);
        return query;
    }
}
