package com.studio.a4kings.qr_code_app.Api.Interface;

import com.studio.a4kings.qr_code_app.Models.Response.SignResponse;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by hack on 24.01.16.
 */
public interface SignInUpInterface {

    @FormUrlEncoded
    @POST("/api/account/register")
    retrofit2.Call<SignResponse> signUpApi(@Field("email") String email, @Field("password") String password);

    @POST("/api/account/register")
    @FormUrlEncoded
    retrofit2.Call<SignResponse> signUpApiWithMap(@FieldMap Map<String, String> args);

    @POST("/token")
    @FormUrlEncoded
    retrofit2.Call<SignResponse> signInApiWithMap(@FieldMap Map<String, String> args);

}
