package com.studio.a4kings.qr_code_app.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by DUX on 15.03.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tag {
    Integer Id;
    String Title;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
