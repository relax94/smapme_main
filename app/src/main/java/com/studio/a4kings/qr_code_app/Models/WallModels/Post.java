package com.studio.a4kings.qr_code_app.Models.WallModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dmitry Pavlenko on 31.05.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Post implements Serializable {
    @JsonProperty("_id")
    private String postId;
    @JsonProperty("wall_id")
    private String wallId;
    @JsonProperty("owner_id")
    private MongoMember owner;
    @JsonProperty("posted_date")
    private String postedDate;
    @JsonProperty("post_type")
    private int postType;
    @JsonProperty("text")
    private String text;
    @JsonProperty("attachment_url")
    private String attachmentUrl;
    @JsonProperty("creator_id")
    private String creatorId;

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public MongoMember getOwnerId() {
        return owner;
    }

    public void setOwnerId(MongoMember owner) {
        this.owner = owner;
    }

    public String getPostId() {
        return postId;
    }

    public String getWallId() {
        return wallId;
    }

    public void setWallId(String wallId) {
        this.wallId = wallId;
    }

    public String getPostedDate() {
        try {
            ISO8601DateFormat df = new ISO8601DateFormat();
            Date date = df.parse(postedDate);
            String newstring = new SimpleDateFormat("HH-mm").format(date);
            return newstring;
        }
        catch (Exception ex){
            return postedDate;
        }
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public int getPostType() {
        return postType;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
