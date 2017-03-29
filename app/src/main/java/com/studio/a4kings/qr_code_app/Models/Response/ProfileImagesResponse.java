package com.studio.a4kings.qr_code_app.Models.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DUX on 13.06.2016.
 */
public class ProfileImagesResponse extends MainResponse{
    ArrayList<String> model;

    public ArrayList<String> getModel() {
        return model;
    }

    public void setModel(ArrayList<String> model) {
        this.model = model;
    }
}
