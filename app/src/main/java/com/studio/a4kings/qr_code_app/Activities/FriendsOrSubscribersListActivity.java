package com.studio.a4kings.qr_code_app.Activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.studio.a4kings.qr_code_app.Adapters.SubscriberListAdapter;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.SubscribersService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.PeopleEnum;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.Enums.UserEnum;
import com.studio.a4kings.qr_code_app.Models.Response.AllUsersResponse;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;
import com.studio.a4kings.qr_code_app.Models.SubscriberModel;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DUX on 23.03.2016.
 */
public class FriendsOrSubscribersListActivity extends PeopleList implements Callback<AllUsersResponse> {

    SubscribersService subscribersService;
    ArrayList<SubscriberModel> subscriberModels;
    UserEnum userEnum;
    String currentUserId;
    String id = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subscribersService = new SubscribersService(Constants.SITE_URL);
        subscribersService.setOperationToken(PrefsManager.getInstance(context).getFromCore(PrefsParam.TOKEN));
        subscriberModels = (ArrayList<SubscriberModel>) getIntent().getSerializableExtra("list");
        id =  getIntent().getStringExtra("id");
        currentUserId = PrefsManager.getInstance(this).getFromCore(PrefsParam.USER_ID);
        if (subscriberModels != null)
            fill();
        else {
            userEnum = (UserEnum) getIntent().getSerializableExtra(context.getString(R.string.sub_enum));
            if (userEnum != null && userEnum.equals(UserEnum.MY)) {
                subscribersService.getAllUserFriends(this);
            } else {
                subscribersService.getAllPeople(this);
            }
        }
    }


    @Override
    public void onResponse(Call<AllUsersResponse> call, Response<AllUsersResponse> response) {
        if (response.body() != null) {
            subscriberModels = response.body().getModel();
            fill();
        }
    }

    private void fill() {
        if(id == null || id.equals(currentUserId))
            fillSubscribersForm(subscriberModels, onItemClickListener, PeopleEnum.SUBSCRIBERS);
        else  fillSubscribersForm(subscriberModels, onItemClickListener, PeopleEnum.ELSE);
    }

    @Override
    public void onFailure(Call<AllUsersResponse> call, Throwable t) {

    }

    SubscriberListAdapter.OnItemClickListener onItemClickListener = new SubscriberListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if(!subscriberModels.get(position).getIsSubscriber())
                addToFriends(subscriberModels.get(position).getUserId(), position);
            else removeFromFriends(subscriberModels.get(position).getUserId(), position);
        }
    };

    public void addToFriends(String friendId, final Integer position) {
        subscribersService.subscribe(friendId, new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.body() != null)
                    if (response.body().getCode() == 200) {
                        subscriberModels.get(position).setIsSubscriber(true);
                        subscriberListAdapter.notifyItemChanged(position);
                    }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

            }
        });
    }
    public void removeFromFriends(String friendId, final Integer position) {
        subscribersService.deleteSubscriber(friendId, new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.body() != null)
                    if (response.body().getCode() == 200) {
                        subscriberModels.get(position).setIsSubscriber(false);
                        subscriberListAdapter.notifyItemChanged(position);
                    }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

            }
        });
    }

}
