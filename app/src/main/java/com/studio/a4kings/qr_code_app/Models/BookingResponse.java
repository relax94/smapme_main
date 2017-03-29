package com.studio.a4kings.qr_code_app.Models;

import com.studio.a4kings.qr_code_app.Models.BookingModels.BookingModel;

import java.util.List;

/**
 * Created by Dmitry Pavlenko on 18.03.2016.
 */
public class BookingResponse {

    private Integer code;

    private List<BookingModel> bookingData;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<BookingModel> getBookingData() {
        return bookingData;
    }

    public void setBookingData(List<BookingModel> bookingData) {
        this.bookingData = bookingData;
    }
}
