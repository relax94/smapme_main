package com.studio.a4kings.qr_code_app.Models.Events;

import com.studio.a4kings.qr_code_app.Models.BookingModels.BookingModel;

import java.util.List;

/**
 * Created by Dmitry Pavlenko on 18.03.2016.
 */
public class BookingHotelsEvent {
    private List<BookingModel> hotels;

    public List<BookingModel> getHotels() {
        return hotels;
    }

    public BookingHotelsEvent(List<BookingModel> hotels) {
        this.hotels = hotels;
    }
}
