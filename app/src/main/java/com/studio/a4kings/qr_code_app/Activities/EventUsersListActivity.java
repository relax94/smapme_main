package com.studio.a4kings.qr_code_app.Activities;

import android.os.Bundle;
import android.view.View;

import com.studio.a4kings.qr_code_app.Adapters.SubscriberListAdapter;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.EventsService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.PeopleEnum;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;
import com.studio.a4kings.qr_code_app.Models.SubscriberModel;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DUX on 22.05.2016.
 */
public class EventUsersListActivity extends PeopleList {
    ArrayList<SubscriberModel> requestModels;
    EventsService eventsService;
    Integer eventId = -1;
    Boolean isCreator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        eventsService = new EventsService(Constants.SITE_URL);
        eventsService.setOperationToken(PrefsManager.getInstance(context).getFromCore(PrefsParam.TOKEN));
        requestModels = (ArrayList<SubscriberModel>) getIntent().getSerializableExtra(context.getString(R.string.request_list));
        eventId = getIntent().getIntExtra(context.getString(R.string.event_id), -1);
        isCreator = getIntent().getBooleanExtra(context.getString(R.string.is_creator), false);
        if(!isCreator)
            fillSubscribersForm(requestModels, null, PeopleEnum.ELSE);
        else fillSubscribersForm(requestModels, new SubscriberListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                removeUserFromEvent(requestModels.get(position).getUserId(), position);
            }
        }, PeopleEnum.EVENT_USERS);
    }

    private void removeUserFromEvent(String userId, final int position) {
        if (eventId != -1) {
            eventsService.removeUserFromEvent(eventId, userId, new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    if (response.body() != null) {
                        if (response.body().getCode() == 200)
                            requestModels.remove(position);
                        subscriberListAdapter.notifyItemRemoved(position);
                    }
                }
                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {

                }
            });
        }
    }
}
