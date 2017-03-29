package com.studio.a4kings.qr_code_app.Callbacks;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.studio.a4kings.qr_code_app.Interfaces.BroadcastMethods;
import com.studio.a4kings.qr_code_app.Models.Enums.AccountType;
import com.studio.a4kings.qr_code_app.Models.Member;

import org.json.JSONObject;

/**
 * Created by Dmitry Pavlenko on 04.01.2016.
 */
public class FacebookCallbacks<T> implements FacebookCallback<T> {

    private BroadcastMethods broadcastMethods;

    public FacebookCallbacks(BroadcastMethods broadcastMethods) {
        if (broadcastMethods != null)
            this.broadcastMethods = broadcastMethods;
    }

    @Override
    public void onSuccess(T t) {
        makeGraphRequest();
    }

    @Override
    public void onCancel() {
        BroadcastAuthenticationResult(false, null);
    }

    @Override
    public void onError(FacebookException error) {
        BroadcastAuthenticationResult(false, null);
    }

    private void makeGraphRequest() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        GraphResponse r = response;
                        parseMemberAndSendResult(object);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,birthday,gender,hometown");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void parseMemberAndSendResult(JSONObject response) {
        try {
            if (response != null) {
                Member member = new Member();
                member.setAccountType(AccountType.FB.ordinal());
                String email = response.has("email") ? response.getString("email") : "";
                String name = response.has("name") ? response.getString("name") : "";
                String firstname = name.split(" ")[0].trim();
                String lastname = name.split(" ")[1].trim();
                String birthday = response.has("birthday") ? response.getString("birthday") : "";
                String gender = response.has("gender") ? response.get("gender").toString() : "";
                String id = response.has("id") ? response.getString("id") : "";
                member.setEmail(email != null ? email : "");
                member.getProfile().setFirstName(firstname);
                member.getProfile().setLastName(lastname);
                member.setNameSignature(name);
                member.setSocialId(id);
                member.getProfile().setBirthday(birthday);
                member.getProfile().setGender(gender);
                BroadcastAuthenticationResult(true, member);
            }
        } catch (Exception ex) {
            BroadcastAuthenticationResult(false, null);
        }
    }

    private void BroadcastAuthenticationResult(boolean result, Member data) {
        if (this.broadcastMethods != null)
            broadcastMethods.BroadcastAuthenticationResult(result, data);
    }
}
