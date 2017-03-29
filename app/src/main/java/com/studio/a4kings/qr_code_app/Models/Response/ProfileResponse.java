package com.studio.a4kings.qr_code_app.Models.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.studio.a4kings.qr_code_app.Models.MemberProfile;

import io.realm.annotations.Ignore;

/**
 * Created by hack on 25.01.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileResponse extends MainResponse{
    private MemberProfile profile;

    public MemberProfile getProfile() {
        return profile;
    }

    public void setProfile(MemberProfile profile) {
        this.profile = profile;
    }
}
