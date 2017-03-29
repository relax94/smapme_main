package com.studio.a4kings.qr_code_app.Managers;


import com.studio.a4kings.qr_code_app.Utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by hack on 24.01.16.
 */
public abstract class ApiManager {
    private Retrofit retrofit;
    private OkHttpClient.Builder okHttpClientBuilder;
    private final String API_ENDPOINT;
    public String OPERATION_TOKEN;
    private final int CONNECT_TIMEOUT = 15;
    private final int WRITE_TIMEOUT = 60;
    private final int TIMEOUT = 60;
    private OkHttpClient okHttpClient;

    public ApiManager(String baseUrl) {
        this.API_ENDPOINT = baseUrl;
        this.okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS);
        this.okHttpClient = this.okHttpClientBuilder.build();
        this.retrofit = getRetrofit(this.okHttpClient);
    }

    private Retrofit getRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(this.API_ENDPOINT)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
    }

    public <T> T getApiInterface(Class<T> clazz) {
        return this.retrofit.create(clazz);

    }

    public void setOperationToken(String operationToken) {
        this.OPERATION_TOKEN = String.format("Bearer %s", operationToken);
    }
}
