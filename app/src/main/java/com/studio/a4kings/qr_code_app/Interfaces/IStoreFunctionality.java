package com.studio.a4kings.qr_code_app.Interfaces;

import java.util.List;

import io.realm.RealmConfiguration;
import io.realm.RealmObject;

/**
 * Created by Dmitry Pavlenko on 08.01.2016.
 */
public interface IStoreFunctionality {
    <T extends RealmObject> T createSafeObject(Class<T> clazz);
    <T extends RealmObject> int getCollectionCount(Class<T> clazz);
    <T extends RealmObject> T save(T obj);
    <T extends RealmObject> List<T> findAll(Class<T> clazz);
    <T extends RealmObject> List<T> findByQuery(Class<T> clazz,  String fieldName, String value);
}
