package com.studio.a4kings.qr_code_app.Fragments;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.a4kings.qr_code_app.Api.Interface.TravelEventsInterface;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.TravelEventsService;
import com.studio.a4kings.qr_code_app.Managers.ApiManager;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.Response.TravelEventResponse;
import com.studio.a4kings.qr_code_app.Models.TravelEventModel;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;
import java.io.IOException;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTravelEventFragment extends Fragment implements Callback<TravelEventResponse>{

    private TravelEventsService travelEventsService;

    @OnClick(R.id.sendEvent)
    public void sendEvent(View view) {
        TravelEventModel model = new TravelEventModel();
        model.setTitle("Поездка в Херсон");
        model.setDateStart("24/02/2016");
        model.setDateEnd("28/02/2016");
        model.setDescription("Поездка в Херсон");
        model.setUsersCount(4);
        model.setBudget(500);
        ArrayList<String> list = new ArrayList<String>() {{
            add("1");
            add("2");
        }};
        model.setCities(list);
        model.setCountry("1");
        this.travelEventsService.setOperationToken(PrefsManager.getInstance(this.getContext()).getFromCore(PrefsParam.TOKEN));
      /*  ApiManager.getInstance(Constants.SITE_URL).addTravelEventAsync(model,
                new Callback<MainResponse>() {
                    @Override
                    public void onResponse(Response<MainResponse> response) {
                        if (response.body() != null) {

                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                }
        );*/

       /* try {
            ApiManager.getInstance(Constants.SITE_URL).getEventsListAsync(this);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        try {
            this.travelEventsService.getUserEventsListAsync("fbc90e9b-11aa-49b6-bdb4-635c686067e5", this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_travel_event, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResponse(Call<TravelEventResponse> call, Response<TravelEventResponse> response) {
        if(response.body() != null){

        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.travelEventsService = new TravelEventsService(Constants.SITE_URL);
    }

    @Override
    public void onFailure(Call<TravelEventResponse> call,Throwable t) {
    }
}
