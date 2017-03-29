package com.studio.a4kings.qr_code_app.Models.Activity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;

import java.util.ArrayList;

/**
 * Created by DUX on 13.06.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityResponse extends MainResponse {
    ArrayList<ActivityModel> model;

    public ArrayList<ActivityModel> getModel() {
        return model;
    }

    public void setModel(ArrayList<ActivityModel> model) {
        this.model = model;
    }
}
