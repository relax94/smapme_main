package com.studio.a4kings.qr_code_app.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.studio.a4kings.qr_code_app.Adapters.SubscriberListAdapter;
import com.studio.a4kings.qr_code_app.Models.Enums.PeopleEnum;
import com.studio.a4kings.qr_code_app.Models.SubscriberModel;
import com.studio.a4kings.qr_code_app.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DUX on 22.05.2016.
 */
public class PeopleList extends AppCompatActivity {
    @Bind(R.id.list)
    RecyclerView recyclerView;
    SubscriberListAdapter subscriberListAdapter;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
    }

    public void fillSubscribersForm(final ArrayList<? extends SubscriberModel> subscriberModels, SubscriberListAdapter.OnItemClickListener onItemClickListener, PeopleEnum peopleEnum) {
        if (subscriberModels != null) {
            subscriberListAdapter = new SubscriberListAdapter(context, subscriberModels, peopleEnum);
            recyclerView.setAdapter(subscriberListAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            if (onItemClickListener != null)
                subscriberListAdapter.setOnAddClickListener(onItemClickListener);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
