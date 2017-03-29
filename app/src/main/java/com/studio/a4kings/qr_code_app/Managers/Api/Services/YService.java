package com.studio.a4kings.qr_code_app.Managers.Api.Services;

import com.studio.a4kings.qr_code_app.Api.Interface.Experemental.YandexCityDirectionsInterface;
import com.studio.a4kings.qr_code_app.Managers.Api.Interfaces.ApiService;
import com.studio.a4kings.qr_code_app.Managers.ApiManager;
import com.studio.a4kings.qr_code_app.Managers.Listeners.YStationsListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dmitry Pavlenko on 24.03.2016.
 */
public class YService extends ApiManager implements ApiService {

    private YandexCityDirectionsInterface yandexCityDirectionsInterface;
    private final String API_KEY;

    public YService(String baseUrl, String apiKey) {
        super(baseUrl);
        this.yandexCityDirectionsInterface = getApiInterface(YandexCityDirectionsInterface.class);
        this.API_KEY = apiKey;
    }

    @Override
    public <T> T getApiInterface(Class<T> clazz) {
        return super.getApiInterface(clazz);
    }

    @Override
    public <T> void setApiManager(T apiManager) {

    }

    public void getNearestStations(double lat, double lng) {
        this.yandexCityDirectionsInterface.getNearestStations(buildQuery(lat, lng)).enqueue(new YStationsListener());
    }

    private Map<String, String> buildQuery(double lat, double lng) {
        Map<String, String> query = new HashMap<>();
        query.put("apikey", this.API_KEY);
        query.put("format", "json");
        query.put("lat", String.format("%f", lat));
        query.put("lng", String.format("%f", lng));
        query.put("distance", String.format("%d", 50));
        query.put("lang", "ru");
        return query;
    }
}
