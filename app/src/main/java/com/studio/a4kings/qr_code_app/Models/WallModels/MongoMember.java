package com.studio.a4kings.qr_code_app.Models.WallModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.studio.a4kings.qr_code_app.Models.MemberProfile;

import java.io.Serializable;

/**
 * Created by Dmitry Pavlenko on 03.06.2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MongoMember implements Serializable {

    @JsonProperty("_id")
    private String mongoId;
    @JsonProperty("member_id")
    private String memberId;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("icon_url")
    private String iconUrl;

    public MongoMember(String memberId, String firstName, String lastName, String iconUrl) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.iconUrl = iconUrl;
    }

    public MongoMember() {
    }

    public String getMongoId() {
        return mongoId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public MongoMember convertMemberProfileToMongo(MemberProfile member){
        return new MongoMember(member.getUserId(), member.getFirstName(), member.getLastName(), member.getPhoto());
    }
}
