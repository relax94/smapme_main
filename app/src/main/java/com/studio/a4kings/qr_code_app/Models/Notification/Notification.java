package com.studio.a4kings.qr_code_app.Models.Notification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.studio.a4kings.qr_code_app.Models.WallModels.MongoMember;

/**
 * Created by Dmitry Pavlenko on 07.06.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {
    @JsonProperty("_id")
    private String mongoId;
    @JsonProperty("owner_id")
    private String ownerId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("body_text")
    private String text;
    @JsonProperty("sender")
    private MongoMember sender;
    @JsonProperty("posted_date")
    private String postedDate;
    @JsonProperty("core_id")
    private String coreId;

    public String getCoreId() {
        return coreId;
    }

    public void setCoreId(String coreId) {
        this.coreId = coreId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public MongoMember getSender() {
        return sender;
    }

    public void setSender(MongoMember sender) {
        this.sender = sender;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }
}
