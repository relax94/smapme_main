package com.studio.a4kings.qr_code_app.Models.Response;

import com.studio.a4kings.qr_code_app.Models.CountryModel;

import java.util.List;

/**
 * Created by DUX on 16.02.2016.
 */
public class CountryResponse extends MainResponse{
    List<CountryModel> model;

    public List<CountryModel> getModel() {
        return model;
    }

    public void setModel(List<CountryModel> model) {
        this.model = model;
    }
}
