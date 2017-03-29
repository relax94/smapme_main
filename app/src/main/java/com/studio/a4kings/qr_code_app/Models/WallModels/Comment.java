package com.studio.a4kings.qr_code_app.Models.WallModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dmitry Pavlenko on 31.05.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {

    @JsonProperty("_id")
    private String commentId;
    @JsonProperty("post_id")
    private String postId;
    @JsonProperty("owner_id")
    private MongoMember ownerId;
    @JsonProperty("posted_date")
    private String postedDate;
    @JsonProperty("type")
    private int commentType;
    @JsonProperty("text")
    private String text;
    @JsonProperty("attachment_url")
    private String attachmentUrl;

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public MongoMember getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(MongoMember ownerId) {
        this.ownerId = ownerId;
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

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
