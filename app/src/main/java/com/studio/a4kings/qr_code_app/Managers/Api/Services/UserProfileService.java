package com.studio.a4kings.qr_code_app.Managers.Api.Services;

import android.graphics.Bitmap;

import com.studio.a4kings.qr_code_app.Api.Interface.CountryInterface;
import com.studio.a4kings.qr_code_app.Api.Interface.ProfileInterface;
import com.studio.a4kings.qr_code_app.Managers.Api.Interfaces.ApiService;
import com.studio.a4kings.qr_code_app.Managers.ApiManager;
import com.studio.a4kings.qr_code_app.Models.MemberProfile;
import com.studio.a4kings.qr_code_app.Models.Response.CountryResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ImageEventResponse;
import com.studio.a4kings.qr_code_app.Models.Response.PhotoResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ProfileImagesResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ProfileResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ProfileStatusResponse;
import com.studio.a4kings.qr_code_app.Models.Response.UserResponse;
import com.studio.a4kings.qr_code_app.Utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;

/**
 * Created by Dmitry Pavlenko on 14.03.2016.
 */
public class UserProfileService extends ApiManager implements ApiService {

    private ProfileInterface profileInterface;
    private CountryInterface countryInterface;

    public UserProfileService(String baseUrl) {
        super(baseUrl);
        this.profileInterface = getApiInterface(ProfileInterface.class);
        this.countryInterface = getApiInterface(CountryInterface.class);
    }

    @Override
    public <T> T getApiInterface(Class<T> clazz) {
        return super.getApiInterface(clazz);
    }

    @Override
    public <T> void setApiManager(T apiManager) {

    }

    public void getProfileAsync(@NotNull Callback<ProfileResponse> callback) {
        profileInterface.getProfile(this.OPERATION_TOKEN).enqueue(callback);
    }

    public void getImages(@NotNull Callback<ProfileImagesResponse> callback) {
        profileInterface.getImages(this.OPERATION_TOKEN).enqueue(callback);
    }

    public void getImages(String userId, @NotNull Callback<ProfileImagesResponse> callback) {
        profileInterface.getImages(this.OPERATION_TOKEN, userId).enqueue(callback);
    }


    public void getProfileAsyncById(String userId, @NotNull Callback<ProfileResponse> callback) {
        profileInterface.getProfileById(this.OPERATION_TOKEN, userId).enqueue(callback);
    }

    public void getUserAsync(@NotNull Callback<UserResponse> callback) throws IOException {
        profileInterface.getUser(this.OPERATION_TOKEN).enqueue(callback);
    }

    public void getProfileStatusAsync(@NotNull Callback<ProfileStatusResponse> callback) {
        profileInterface.getProfileStatus(this.OPERATION_TOKEN).enqueue(callback);
    }

    public void getCountriesAsync(@NotNull Callback<CountryResponse> callback) {
        countryInterface.getCountries(this.OPERATION_TOKEN).enqueue(callback);
    }

    public void updateProfileAsync(@NotNull MemberProfile profile, @NotNull Callback<ProfileResponse> callback) {

        profileInterface.updateProfile(this.OPERATION_TOKEN, profileToMap(profile)).enqueue(callback);
    }

    public void updatePhotoAsync(@NotNull String imageUrl, @NotNull Callback<PhotoResponse> callback) {
        profileInterface.updatePhoto(this.OPERATION_TOKEN, Utils.getInstance().uploadFileToBase64(imageUrl)).enqueue(callback);
    }

    public void updatePhotoAsync(@NotNull Bitmap imageBitmap, @NotNull Callback<PhotoResponse> callback) {
        profileInterface.updatePhoto(this.OPERATION_TOKEN, Utils.getInstance().bitmapToBase64(imageBitmap)).enqueue(callback);
    }

    public void updateWallPaperAsync(@NotNull String imageUrl, @NotNull Callback<PhotoResponse> callback) {
        profileInterface.updateWallPaper(this.OPERATION_TOKEN, Utils.getInstance().uploadFileToBase64(imageUrl)).enqueue(callback);
    }

    public void updateWallPaperAsync(@NotNull Bitmap imageBitmap, @NotNull Callback<PhotoResponse> callback) {
        profileInterface.updateWallPaper(this.OPERATION_TOKEN, Utils.getInstance().bitmapToBase64(imageBitmap)).enqueue(callback);
    }

    private Map<String, String> profileToMap(MemberProfile profile) {
        Map<String, String> args = new HashMap<String, String>();
        args.put("firstname", profile.getFirstName());
        args.put("lastname", profile.getLastName());
        args.put("birthday", profile.getBirthday());
        args.put("aboutme", profile.getAboutMe());
        args.put("gender", profile.getGender());
        return args;
    }

}
