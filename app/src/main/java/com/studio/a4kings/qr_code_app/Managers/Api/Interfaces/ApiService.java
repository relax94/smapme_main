package com.studio.a4kings.qr_code_app.Managers.Api.Interfaces;

/**
 * Created by Dmitry Pavlenko on 14.03.2016.
 */
public interface ApiService {
    <T> T getApiInterface(Class<T> clazz);
    <T> void setApiManager(T apiManager);
}
