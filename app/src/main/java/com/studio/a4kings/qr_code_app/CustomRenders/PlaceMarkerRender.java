package com.studio.a4kings.qr_code_app.CustomRenders;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.studio.a4kings.qr_code_app.Activities.DetailPlaceActivity;
import com.studio.a4kings.qr_code_app.Models.GCluster.PlaceMapMarker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dmitry Pavlenko on 21.03.2016.
 */
public class PlaceMarkerRender extends DefaultClusterRenderer<PlaceMapMarker> /*implements ClusterManager.OnClusterItemInfoWindowClickListener<PlaceMapMarker>*/ {

    public Context context;
    public List<LatLng> placeMapMarkers;

   /* @Override
    public void onClusterItemInfoWindowClick(PlaceMapMarker placeMapMarker) {
        //Log.d("CLUSTERITEM", placeMapMarker.getMarkerOptions().getSnippet());
        //this.placeMapMarkers.add(placeMapMarker.getPosition());
        this.goToDetailPlacePage(placeMapMarker);
    }*/

    public PlaceMarkerRender(Context mcontext, GoogleMap map, ClusterManager<PlaceMapMarker> clusterManager) {
        super(mcontext, map, clusterManager);
        context = mcontext;
        placeMapMarkers = new ArrayList<>();
    }


    @Override
    protected void onBeforeClusterItemRendered(PlaceMapMarker item, MarkerOptions markerOptions) {

        markerOptions.icon(item.getMarkerOptions().getIcon());
        markerOptions.snippet(item.getMarkerOptions().getSnippet());
        //  super.onBeforeClusterItemRendered(item, markerOptions);
    }

    public List<LatLng> getPlaceMapMarkers() {
        return placeMapMarkers;
    }

   /* @Override
    public void onClusterItemInfoWindowClick(PlaceMapMarker placeMapMarker) {
        //Log.d("CLUSTERITEM", placeMapMarker.getMarkerOptions().getSnippet());
        //this.placeMapMarkers.add(placeMapMarker.getPosition());
       // this.goToDetailPlacePage(placeMapMarker);
    }*/

    /*private void goToDetailPlacePage(PlaceMapMarker placeMapMarker) {
        Intent placeIntent = new Intent(this.context.getApplicationContext(), DetailPlaceActivity.class);
        placeIntent.putExtra("placeToShow", placeMapMarker.toString());
        this.context.startActivity(placeIntent);
    }*/


}
