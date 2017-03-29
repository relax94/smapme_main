package com.studio.a4kings.qr_code_app.Models;

/**
 * Created by DUX on 22.03.2016.
 */
public class EventMemberModel {
    private String Id;
    private String FullName;
    private String Photo;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
