package com.studio.a4kings.qr_code_app.Managers.Api.Services;

import com.studio.a4kings.qr_code_app.Api.Interface.ActivityInterface;
import com.studio.a4kings.qr_code_app.Managers.Api.Interfaces.ApiService;
import com.studio.a4kings.qr_code_app.Managers.ApiManager;
import com.studio.a4kings.qr_code_app.Models.Activity.ActivityResponse;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;

import retrofit2.Callback;

/**
 * Created by DUX on 13.06.2016.
 */
public class ActivityService extends ApiManager implements ApiService {

    private ActivityInterface activityInterface;

    public ActivityService(String baseUrl) {
        super(baseUrl);
        this.activityInterface = getApiInterface(ActivityInterface.class);
    }

    @Override
    public <T> T getApiInterface(Class<T> clazz) {
        return super.getApiInterface(clazz);
    }

    @Override
    public <T> void setApiManager(T apiManager) {

    }

    public void getAllActivities(Callback<ActivityResponse> callback){
        activityInterface.getAll(this.OPERATION_TOKEN).enqueue(callback);
    }
}
