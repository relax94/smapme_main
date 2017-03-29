package com.studio.a4kings.qr_code_app.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by DUX on 06.05.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageEventModel implements Serializable{
    private String photoName;
    private String createdDate;
    private String authorId;
    private String fullNameAuthor;
    private Integer photoId;

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getFullNameAuthor() {
        return fullNameAuthor;
    }

    public void setFullNameAuthor(String fullNameAuthor) {
        this.fullNameAuthor = fullNameAuthor;
    }
}
