package com.studio.a4kings.qr_code_app.Managers.Api.Services;

import com.studio.a4kings.qr_code_app.Api.Interface.BookingInterface;
import com.studio.a4kings.qr_code_app.Managers.Api.Interfaces.ApiService;
import com.studio.a4kings.qr_code_app.Managers.ApiManager;
import com.studio.a4kings.qr_code_app.Models.BookingResponse;

import retrofit2.Callback;

/**
 * Created by Dmitry Pavlenko on 18.03.2016.
 */
public class BookingService extends ApiManager implements ApiService {

    private BookingInterface bookingInterface;

    public BookingService(String baseUrl) {
        super(baseUrl);
        this.bookingInterface  = getApiInterface(BookingInterface.class);
    }

    @Override
    public <T> T getApiInterface(Class<T> clazz) {
        return super.getApiInterface(clazz);
    }

    @Override
    public <T> void setApiManager(T apiManager) {

    }

    public void loadHotelsByCity(String cityName, Callback<BookingResponse> callback){
        this.bookingInterface.loadRecordsByCriteria("city_preferred", cityName).enqueue(callback);
    }

}
