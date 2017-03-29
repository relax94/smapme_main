package com.studio.a4kings.qr_code_app.Models.FSQModels;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Dmitry Pavlenko on 15.03.2016.
 */

public class FSQCategory {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("pluralName")
    private String pluralName;
    @JsonProperty("shortName")
    private String shortName;
    @JsonProperty("icon")
    private FSQIcon icon;
    @JsonProperty("primary")
    private boolean primary;

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

    public String getPluralName() {
        return pluralName;
    }

    public void setPluralName(String pluralName) {
        this.pluralName = pluralName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public FSQIcon getIcon() {
        return icon;
    }

    public void setIcon(FSQIcon icon) {
        this.icon = icon;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}
