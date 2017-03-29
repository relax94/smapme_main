package com.studio.a4kings.qr_code_app.Managers.Api.Services;

import com.studio.a4kings.qr_code_app.Api.Interface.CountryInterface;
import com.studio.a4kings.qr_code_app.Api.Interface.ProfileInterface;
import com.studio.a4kings.qr_code_app.Api.Interface.SignInUpInterface;
import com.studio.a4kings.qr_code_app.Api.Interface.TravelEventsInterface;
import com.studio.a4kings.qr_code_app.Managers.Api.Interfaces.ApiService;
import com.studio.a4kings.qr_code_app.Managers.ApiManager;
import com.studio.a4kings.qr_code_app.Models.Member;
import com.studio.a4kings.qr_code_app.Models.Response.SignResponse;
import com.studio.a4kings.qr_code_app.Utils.DataEncryption;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Dmitry Pavlenko on 14.03.2016.
 */
public class SignInUpService extends ApiManager implements ApiService {

    private SignInUpInterface signInUpInterface;

    public SignInUpService(String baseUrl) {
        super(baseUrl);
        this.signInUpInterface = getApiInterface(SignInUpInterface.class);
    }

    @Override
    public <T> T getApiInterface(Class<T> clazz) {
        return super.getApiInterface(clazz);
    }

    @Override
    public <T> void setApiManager(T apiManager) {

    }

    public void signUpAsync(@NotNull Member member, @NotNull Callback<SignResponse> callback) {
        Map<String, String> args = new HashMap<>();
        args.put("email", member.getEmail());
        args.put("password", member.getPassword());
        signInUpInterface.signUpApiWithMap(args).enqueue(callback);
    }

    private Call<SignResponse> signInAsyncRequest(@NotNull Map<String, String> args) {
        args.put("deviceToken", DataEncryption.encryptString());
        args.put("grant_type", "password");
        return signInUpInterface.signInApiWithMap(args);
    }

    public void signInAsync(@NotNull Member member, @NotNull Callback<SignResponse> callback) {
        Map<String, String> args = new HashMap<>();
        args.put("email", member.getEmail());
        args.put("password", member.getPassword());
        args.put("accountType", Integer.toString(member.getAccountType()));
        args.put("socialId", member.getSocialId());
        signInAsyncRequest(args).enqueue(callback);
    }
}
