package com.studio.a4kings.qr_code_app.Managers.Api.Services;

import com.studio.a4kings.qr_code_app.Api.Interface.FSQServicesInterface;
import com.studio.a4kings.qr_code_app.Managers.Api.Interfaces.ApiService;
import com.studio.a4kings.qr_code_app.Managers.ApiManager;
import com.studio.a4kings.qr_code_app.Models.FSQResponse;

import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

import retrofit2.Callback;
import rx.Subscriber;

/**
 * Created by Dmitry Pavlenko on 15.03.2016.
 */
public class FSQService extends ApiManager implements ApiService {

    private FSQServicesInterface fsqServicesInterface;
    private String CLIENT_ID;
    private String CLIENT_SECRET;

    public FSQService(String baseUrl) {
        super(baseUrl);
        this.fsqServicesInterface = getApiInterface(FSQServicesInterface.class);
    }

    public FSQService(String baseUrl, String clientId, String clientSecret) {
        super(baseUrl);
        this.CLIENT_ID = clientId;
        this.CLIENT_SECRET = clientSecret;
        this.fsqServicesInterface = getApiInterface(FSQServicesInterface.class);
    }

    @Override
    public <T> T getApiInterface(Class<T> clazz) {
        return super.getApiInterface(clazz);
    }

    @Override
    public <T> void setApiManager(T apiManager) {

    }

    public void foursquareSearchPlacesByCoords(Double lat, Double lng, Callback<FSQResponse> callback){
        this.fsqServicesInterface.fourSquareSearchByCoords(buildRequestQuery(lat,lng)).enqueue(callback);
    }

    public void fsqRx(Double lat, Double lng, Subscriber<FSQResponse> callback){
        this.fsqServicesInterface.fourSquareSearchByCoordsRx(buildRequestQuery(lat, lng))
            .subscribe(callback);
    }

    private HashMap<String, String> buildRequestQuery(Double lat, Double lng){
        HashMap<String, String> reqQuery = new HashMap<>();
        reqQuery.put("client_id", this.CLIENT_ID);
        reqQuery.put("client_secret", this.CLIENT_SECRET);
        reqQuery.put("v", "20130815");
        reqQuery.put("ll", String.format(Locale.US,"%f,%f", lat, lng));
        return reqQuery;
    }


}
