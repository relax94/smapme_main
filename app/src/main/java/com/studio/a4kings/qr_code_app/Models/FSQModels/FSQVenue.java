package com.studio.a4kings.qr_code_app.Models.FSQModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Dmitry Pavlenko on 15.03.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FSQVenue {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("location")
    private FSQLocation location;
    @JsonProperty("categories")
    private List<FSQCategory> categories;
    @JsonProperty("verified")
    private boolean verified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FSQLocation getLocation() {
        return location;
    }

    public void setLocation(FSQLocation location) {
        this.location = location;
    }

    public List<FSQCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<FSQCategory> categories) {
        this.categories = categories;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
