package com.studio.a4kings.qr_code_app.Models;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by DUX on 08.02.2016.
 */
public class MemberProfile /* extends RealmObject*/ implements Serializable {
    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        this.Status = status;
    }

    private  Integer Status;
    private  String UserId;
    private  String Photo;
    private  String Wallpaper;
    private  String Gender;
    private  String FirstName;
    private  String LastName;
    private  String AboutMe;
    private  String Birthday;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getWallpaper() {
        return Wallpaper;
    }

    public void setWallpaper(String wallpaper) {
        Wallpaper = wallpaper;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAboutMe() {
        return AboutMe;
    }

    public void setAboutMe(String aboutMe) {
        AboutMe = aboutMe;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getSignature(){
        return FirstName + " " + LastName;
    }
}
