package com.studio.a4kings.qr_code_app.Managers;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.BookingService;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.FSQService;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.GService;
import com.studio.a4kings.qr_code_app.Managers.Listeners.BookingResponseListener;
import com.studio.a4kings.qr_code_app.Managers.Listeners.FSQResponseListener;
import com.studio.a4kings.qr_code_app.Managers.Listeners.GServiceResponseListener;
import com.studio.a4kings.qr_code_app.Models.BookingResponse;
import com.studio.a4kings.qr_code_app.Models.Callbacks.RRFCallback;
import com.studio.a4kings.qr_code_app.Models.Coords;
import com.studio.a4kings.qr_code_app.Models.Events.TripRouteEvent;
import com.studio.a4kings.qr_code_app.Models.FSQResponse;
import com.studio.a4kings.qr_code_app.Models.GDirectionModels.GLegs;
import com.studio.a4kings.qr_code_app.Models.GDirectionModels.GRoutes;
import com.studio.a4kings.qr_code_app.Models.GDirectionModels.GSteps;
import com.studio.a4kings.qr_code_app.Models.Response.GDirection;
import com.studio.a4kings.qr_code_app.Models.TripModel;
import com.studio.a4kings.qr_code_app.Utils.AppSettings;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dmitry Pavlenko on 15.03.2016.
 */
public class TripBuilder {

    private static TripBuilder instance;
    private final String API_KEY;
    private GService gService;
    private EventBus eventBus;
    private FSQService fsqService;
    private BookingService bookingService;
    private List<LatLng> tripPoints;





    TripBuilder(String key){
        this.API_KEY = key;
        this.gService = new GService(AppSettings.GOOGLE_MAPS_API_STRING);
        this.eventBus = EventBus.getDefault();
        this.fsqService = new FSQService(AppSettings.FSQ_API_STRING, AppSettings.FSQ_PUBLIC_KEY, AppSettings.FSQ_SECRET_KEY);
        this.bookingService = new BookingService(AppSettings.BOOKING_API_STRING);
        this.tripPoints = new ArrayList<>();
    }

    public static TripBuilder getInstance(String key){
        if(instance == null)
            instance = new TripBuilder(key);
        return instance;
    }



    public void buildOptionsForRoute(LatLng start, LatLng  end){
        gService.getDirectionsAsync(this.API_KEY, start, end, new GServiceResponseListener());
    }

    public void buildOptionsForRoute(LatLng start, LatLng  end, List<LatLng> waypoints, int lineWidth, int lineColor){
         lineWidth = lineWidth <= 0 ? 10 : lineWidth;
         lineColor = lineColor == 0 ? Color.BLUE : lineColor;
        gService.getDirectionsAsyncWithWaypoints(this.API_KEY, start, end, waypoints, new GServiceResponseListener(lineWidth, 30, lineColor));
    }


    public void buildOptionsForRoute(TripModel tripModel){
        if(tripModel != null) {
            List<LatLng> points = Coords.convertToLatLngList(tripModel.getTripPoints());
            if(points != null && points.size() >= 2) {
                crearTripPoints();
                this.tripPoints.addAll(points);
                int lineWidth = tripModel.getLineWidth() <= 0 ? 10 : tripModel.getLineWidth();
                int lineColor = tripModel.getLineColor() == 0 ? Color.BLUE : tripModel.getLineColor();
                gService.getDirectionsAsyncWithWaypoints(this.API_KEY, points.get(0), points.get(0), points, new GServiceResponseListener(lineWidth, 30, lineColor));
            }
        }
    }

    public void addTripPoint(LatLng point)
    {
        if(point != null)
            this.tripPoints.add(point);
    }

    public void buildOptionsForRoute(int lineWidth, int lineColor){
        this.buildOptionsForRoute(this.tripPoints.get(0), this.tripPoints.get(0), tripPoints, lineWidth, lineColor);
    }

    public void crearTripPoints(){
        this.tripPoints.clear();
    }

    public void removeTripPoint(LatLng point){
        if(tripPoints.size() > 0 && tripPoints.contains(point)){
            tripPoints.remove(point);
        }
    }


    public boolean isPointInTrip(LatLng point){
        return  tripPoints.contains(point);
    }

    public List<LatLng> getTripPoints()
    {
        return this.tripPoints;
    }

    public void loadFSQPlacesByCoords(LatLng location){
        this.fsqService.foursquareSearchPlacesByCoords(location.latitude, location.longitude, new FSQResponseListener());
    }

    public void loadFSQPlacesByCoordsWithOffset(LatLng location, double offsetLat, double offsetLot){
        this.fsqService.foursquareSearchPlacesByCoords(location.latitude + offsetLat, location.longitude + offsetLot, new FSQResponseListener());
    }

    public void loadBookingHotels(String cityName){
        this.bookingService.loadHotelsByCity(cityName, new BookingResponseListener());
    }
}
