package com.studio.a4kings.qr_code_app.Managers.Api.Services;

import android.support.annotation.NonNull;

import com.studio.a4kings.qr_code_app.Api.Interface.TripInterface;
import com.studio.a4kings.qr_code_app.Managers.Api.Interfaces.ApiService;
import com.studio.a4kings.qr_code_app.Managers.ApiManager;
import com.studio.a4kings.qr_code_app.Models.Migration.VkMigrationPage;
import com.studio.a4kings.qr_code_app.Models.Response.ServerResponse;
import com.studio.a4kings.qr_code_app.Models.Response.SnapshotUploadResponse;
import com.studio.a4kings.qr_code_app.Models.Response.TripResponse;
import com.studio.a4kings.qr_code_app.Models.TripModel;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;


/**
 * Created by Dmitry Pavlenko on 25.05.2016.
 */
public class TripBuilderService extends ApiManager implements ApiService {

    private TripInterface tripInterface;

    public TripBuilderService(String baseUrl) {
        super(baseUrl);
        this.tripInterface = getApiInterface(TripInterface.class);
    }

    public void saveTrip(TripModel tripModel, @NonNull Callback<TripResponse> saveTripCallback){
        if(tripModel != null){
            this.tripInterface.saveTrip(tripModel).enqueue(saveTripCallback);
        }
    }

    public void getTrip(String ownerId, String eventId, @NonNull Callback<TripResponse> getTripCallback){
        HashMap<String, String> args = new HashMap<>();
        args.put("owner_id", ownerId);
        args.put("event_id", eventId);
        this.tripInterface.getTrip(args).enqueue(getTripCallback);
    }

    public void uploadTripSnapshot( String uri, Callback<SnapshotUploadResponse> uploadCallback){
        File file = new File(uri);
        if(file.exists()){


            // create RequestBody instance from file
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

            // add another part within the multipart request
            RequestBody description =
                    RequestBody.create(
                            MediaType.parse("multipart/form-data"), "some");


            this.tripInterface.uploadTripSnapshot(description, body).enqueue(uploadCallback);

        }
    }


    public void vkMigration(String pageId, Callback<ServerResponse<VkMigrationPage>> callback){
        this.tripInterface.vkMigration(pageId).enqueue(callback);
    }

    @Override
    public <T> void setApiManager(T apiManager) {

    }
}
