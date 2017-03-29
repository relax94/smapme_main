package com.studio.a4kings.qr_code_app.Activities;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.studio.a4kings.qr_code_app.Adapters.Map.MyInfoWindowAdapter;
import com.studio.a4kings.qr_code_app.Models.Events.BookingHotelsEvent;
import com.studio.a4kings.qr_code_app.Models.Events.FSQPlacesEvent;
import com.studio.a4kings.qr_code_app.Models.Events.TripRouteEvent;
import com.studio.a4kings.qr_code_app.Models.Events.YStationsEvent;
import com.studio.a4kings.qr_code_app.Presenters.Implementations.MapPresenter;
import com.studio.a4kings.qr_code_app.Presenters.Interfaces.MapView;
import com.studio.a4kings.qr_code_app.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Map extends FragmentActivity implements MapView, OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, SeekBar.OnSeekBarChangeListener {

    private GoogleMap mMap;
    private MapPresenter mapPresenter;
    private SupportMapFragment mapFragment;

    @Bind(R.id.tripButton)
    FloatingActionButton tripButton;
    @Bind(R.id.saveTripButton)
    FloatingActionButton saveTripButton;
    @Bind(R.id.colorPicker)
    Button colorPicker;
    @Bind(R.id.seekbar)
    SeekBar seekBar;
    @Bind(R.id.lineThickness)
    TextView lineThickness;
    @Bind(R.id.lineCustomizeBlock)
    LinearLayout lineCustomizeBlock;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @Bind(R.id.mapCoordinatorLayout)
    CoordinatorLayout mapCoordinatorLayout;


    @OnClick(R.id.colorPicker)
    public void pickColor() {
        mapPresenter.pickColor(getSupportFragmentManager(), getLineColor());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        this.mapPresenter = new MapPresenter(this);


/*
            setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            collapsingToolbar.setTitle("Our text");
            toolbar.setTitle("Our text");
        }*/
    }

    @Override
    public void initMap() {
        this.mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (this.mapFragment != null)
            mapFragment.getMapAsync(Map.this);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        this.bindGMapsEventsAndProps(map);
        this.mapPresenter.mapSuccessLoaded();
        this.mapPresenter.checkIncomeMode(getIntent());
    }

    @Override
    public void drawTrip(final PolylineOptions tripPolyLine) {
        mMap.addPolyline(tripPolyLine);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public GoogleMap getMap() {
        return this.mMap;
    }

    @Override
    public void moveCamera(LatLng location, int zoom) {
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                location, zoom));
    }

    @Override
    public void bindGMapsEventsAndProps(GoogleMap map) {
        this.mMap = map;
        this.mMap.clear();
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(this));
        seekBar.setOnSeekBarChangeListener(this);
    }


    @Override
    public void onMapClick(LatLng latLng) {
        this.mapPresenter.onMapClickEvent(latLng);
    }

    @OnClick(R.id.saveTripButton)
    public void onSaveTripButtonClicked() {
        this.snapshotCurrentMapImage();
    }

    @OnClick(R.id.tripButton)
    public void onTripButtonClicked() {
        this.mapPresenter.buildTripAction();
    }

    public void hideButtons() {
        saveTripButton.setVisibility(View.GONE);
        tripButton.setVisibility(View.GONE);
        lineCustomizeBlock.setVisibility(View.GONE);
    }

    @Override
    public int getLineColor() {
        return ((ColorDrawable) colorPicker.getBackground()).getColor();
    }

    @Override
    public void setLineColor(int color) {
        colorPicker.setBackgroundColor(color);
    }

    @Override
    public int getLineThickness() {
        return seekBar.getProgress() + 10;
    }

    @Override
    public void showSnackbarMessage(String text) {
         Snackbar.make(this.mapCoordinatorLayout, text, Snackbar.LENGTH_LONG).show();
    }

    public void snapshotCurrentMapImage() {
        if (this.mMap != null) {
            mMap.snapshot(this.mapPresenter.getSnapshotCallback());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()  == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        this.mapPresenter.onMapLongClickEvent(latLng);
    }


 /*   @org.greenrobot.eventbus.Subscribe
    public void onYandexNearestStationsEvenct(final YStationsEvent yStationsEvent) {
        mapPresenter.handleYandexEvent(yStationsEvent);
    }*/

    @org.greenrobot.eventbus.Subscribe
    public void onTripRouteEvent(final TripRouteEvent tripRouteEvent) {
        mapPresenter.handleTripRouteEvent(tripRouteEvent);
    }

    @org.greenrobot.eventbus.Subscribe
    public void onFSQPlacesResponseEvent(final FSQPlacesEvent placesEvent) {
       /* runOnUiThread(new Runnable() {
            @Override
            public void run() {*/
        mapPresenter.handleFSQEvent(placesEvent.getFsqResponse());

    }

  /*  @org.greenrobot.eventbus.Subscribe
    public void onBookingHotelEvent(BookingHotelsEvent event) {
        this.mapPresenter.handleBookingEvent(event);
    }*/


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        mMap.clear();
        super.onStop();
    }




    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        mMap.clear();

      /*  (SupportMapFragment) getSupportFragmentManager();
        ft.remove(mapFragment);
        ft.commit();*/
        System.gc();
        super.onDestroy();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        lineThickness.setText(String.valueOf(getLineThickness()));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}


