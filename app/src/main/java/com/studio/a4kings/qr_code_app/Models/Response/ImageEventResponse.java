package com.studio.a4kings.qr_code_app.Models.Response;

import com.studio.a4kings.qr_code_app.Models.ImageEventModel;

import java.util.ArrayList;

/**
 * Created by DUX on 04.05.2016.
 */
public class ImageEventResponse extends MainResponse {
    ArrayList<ImageEventModel> model;

    public ArrayList<ImageEventModel> getModel() {
        return model;
    }

    public void setModel(ArrayList<ImageEventModel> model) {
        this.model = model;
    }
}
