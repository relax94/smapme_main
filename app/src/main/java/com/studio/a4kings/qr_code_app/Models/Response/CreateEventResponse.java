package com.studio.a4kings.qr_code_app.Models.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by DUX on 04.06.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateEventResponse extends MainResponse {
    Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
