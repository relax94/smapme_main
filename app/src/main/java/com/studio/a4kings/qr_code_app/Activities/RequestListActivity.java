package com.studio.a4kings.qr_code_app.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.studio.a4kings.qr_code_app.Adapters.SubscriberListAdapter;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.EventsService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.PeopleEnum;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.RequestModel;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;

import java.util.ArrayList;

import okhttp3.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DUX on 21.05.2016.
 */
public class RequestListActivity extends PeopleList {
    ArrayList<RequestModel> requestModels;
    EventsService eventsService;
    Integer eventId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventsService = new EventsService(Constants.SITE_URL);
        eventsService.setOperationToken(PrefsManager.getInstance(context).getFromCore(PrefsParam.TOKEN));
        eventId = getIntent().getIntExtra(context.getString(R.string.event_id), -1);
        requestModels = (ArrayList<RequestModel>) getIntent().getSerializableExtra(context.getString(R.string.request_list));
        fillSubscribersForm(requestModels, new SubscriberListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                addRequest(requestModels.get(position).getRequestId(), position);
            }
        }, PeopleEnum.REQUESTS);
    }

    private void addRequest(Integer userId, final int position) {
        if (eventId != -1) {
            eventsService.addUserToEvent(eventId, userId, new Callback<MainResponse>() {
                @Override
                public void onResponse(retrofit2.Call<MainResponse> call, Response<MainResponse> response) {
                    if (response.body() != null) {
                        if (response.body().getCode() == 200)
                            requestModels.remove(position);
                        subscriberListAdapter.notifyItemRemoved(position);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<MainResponse> call, Throwable t) {

                }

            });
        }
    }
}
