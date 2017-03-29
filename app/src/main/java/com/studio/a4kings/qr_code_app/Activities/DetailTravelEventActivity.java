package com.studio.a4kings.qr_code_app.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


import com.studio.a4kings.qr_code_app.Models.TravelEventModel;
import com.studio.a4kings.qr_code_app.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DetailTravelEventActivity extends AppCompatActivity {

    @Bind(R.id.backdrop)ImageView imageView;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)CollapsingToolbarLayout collapsingToolbar;
    TravelEventModel travelEventModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_event_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        travelEventModel =  (TravelEventModel) intent.getSerializableExtra("event");
        final String cheeseName = "Some text";
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar.setTitle(cheeseName);
        imageView.setImageResource(R.drawable.profile_img);
        /*Picasso.with(this).load(R.drawable.profile_img).fit().centerCrop().into(imageView);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
