package com.studio.a4kings.qr_code_app.Managers.Api.Services;

import com.studio.a4kings.qr_code_app.Api.Interface.TravelEventsInterface;
import com.studio.a4kings.qr_code_app.Managers.Api.Interfaces.ApiService;
import com.studio.a4kings.qr_code_app.Managers.ApiManager;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;
import com.studio.a4kings.qr_code_app.Models.Response.TravelEventResponse;
import com.studio.a4kings.qr_code_app.Models.TravelEventModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import retrofit2.Callback;

/**
 * Created by Dmitry Pavlenko on 14.03.2016.
 */
public class TravelEventsService extends ApiManager implements ApiService {

    private TravelEventsInterface travelEventsInterface;

    public TravelEventsService(String baseUrl) {
        super(baseUrl);
        this.travelEventsInterface = getApiInterface(TravelEventsInterface.class);
    }

    @Override
    public <T> T getApiInterface(Class<T> clazz) {
        return super.getApiInterface(clazz);
    }

    @Override
    public <T> void setApiManager(T apiManager) {

    }

    public void addTravelEventAsync(@NotNull TravelEventModel travelEvent, @NotNull Callback<MainResponse> callback){
        travelEventsInterface.addEvent(this.OPERATION_TOKEN, travelEvent).enqueue(callback);
    }

    public void getAll(@NotNull Callback<TravelEventResponse> callback) throws IOException {
        travelEventsInterface.getEventsList(this.OPERATION_TOKEN).enqueue(callback);
    }

    public void getUserEventsListAsync(@NotNull String userEmail, @NotNull Callback<TravelEventResponse> callback) throws IOException{
        travelEventsInterface.getUserEventsList(this.OPERATION_TOKEN, userEmail).enqueue(callback);
    }
}
