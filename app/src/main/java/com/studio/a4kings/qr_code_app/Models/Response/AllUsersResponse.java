package com.studio.a4kings.qr_code_app.Models.Response;

import com.studio.a4kings.qr_code_app.Models.SubscriberModel;

import java.util.ArrayList;

/**
 * Created by DUX on 24.03.2016.
 */
public class AllUsersResponse extends MainResponse {
    ArrayList<SubscriberModel> model;

    public ArrayList<SubscriberModel> getModel() {
        return model;
    }

    public void setModel(ArrayList<SubscriberModel> model) {
        this.model = model;
    }
}
