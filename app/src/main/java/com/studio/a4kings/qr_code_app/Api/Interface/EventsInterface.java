package com.studio.a4kings.qr_code_app.Api.Interface;

import com.studio.a4kings.qr_code_app.Models.EventModels.EventDetailResponse;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventFullResponse;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventRequestModel;
import com.studio.a4kings.qr_code_app.Models.EventModels.EventsResponse;
import com.studio.a4kings.qr_code_app.Models.Response.AllRequestsResponse;
import com.studio.a4kings.qr_code_app.Models.Response.AllUsersResponse;
import com.studio.a4kings.qr_code_app.Models.Response.CreateEventResponse;
import com.studio.a4kings.qr_code_app.Models.Response.EventRatingResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ImageEventResponse;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;
import com.studio.a4kings.qr_code_app.Models.Response.TagsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by DUX on 19.03.2016.
 */
public interface EventsInterface {
    @POST("/api/te/create")
    Call<CreateEventResponse> createEvent(@Header("Authorization") String authorizationToken, @Body EventRequestModel user);

    @GET("/api/te/getAll")
    Call<EventsResponse> getAll(@Header("Authorization") String authorizationToken);

    @GET("/api/te/getAllByUserId")
    Call<EventsResponse> getAllEventForUser(@Header("Authorization") String authorizationToken, @Query("userId") String userId);

    @GET("/api/te/getAllMyEvents")
    Call<EventsResponse> getMyEvents(@Header("Authorization") String authorizationToken);

    @GET("/api/te/getAllMyEventsFetch")
    Call<EventsResponse> getMyEventsFetch(@Header("Authorization") String authorizationToken, @Query("page") Integer page );

    @GET("/api/te/getAllFetch")
    Call<EventsResponse> getAllEventsFetch(@Header("Authorization") String authorizationToken, @Query("page") Integer page);

    @GET("/api/te/getAllByUserIdFetch")
    Call<EventsResponse> getAllByUserIdFetch(@Header("Authorization") String authorizationToken, @Query("page") Integer page, @Query("userId") String usrId);

    @GET("/api/te/deleteImageFromEventId")
    Call<MainResponse> deleteImageFromEventId(@Header("Authorization") String authorizationToken, @Query("eventId") Integer eventId, @Query("photoId") Integer photoId);

    @POST("/api/te/addRequest")
    Call<MainResponse> addRequest(@Header("Authorization") String authorizationToken, @Query("eventId") Integer eventId, @Query("userId") String userId);

    @GET("/api/te/getAllTags")
    Call<TagsResponse> getTags(@Header("Authorization") String authorizationToken);

    @GET("/api/te/getAllRequestsForEvent")
    Call<AllRequestsResponse> getAllRequestsForEvent(@Header("Authorization") String authorizationToken, @Query("eventId") Integer eventId);

    @GET("/api/te/getFullEventById")
    Call<EventFullResponse> getEventById(@Header("Authorization") String authorizationToken, @Query("eventId") Integer eventId);

    @POST("/api/te/addUserToEvent")
    Call<MainResponse> addUserToEvent(@Header("Authorization") String authorizationToken, @Query("eventId") Integer eventId, @Query("requestId") Integer userId);

    @GET("/api/te/getAllSubsForEvent")
    Call<AllUsersResponse> getAllSubsForEvent(@Header("Authorization") String operation_token, @Query("eventId") Integer eventId);

    @GET("/api/te/getAllImagesForEventById")
    Call<ImageEventResponse> getAllImagesForEventById(@Header("Authorization") String authorizationToken, @Query("eventId") Integer eventId);

    @POST("/api/te/addImagesToEvent")
    Call<MainResponse> addImagesToEvent(@Header("Authorization") String authorizationToken, @Query("eventId") Integer eventId, @Body ArrayList<String> images);

    @GET("/api/te/deleteUserFromRequest")
    Call<MainResponse> removeUserFromRequests(@Header("Authorization") String authorizationToken, @Query("eventId") Integer eventId);

    @POST("/api/te/updateEventById")
    Call<MainResponse> updateEventById(@Header("Authorization") String operation_token, @Query("eventId") Integer eventId, @Body EventRequestModel eventModel);

    @GET("/api/te/deleteEventById")
    Call<MainResponse> deleteEventById(@Header("Authorization") String operation_token, @Query("eventId") Integer eventId);


    @GET("/api/te/addRating")
    Call<MainResponse> sendRating(@Header("Authorization") String operation_token, @Query("eventId") Integer eventId, @Query("eval") Float eval);

    @GET("/api/te/getUserRating")
    Call<EventRatingResponse>  getUserRatingForEvent(@Header("Authorization") String operation_token,@Query("eventId") Integer eventId, @Query("userId") String userId);

    //by admin
    @GET("/api/te/deleteUserFromEventByAdmin")
    Call<MainResponse> removeUserFromEvent(@Header("Authorization") String authorizationToken, @Query("eventId") Integer eventId, @Query("userId") String userId);

    //by myself
    @GET("/api/te/deleteUserFromEvent")
    Call<MainResponse> removeUserFromEvent(@Header("Authorization") String authorizationToken, @Query("eventId") Integer eventId);
}
