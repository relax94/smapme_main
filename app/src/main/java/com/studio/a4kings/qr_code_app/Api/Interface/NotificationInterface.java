package com.studio.a4kings.qr_code_app.Api.Interface;



import com.studio.a4kings.qr_code_app.Models.Notification.Notification;
import com.studio.a4kings.qr_code_app.Models.Response.WallResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Dmitry Pavlenko on 07.06.2016.
 */
public interface NotificationInterface {

    @POST("/notify/getall")
    @FormUrlEncoded
    Call<WallResponse<Notification>> getAllNotificationsByOwner(@Field("owner_id") String ownerId);

}
