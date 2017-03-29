package com.studio.a4kings.qr_code_app.Managers.Listeners;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.model.PolylineOptions;

import com.studio.a4kings.qr_code_app.Models.Events.TripRouteEvent;
import com.studio.a4kings.qr_code_app.Models.GDirectionModels.GLegs;
import com.studio.a4kings.qr_code_app.Models.GDirectionModels.GRoutes;
import com.studio.a4kings.qr_code_app.Models.GDirectionModels.GSteps;
import com.studio.a4kings.qr_code_app.Models.Response.GDirection;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dmitry Pavlenko on 16.03.2016.
 */
public class GServiceResponseListener implements Callback<GDirection>  {

    private int lineWidth;
    private int zIndex;
    private int lineColor;

    public GServiceResponseListener(){
        this(5, 10, Color.RED);
    }

    public GServiceResponseListener(int lineWidth, int zIndex, int lineColor){
        this.lineWidth = lineWidth;
        this.zIndex = zIndex;
        this.lineColor = lineColor;
    }

    @Override
    public void onResponse(Call<GDirection> call, Response<GDirection> response) {
        List<GSteps> ponts = new ArrayList<>();
        final PolylineOptions rectLine = new PolylineOptions().width(this.lineWidth).color(this.lineColor).zIndex(this.zIndex);
        if(response.body().routes.size() > 0) {
            for(GRoutes route : response.body().routes){
                for(GLegs leg : route.getLegs()){
                    ponts.addAll(leg.getSteps());
                }
            }


            for (int i = 0; i < ponts.size(); i++) {
                rectLine.addAll(ponts.get(i).getPolyline().getDecodedPolyLine());
            }

            try {
                EventBus.getDefault().post(new TripRouteEvent(rectLine, ponts));
            } catch (Exception ex) {
                Log.d("GMAPS", ex.getMessage());
            }
        }

    }

    @Override
    public void onFailure(Call<GDirection> call, Throwable t) {

    }
}
