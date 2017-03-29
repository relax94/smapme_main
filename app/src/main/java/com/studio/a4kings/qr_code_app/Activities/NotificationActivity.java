package com.studio.a4kings.qr_code_app.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.studio.a4kings.qr_code_app.Adapters.NotificationAdapter;
import com.studio.a4kings.qr_code_app.Interfaces.NotifyItemClickListener;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.EventsService;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.WallService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventDetailResponse;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventFullResponse;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventResponseModel;
import com.studio.a4kings.qr_code_app.Models.Notification.Notification;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;
import com.studio.a4kings.qr_code_app.Models.Response.WallResponse;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.AppSettings;
import com.studio.a4kings.qr_code_app.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity implements NotifyItemClickListener {

    private WallService wallService;

    @Bind(R.id.notificationsRecycle)
    RecyclerView notificationRecycle;
    NotificationAdapter notificationAdapter;
    List<Notification> notifications;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        ButterKnife.bind(this);

        if(getSupportActionBar() != null)
            getSupportActionBar().setHomeButtonEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        notifications = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(this, notifications, this);
        notificationRecycle.setItemAnimator(new DefaultItemAnimator());
        notificationRecycle.setLayoutManager(new LinearLayoutManager(this));
        notificationRecycle.setAdapter(notificationAdapter);

        this.initNotificationWall();
    }

    private void initNotificationWall(){
        String userId = PrefsManager.getInstance(this).get(PrefsParam.USER_ID);
        if(!userId.isEmpty()) {
            this.wallService = new WallService(AppSettings.TRIP_BACKEND);
            this.wallService.getAllNotification(userId, new Callback<WallResponse<List<Notification>>>() {
                @Override
                public void onResponse(Call<WallResponse<List<Notification>>> call, Response<WallResponse<List<Notification>>> response) {
                    WallResponse<List<Notification>> wallResponse = response.body();
                    if(wallResponse != null && wallResponse.isState()){
                        List<Notification> notificationList = wallResponse.getR();
                        if(notificationList != null && notificationList.size() > 0)
                        {
                            notifications.addAll(notificationList);
                            notificationAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onFailure(Call<WallResponse<List<Notification>>> call, Throwable t) {

                }
            });
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void getEventById(String eventId){
        Intent transitionIntent = new Intent(context, DetailEventActivity.class);
        transitionIntent.putExtra("eventId", Integer.parseInt(eventId));
        startActivity(transitionIntent);
        /*String accessToken = PrefsManager.getInstance(this).get(PrefsParam.TOKEN);
        if(!accessToken.isEmpty()){
            EventsService eventsService = new EventsService(Constants.SITE_URL);
            eventsService.setOperationToken(accessToken);
            eventsService.getEventById(Integer.parseInt(eventId), new Callback<EventFullResponse>() {
                @Override
                public void onResponse(Call<EventFullResponse> call, Response<EventFullResponse> response) {
                    EventFullResponse eventFullResponse = response.body();
                    if(eventFullResponse != null){
                        EventResponseModel responseModel = eventFullResponse.getModel();
                        if(responseModel != null){
                            Intent transitionIntent = new Intent(context, DetailEventActivity.class);
                            transitionIntent.putExtra(context.getString(R.string.event), responseModel);
//                            ImageView placeImage = (ImageView) v.findViewById(R.id.eventImg);
//                            Drawable drawable = placeImage.getDrawable();
//                            if (drawable != null) {
//                                Bitmap bitmap = ((BitmapDrawable) placeImage.getDrawable()).getBitmap();
//                                if (bitmap != null)
//                                    transitionIntent.putExtra(context.getString(R.string.eventImg), bitmap);
//                            }
                            startActivity(transitionIntent);
                        }
                    }
                }

                @Override
                public void onFailure(Call<EventFullResponse> call, Throwable t) {

                }
            });
        }*/
    }

    @Override
    public void onItemHolderClicked(int position) {
        getEventById(notifications.get(position).getCoreId());
    }
}
