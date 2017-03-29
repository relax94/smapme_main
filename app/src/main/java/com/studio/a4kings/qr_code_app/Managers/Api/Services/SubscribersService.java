package com.studio.a4kings.qr_code_app.Managers.Api.Services;

import com.studio.a4kings.qr_code_app.Api.Interface.SubscriptionInterface;
import com.studio.a4kings.qr_code_app.Managers.Api.Interfaces.ApiService;
import com.studio.a4kings.qr_code_app.Managers.ApiManager;
import com.studio.a4kings.qr_code_app.Models.Response.AllUsersResponse;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Callback;

/**
 * Created by DUX on 24.03.2016.
 */
public class SubscribersService extends ApiManager implements ApiService {

    SubscriptionInterface subscriptionInterface;

    public SubscribersService(String baseUrl) {
        super(baseUrl);
        this.subscriptionInterface = getApiInterface(SubscriptionInterface.class);
    }

    @Override
    public <T> T getApiInterface(Class<T> clazz) {
        return super.getApiInterface(clazz);
    }

    @Override
    public <T> void setApiManager(T apiManager) {
    }

    public void subscribe(String userId, @NotNull Callback<MainResponse> callback)
    {
        this.subscriptionInterface.subscribe(this.OPERATION_TOKEN, userId).enqueue(callback);
    }

    public void deleteSubscriber(String userId, @NotNull Callback<MainResponse> callback)
    {
        this.subscriptionInterface.deleteSub(this.OPERATION_TOKEN, userId).enqueue(callback);
    }

    public void getAllPeople(@NotNull Callback<AllUsersResponse> callback)
    {
        this.subscriptionInterface.getAllUsers(this.OPERATION_TOKEN).enqueue(callback);
    }

    public void getAllUserFriends(@NotNull Callback<AllUsersResponse> callback)
    {
        this.subscriptionInterface.getAllUserSubscribers(this.OPERATION_TOKEN).enqueue(callback);
    }

    public void getAllSubscribersById(String userId, @NotNull Callback<AllUsersResponse> callback){
        this.subscriptionInterface.getAllUserSubscribersById(this.OPERATION_TOKEN, userId).enqueue(callback);
    }

}
