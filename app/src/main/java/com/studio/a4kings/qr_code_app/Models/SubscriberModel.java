package com.studio.a4kings.qr_code_app.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by DUX on 23.03.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriberModel implements Serializable{
    private String userId;
    private String fullName;
    private String photo;
    @JsonProperty("isSubscriber")
    private Boolean isSubscriber;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        userId = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Boolean getIsSubscriber() {
        return isSubscriber;
    }

    public void setIsSubscriber(Boolean friend) {
        isSubscriber = friend;
    }
}
