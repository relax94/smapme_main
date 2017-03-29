package com.studio.a4kings.qr_code_app.Managers.Api.Services;

import android.graphics.Bitmap;

import com.studio.a4kings.qr_code_app.Api.Interface.EventsInterface;
import com.studio.a4kings.qr_code_app.Managers.Api.Interfaces.ApiService;
import com.studio.a4kings.qr_code_app.Managers.ApiManager;
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
import com.studio.a4kings.qr_code_app.Utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Callback;

/**
 * Created by DUX on 19.03.2016.
 */
public class EventsService extends ApiManager implements ApiService {

    private EventsInterface eventsInterface;

    public EventsService(String baseUrl) {
        super(baseUrl);
        this.eventsInterface = getApiInterface(EventsInterface.class);
    }

    @Override
    public <T> T getApiInterface(Class<T> clazz) {
        return super.getApiInterface(clazz);
    }

    @Override
    public <T> void setApiManager(T apiManager) {
    }

    public void getTags(Callback<TagsResponse> callback) {
        this.eventsInterface.getTags(this.OPERATION_TOKEN).enqueue(callback);
    }

    public void getAllEventForUser(@NotNull String userId, Callback<EventsResponse> callback) {
        this.eventsInterface.getAllEventForUser(this.OPERATION_TOKEN, userId).enqueue(callback);
    }

    public void createEvent(@NotNull EventRequestModel eventModel, Callback<CreateEventResponse> callback) {
        this.eventsInterface.createEvent(this.OPERATION_TOKEN, eventModel).enqueue(callback);
    }

    public void getEventById(@NotNull Integer eventId, Callback<EventFullResponse> callback) {
        this.eventsInterface.getEventById(this.OPERATION_TOKEN, eventId).enqueue(callback);
    }

    public void addRequest(@NotNull Integer eventId, @NotNull String userId, Callback<MainResponse> callback) {
        this.eventsInterface.addRequest(this.OPERATION_TOKEN, eventId, userId).enqueue(callback);
    }

    public void addUserToEvent(@NotNull Integer eventId, @NotNull Integer requestId, Callback<MainResponse> callback) {
        this.eventsInterface.addUserToEvent(this.OPERATION_TOKEN, eventId, requestId).enqueue(callback);
    }

    public void getMyEvents(Callback<EventsResponse> callback) {
        this.eventsInterface.getMyEvents(this.OPERATION_TOKEN).enqueue(callback);
    }

    public void getAllEventsFetch(Integer page, Callback<EventsResponse> callback) {
        this.eventsInterface.getAllEventsFetch(this.OPERATION_TOKEN, page).enqueue(callback);
    }

    public void getMyEventsFetch(Integer page, Callback<EventsResponse> callback) {
        this.eventsInterface.getMyEventsFetch(this.OPERATION_TOKEN, page).enqueue(callback);
    }

    public void deleteImageFromEvent(Integer eventId, Integer photoId, Callback<MainResponse> callback) {
        this.eventsInterface.deleteImageFromEventId(this.OPERATION_TOKEN, eventId, photoId).enqueue(callback);
    }

    public void getAll(Callback<EventsResponse> callback) {
        this.eventsInterface.getAll(this.OPERATION_TOKEN).enqueue(callback);
    }

    public void getAllRequestsForEvent(@NotNull Integer eventId, Callback<AllRequestsResponse> callback) {
        this.eventsInterface.getAllRequestsForEvent(OPERATION_TOKEN, eventId).enqueue(callback);
    }

    public void getAllUsersForEvent(@NotNull Integer eventId, Callback<AllUsersResponse> callback) {
        this.eventsInterface.getAllSubsForEvent(OPERATION_TOKEN, eventId).enqueue(callback);
    }

    public void getAllImagesForEventById(@NotNull Integer eventId, Callback<ImageEventResponse> callback) {
        this.eventsInterface.getAllImagesForEventById(OPERATION_TOKEN, eventId).enqueue(callback);
    }

    public void addImagesToEvent(@NotNull Integer eventId, ArrayList<Bitmap> bitmaps, Callback<MainResponse> callback) {
        ArrayList<String> photos = new ArrayList<>();
        for (Bitmap photo : bitmaps) {
            photos.add(Utils.getInstance().bitmapToBase64(photo));
        }
        this.eventsInterface.addImagesToEvent(OPERATION_TOKEN, eventId, photos).enqueue(callback);
    }

    public void updateEvent(Integer eventId, EventRequestModel eventModel, Callback<MainResponse> callback) {
        this.eventsInterface.updateEventById(this.OPERATION_TOKEN, eventId, eventModel).enqueue(callback);
    }

    public void deleteEvent(Integer eventId, Callback<MainResponse> callback) {
        this.eventsInterface.deleteEventById(this.OPERATION_TOKEN, eventId).enqueue(callback);
    }

    public void removeRequest(Integer eventId, Callback<MainResponse> callback) {
        this.eventsInterface.removeUserFromRequests(this.OPERATION_TOKEN, eventId).enqueue(callback);
    }

    public void removeUserFromEvent(Integer eventId, String userId, Callback<MainResponse> callback) {
        this.eventsInterface.removeUserFromEvent(this.OPERATION_TOKEN, eventId, userId).enqueue(callback);
    }

    public void sendRating(Integer eventId, Float eval, Callback<MainResponse> callback) {
        this.eventsInterface.sendRating(this.OPERATION_TOKEN, eventId, eval).enqueue(callback);
    }

    public void removeUserFromEvent(Integer eventId, Callback<MainResponse> callback) {
        this.eventsInterface.removeUserFromEvent(this.OPERATION_TOKEN, eventId).enqueue(callback);
    }

    public void getUserRatingForEvent(Integer eventId, String userId, Callback<EventRatingResponse> callback) {
        this.eventsInterface.getUserRatingForEvent(this.OPERATION_TOKEN, eventId, userId).enqueue(callback);
    }
}
