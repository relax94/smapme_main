package com.studio.a4kings.qr_code_app.Models.Response;

import com.studio.a4kings.qr_code_app.Models.RequestModel;

import java.util.ArrayList;

/**
 * Created by DUX on 06.05.2016.
 */
public class AllRequestsResponse extends MainResponse {
    ArrayList<RequestModel> model;

    public ArrayList<RequestModel> getModel() {
        return model;
    }

    public void setModel(ArrayList<RequestModel> model) {
        this.model = model;
    }
}
