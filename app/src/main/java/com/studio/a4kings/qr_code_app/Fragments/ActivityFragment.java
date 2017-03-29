package com.studio.a4kings.qr_code_app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.a4kings.qr_code_app.Managers.Api.Services.ActivityService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Activity.ActivityResponse;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DUX on 13.06.2016.
 */
public class ActivityFragment extends Fragment {

    ActivityService activityService;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        activityService = new ActivityService(Constants.SITE_URL);
        activityService.setOperationToken(PrefsManager.getInstance(this.getContext()).getFromCore(PrefsParam.TOKEN));
        activityService.getAllActivities(new Callback<ActivityResponse>() {
            @Override
            public void onResponse(Call<ActivityResponse> call, Response<ActivityResponse> response) {

            }

            @Override
            public void onFailure(Call<ActivityResponse> call, Throwable t) {

            }
        });
        return view;
    }

}
