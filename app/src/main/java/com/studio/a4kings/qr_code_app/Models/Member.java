package com.studio.a4kings.qr_code_app.Models;

import com.studio.a4kings.qr_code_app.Utils.Utils;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Dmitry Pavlenko on 23.12.2015.
 */
public class Member /*extends RealmObject*/ implements Serializable {


    public String getSocialId() {
        return SocialId;
    }

    public void setSocialId(String socialId) {
        SocialId = socialId;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getAccountType() {
        return AccountType;
    }

    public void setAccountType(int accountType) {
        AccountType = accountType;
    }

    public MemberProfile getProfile() {
        return Profile;
    }

    public void setProfile(MemberProfile profile) {
        Profile = profile;
    }

    public Member(){
        this.Profile = new MemberProfile();
    }

    public void setNameSignature(String nameSignature) {
        this.nameSignature = nameSignature;
    }

    public String getNameSignature() {
        return nameSignature;
    }


    private String nameSignature;
    private String SocialId;
    private String Email;
    private String Password;

    public String getId() {
        return Id;
    }

    public void setId(String memberId) {
        this.Id = memberId;
    }

    private String Id;

   /* @PrimaryKey
    private String recordId;*/
    private int AccountType;
    private MemberProfile Profile;

}
