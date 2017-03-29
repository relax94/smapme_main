package com.studio.a4kings.qr_code_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.studio.a4kings.qr_code_app.Managers.TripBuilder;
import com.studio.a4kings.qr_code_app.Models.GCluster.PlaceMapMarker;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.AppSettings;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DUX on 24.03.2016.
 */
public class DetailPlaceActivity extends AppCompatActivity {

    @Bind(R.id.backdrop)
    ImageView bigHeadImage;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.addEventeToTripButton)
    FloatingActionButton addEventToTripButton;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.description)
    TextView description;
    private PlaceMapMarker placeMapMarker;
    private Animation rotate_forward, rotate_backward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar.setTitle("Our text");
        toolbar.setTitle("Our text");

        rotate_forward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);
        /*setUpEvent(event);*/

        this.getDataFromIntent();
    }

    boolean pointInTrip;
    TripBuilder instance;

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("placeToShow")) {
            PlaceMapMarker mapMarker = new Gson().fromJson(intent.getStringExtra("placeToShow"), PlaceMapMarker.class);
            if (mapMarker != null) {
                this.placeMapMarker = mapMarker;
                this.bindModelForView();
                instance = TripBuilder.getInstance(AppSettings.GMAPS_KEY);
                pointInTrip = instance.isPointInTrip(this.placeMapMarker.getPosition());
                if (pointInTrip)
                    addEventToTripButton.startAnimation(rotate_forward);
            }
        }
    }

    private void bindModelForView() {
        collapsingToolbar.setTitle(this.placeMapMarker.getMarkerOptions().getSnippet());
        description.setText(placeMapMarker.getDetailedDescription());
        title.setText(placeMapMarker.getMarkerOptions().getSnippet());

    }

    @OnClick(R.id.addEventeToTripButton)
    public void onAddEventToTripClicked() {
        if (pointInTrip) {
            instance.removeTripPoint(this.placeMapMarker.getPosition());
            addEventToTripButton.startAnimation(rotate_backward);
            pointInTrip = false;
        }
        else {
            instance.addTripPoint(this.placeMapMarker.getPosition());
            addEventToTripButton.startAnimation(rotate_forward);
            pointInTrip = true;
        }
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
