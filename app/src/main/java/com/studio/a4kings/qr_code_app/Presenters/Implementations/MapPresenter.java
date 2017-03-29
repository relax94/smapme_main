package com.studio.a4kings.qr_code_app.Presenters.Implementations;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.pavelsikun.vintagechroma.ChromaDialog;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.OnColorSelectedListener;
import com.pavelsikun.vintagechroma.colormode.ColorMode;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.studio.a4kings.qr_code_app.Activities.AddEventActivity;
import com.studio.a4kings.qr_code_app.Adapters.Map.LocationProvider;
import com.studio.a4kings.qr_code_app.CustomRenders.PlaceMarkerRender;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.EventsService;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.TripBuilderService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Managers.TripBuilder;
import com.studio.a4kings.qr_code_app.Models.Coords;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventRequestModel;
import com.studio.a4kings.qr_code_app.Models.Events.BookingHotelsEvent;
import com.studio.a4kings.qr_code_app.Models.Events.TripRouteEvent;
import com.studio.a4kings.qr_code_app.Models.Events.YStationsEvent;
import com.studio.a4kings.qr_code_app.Models.FSQModels.FSQIcon;
import com.studio.a4kings.qr_code_app.Models.FSQModels.FSQVenue;
import com.studio.a4kings.qr_code_app.Models.FSQResponse;
import com.studio.a4kings.qr_code_app.Models.GCluster.PlaceMapMarker;
import com.studio.a4kings.qr_code_app.Models.GDirectionModels.GSteps;
import com.studio.a4kings.qr_code_app.Models.MapMarker;
import com.studio.a4kings.qr_code_app.Models.Migration.VkMigrationPage;
import com.studio.a4kings.qr_code_app.Models.Response.CreateEventResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ServerResponse;
import com.studio.a4kings.qr_code_app.Models.Response.SnapshotUploadResponse;
import com.studio.a4kings.qr_code_app.Models.Response.TripResponse;
import com.studio.a4kings.qr_code_app.Models.Response.YResponse;
import com.studio.a4kings.qr_code_app.Models.TripModel;
import com.studio.a4kings.qr_code_app.Models.Yandex.YStation;
import com.studio.a4kings.qr_code_app.Presenters.Interfaces.MapPresenterInterface;
import com.studio.a4kings.qr_code_app.Presenters.Interfaces.MapView;
import com.studio.a4kings.qr_code_app.Utils.AppSettings;
import com.studio.a4kings.qr_code_app.Utils.Constants;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dmitry Pavlenko on 26.05.2016.
 */
public class MapPresenter implements MapPresenterInterface {

    private MapView mapView;
    private List<LatLng> points;
    private List<GSteps> gStepses;
    private TripBuilder tripBuilder;
    private TripBuilderService tripBuilderService;
    private TripModel tripModel;
    private ClusterManager<PlaceMapMarker> mClusterManager;
    private PlaceMarkerRender placeMarkerRender;
    private LatLng currentLocationPoint;
    private final int MAP_ZOOM_VAL = 16;
    public MapPresenter(MapView mapView) {
        this.mapView = mapView;
        if (this.mapView != null)
            this.mapView.initMap();
    }


    @Override
    public void initCoreModules() {
        this.tripBuilder = TripBuilder.getInstance(AppSettings.GMAPS_KEY);
        this.tripBuilderService = new TripBuilderService(AppSettings.TRIP_BACKEND);

        this.tripBuilderService.vkMigration("hedonism_2016", new Callback<ServerResponse<VkMigrationPage>>() {
            @Override
            public void onResponse(Call<ServerResponse<VkMigrationPage>> call, Response<ServerResponse<VkMigrationPage>> response) {
                if(response.body() != null) {
                    ServerResponse<VkMigrationPage> serverResponse = response.body();
                    if(serverResponse.isState()){
                        VkMigrationPage vkMigrationPage = serverResponse.getR();
                        if(vkMigrationPage != null){
                            EventRequestModel eventRequestModel = new EventRequestModel();
                            EventsService eventsService =new EventsService(Constants.SITE_URL);
                            String token = PrefsManager.getInstance(mapView.getContext()).get(PrefsParam.TOKEN);
                            if(!token.isEmpty()){
                                eventRequestModel = VkMigrationPage.convertToEventRequest(vkMigrationPage);
                                eventsService.setOperationToken(token);
                                eventsService.createEvent(eventRequestModel, new Callback<CreateEventResponse>() {
                                    @Override
                                    public void onResponse(Call<CreateEventResponse> call, Response<CreateEventResponse> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<CreateEventResponse> call, Throwable t) {

                                    }
                                });
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<VkMigrationPage>> call, Throwable t) {

            }
        });

    }

    @Override
    public GoogleMap.SnapshotReadyCallback getSnapshotCallback() {
        showProgressDialog();
        return this.snapshotCallback;
    }

    @Override
    public void saveTrip(String url) {
       /* new Thread(new Runnable() {
            @Override
            public void run() {
       */
        tripModel.setSnapShotUrl(url);
        tripBuilderService.saveTrip(tripModel, new Callback<TripResponse>() {
            @Override
            public void onResponse(Call<TripResponse> call, Response<TripResponse> response) {
                if (response.body() != null && response.body().isState()) {
                    mapView.showSnackbarMessage("Save Trip Success");
                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<TripResponse> call, Throwable t) {

            }
        });
        // }
        //   }).start();
    }

    @Override
    public void createTrip(String ownerId, String eventId) {
        tripModel = new TripModel();
        tripModel.setOwnerId(ownerId);
        tripModel.setEventId(eventId);

        this.getTrip(ownerId, eventId);
    }

    @Override
    public void getTrip(String ownerId, String eventId) {
        showProgressDialog();
        this.tripBuilderService.getTrip(ownerId, eventId, new Callback<TripResponse>() {
            @Override
            public void onResponse(Call<TripResponse> call, Response<TripResponse> response) {
                if (response != null && response.body() != null) {
                    TripResponse tripResponse = response.body();
                    TripModel tripR = tripResponse.getR();
                    if (tripR != null && tripR.getTripPoints() != null) {
                        if (tripResponse.isState() && tripR != null ) {
                                tripBuilder.buildOptionsForRoute(tripR);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TripResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onMapClickEvent(LatLng point) {
        this.tripBuilder.loadFSQPlacesByCoords(point);
    }

    @Override
    public void onMapLongClickEvent(LatLng point) {
        final MarkerOptions markerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.house_flag))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(point);
        final MapMarker mapMarker = new MapMarker();

        final boolean[] isPositiveAnswer = {false};

        this.openCustomAddEventForm(point.latitude, point.longitude);
    }


    private void checkGPSConnectivity(){
        LocationManager lm = (LocationManager) mapView.getContext().getSystemService(Context.LOCATION_SERVICE);
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            // Build the alert dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mapView.getContext());
            builder.setTitle("Location Services Not Active");
            builder.setMessage("Please enable Location Services and GPS");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Show location settings when the user acknowledges the alert dialog
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mapView.getContext().startActivity(intent);
                }
            });
            Dialog alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
    }

    @Override
    public void mapSuccessLoaded() {
        this.initCoreModules();

        this.checkGPSConnectivity();

        LocationProvider locationProvider = new LocationProvider(this.mapView.getContext());
        this.currentLocationPoint = locationProvider.getCurrentLocation();
        if (this.currentLocationPoint == null) {
            this.currentLocationPoint = new LatLng(50.266582, 28.6537633);
        }



        GoogleMap map = this.mapView.getMap();
        if (map != null) {
            this.mClusterManager = new ClusterManager<PlaceMapMarker>(this.mapView.getContext(), map);

            map.setOnMarkerClickListener(mClusterManager);
            this.placeMarkerRender = new PlaceMarkerRender(this.mapView.getContext(), map, mClusterManager);
            this.mClusterManager.setRenderer(placeMarkerRender);
           // this.mClusterManager.setOnClusterItemInfoWindowClickListener((ClusterManager.OnClusterItemInfoWindowClickListener<PlaceMapMarker>) placeMarkerRender);
            map.setOnInfoWindowClickListener(mClusterManager);
        }
    }

    @Override
    public void checkIncomeMode(Intent invokeIntent) {
        if (invokeIntent.hasExtra("requestCode")) {
            int requestCode = invokeIntent.getIntExtra("requestCode", -1);
            // TODO CHECK MODES FOR MAP BY REQUEST CODE
            String ownerId = invokeIntent.getStringExtra("ownerId");
            String eventId = invokeIntent.getStringExtra("eventId");

            if (requestCode == 100) {
                this.createTrip(ownerId, eventId);
            } else if (requestCode == 200) {
                mapView.hideButtons();
                this.getTrip(ownerId, eventId);
            }
            else if(requestCode == 300)
                mapView.hideButtons();
        }
    }

    public void openCustomAddEventForm(double lat, double lng) {
        Intent addForm = new Intent(this.mapView.getContext(), AddEventActivity.class);
        addForm.putExtra("lat", lat);
        addForm.putExtra("lng", lng);
        this.mapView.getContext().startActivity(addForm);
    }

    @Override
    public void handleBookingEvent(BookingHotelsEvent bookingHotelsEvent) {
       /* Bitmap hotelIcon = BitmapFactory.decodeResource(getResources(),
                R.drawable.booking_hotel);
        if (event.getHotels().size() > 0) {
            for (BookingModel hotel : event.getHotels()) {
                PlaceMapMarker hotelMapMarker = new PlaceMapMarker(hotel.getName(), hotel.getLat(), hotel.getLon(), hotelIcon, 2);
                mClusterManager.addItem(hotelMapMarker);
            }
        }*/
    }


    @Override
    public void handleFSQEvent(FSQResponse placesEvent) {
        if (placesEvent != null) {
            for (final FSQVenue venue : placesEvent.getResponse().getVenues()) {
                if (venue != null && venue.getCategories() != null && venue.getCategories().size() > 0) {
                    FSQIcon fsqIcon = venue.getCategories().get(0).getIcon();
                    PlaceMapMarker placeMapMarker = new PlaceMapMarker(venue.getName(),
                            venue.getLocation().getLat(), venue.getLocation().getLng(), null, 1);
                     //placeMapMarker.setImageUrl(venue.ge);
                     mClusterManager.addItem(placeMapMarker);
                    //Change here
                  /*  Picasso.with(this.mapView.getContext())
                            .load(String.format("%s64%s", fsqIcon.getPrefix(), fsqIcon.getSuffix()))
                            .into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                               /* }

                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {

                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {

                                }
                            });*/
                }
/*Ion.with(this).load(String.format("%s64%s", fsqIcon.getPrefix(), fsqIcon.getSuffix())).withBitmap().asBitmap()
                        .setCallback(new FutureCallback<Bitmap>() {
                            @Override
                            public void onCompleted(Exception e, Bitmap result) {

                            }
                        });*/


            }
        }
        this.mapView.moveCamera(this.currentLocationPoint, MAP_ZOOM_VAL - 1);
    }

    @Override
    public void handleYandexEvent(YStationsEvent yStationsEvent) {
        if (yStationsEvent != null) {
            YResponse yResponse = yStationsEvent.getResponse();
            if (yResponse != null) {
                for (YStation yStation : yResponse.getStations()) {
                    PlaceMapMarker placeMapMarker = new PlaceMapMarker(yStation.getTitle(), yStation.getLat(), yStation.getLng(), 3);
                    mClusterManager.addItem(placeMapMarker);
                }
            }
        }
    }

    @Override
    public void handleTripRouteEvent(TripRouteEvent tripRouteEvent) {
        if (tripRouteEvent != null && tripRouteEvent.getPolyline() != null) {
            this.mapView.drawTrip(tripRouteEvent.getPolyline());

            this.gStepses = new ArrayList<>();
            this.gStepses.addAll(tripRouteEvent.getgDirection());
            if(tripModel == null)
                tripModel = new TripModel();
            tripModel.setTripPoints(Coords.convertList(tripBuilder.getTripPoints()));

            this.hideProgressDialog();
            this.onMapClickEvent(this.currentLocationPoint);
            this.mapView.moveCamera(this.currentLocationPoint, MAP_ZOOM_VAL);

            this.mapView.showSnackbarMessage("We loaded your last trip");
            this.mapView.showSnackbarMessage(String.format("Your current location id %s,%s", this.currentLocationPoint.latitude, this.currentLocationPoint.longitude));
            this.mapView.showSnackbarMessage("And we loaded some places nearby");
        }
    }

    @Override
    public void buildTripAction() {
        tripModel.setLineColor(mapView.getLineColor());
        tripModel.setLineWidth(mapView.getLineThickness());
        this.tripBuilder.buildOptionsForRoute(mapView.getLineThickness(), mapView.getLineColor());
    }

    @Override
    public void pickColor(FragmentManager supportFragmentManager, int defaultColor) {
        new ChromaDialog.Builder()
                .initialColor(defaultColor)
                .colorMode(ColorMode.ARGB)
                .indicatorMode(IndicatorMode.HEX)
                .onColorSelected(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(@ColorInt int color) {
                        mapView.setLineColor(color);
                    }
                })
                .create()
                .show(supportFragmentManager, "ChromaDialog");
    }



    public void showProgressDialog(){
        pd = new ProgressDialog(this.mapView.getContext());
        pd.setTitle("Wait");
        pd.show();
    }

    public void hideProgressDialog(){
        pd.dismiss();
    }

    ProgressDialog pd;
    GoogleMap.SnapshotReadyCallback snapshotCallback = new GoogleMap.SnapshotReadyCallback() {
        Bitmap bitmap;

        @Override
        public void onSnapshotReady(Bitmap snapshot) {
            bitmap = snapshot;
            try {
                FileOutputStream out = new FileOutputStream("/mnt/sdcard/Download/TeleSensors.png");
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                tripBuilderService.uploadTripSnapshot("/mnt/sdcard/Download/TeleSensors.png", new Callback<SnapshotUploadResponse>() {
                    @Override
                    public void onResponse(Call<SnapshotUploadResponse> call, Response<SnapshotUploadResponse> response) {
                        if (response.body() != null)
                            if (response.body().isState())
                                saveTrip(response.body().getUrl());
                    }

                    @Override
                    public void onFailure(Call<SnapshotUploadResponse> call, Throwable t) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
