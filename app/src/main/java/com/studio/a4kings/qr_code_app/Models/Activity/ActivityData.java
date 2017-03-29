package com.studio.a4kings.qr_code_app.Models.Activity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by DUX on 13.06.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityData {
    private String userId;
    private String fullName;
    private String message;
    private String updatedImage;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUpdatedImage() {
        return updatedImage;
    }

    public void setUpdatedImage(String updatedImage) {
        this.updatedImage = updatedImage;
    }
}
