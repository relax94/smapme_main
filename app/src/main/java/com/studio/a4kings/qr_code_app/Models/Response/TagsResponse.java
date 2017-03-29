package com.studio.a4kings.qr_code_app.Models.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.studio.a4kings.qr_code_app.Models.Tag;

import java.util.ArrayList;

/**
 * Created by DUX on 19.03.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagsResponse extends MainResponse {
    ArrayList<String> model;

    public ArrayList<String> getModel() {
        return model;
    }

    public void setModel(ArrayList<String> model) {
        this.model = model;
    }
}
