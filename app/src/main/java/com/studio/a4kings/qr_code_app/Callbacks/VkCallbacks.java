package com.studio.a4kings.qr_code_app.Callbacks;

import com.studio.a4kings.qr_code_app.Interfaces.BroadcastMethods;
import com.studio.a4kings.qr_code_app.Models.Member;
import com.studio.a4kings.qr_code_app.Models.Enums.AccountType;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONObject;

/**
 * Created by Dmitry Pavlenko on 04.01.2016.
 */
public class VkCallbacks implements VKCallback<VKAccessToken> {

    private BroadcastMethods broadcastMethods;

    public VkCallbacks(BroadcastMethods methods){
        if(methods != null)
            this.broadcastMethods = methods;
    }

    @Override
    public void onResult(VKAccessToken res) {
        if(res.userId != null)
            vkGetMember(res.userId);
        else
            BroadcastAuthenticationResult(false, null);
    }

    @Override
    public void onError(VKError error) {
        BroadcastAuthenticationResult(false, null);
    }

    private void vkGetMember(String vkUserId){
        final VKRequest request = new VKRequest("users.get", VKParameters.from(VKApiConst.FIELDS, "sex,bdate,city"));


        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                try {
                    JSONObject jsonObject = response.json.getJSONArray("response").getJSONObject(0);
                    Member member = new Member();
                    member.setAccountType(AccountType.VK.ordinal());
                    member.setSocialId(jsonObject.getString("id"));
                    member.setEmail("");
                    member.getProfile().setFirstName(jsonObject.getString("first_name"));
                    member.getProfile().setLastName(jsonObject.getString("last_name"));
                    member.setNameSignature(member.getProfile().getFirstName() + " " + member.getProfile().getLastName());
                    member.getProfile().setGender(jsonObject.get("sex").toString());
                    member.getProfile().setBirthday(jsonObject.get("bdate").toString());

                        BroadcastAuthenticationResult(true, member);

                }catch(Exception ex){
                        BroadcastAuthenticationResult(false, null);
                }
            }
            @Override
            public void onError(VKError error) {
                BroadcastAuthenticationResult(false, null);
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                BroadcastAuthenticationResult(false, null);
            }
        });
    }

    private void BroadcastAuthenticationResult(boolean result, Member data){
        if(this.broadcastMethods != null)
            broadcastMethods.BroadcastAuthenticationResult(result, data);
    }

}
