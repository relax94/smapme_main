package com.studio.a4kings.qr_code_app.Managers;

import android.content.Context;

import com.studio.a4kings.qr_code_app.Interfaces.IStoreFunctionality;
import com.studio.a4kings.qr_code_app.Migrations.RealmCustomMigration;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Dmitry Pavlenko on 02.01.2016.
 */

//@Module
public class DbManager implements IStoreFunctionality {
    private Realm mRealmInstance;
    private Context mContext;

    /*@Provides
    @Singleton
    DbManager provideDbManager(Context context){
        return new DbManager(context);
    }
*/
    public  DbManager(){}

    public void setDbManagerWorkingContext(Context context){
        if(context != null) {
            this.mContext = context;
            this.mRealmInstance = Realm.getDefaultInstance();
        }
    }

    public DbManager(Context context){
       setDbManagerWorkingContext(context);
    }

    private RealmConfiguration getRealmConfiguration(){
        RealmConfiguration config = new RealmConfiguration.Builder(this.mContext)
                .schemaVersion(2) // Must be bumped when the schema changes
                .migration(new RealmCustomMigration()) // Migration to run instead of throwing an exception
                .build();
        return config;
    }

    public  <T extends RealmObject> T createSafeObject(Class<T> clazz){
        mRealmInstance.beginTransaction();
        T obj = mRealmInstance.createObject(clazz);
        mRealmInstance.commitTransaction();
        return obj;
    }

    public <T extends RealmObject> int getCollectionCount(Class<T> clazz){
        this.mRealmInstance.beginTransaction();
        RealmQuery<T> query = mRealmInstance.where(clazz);
        RealmResults<T> results = query.findAll();
        this.mRealmInstance.commitTransaction();
        return results.size();
    }

    public <T extends RealmObject> T save(T obj){
        mRealmInstance.beginTransaction();
        T response = mRealmInstance.copyToRealmOrUpdate(obj);
        mRealmInstance.commitTransaction();
        return response;
    }

    public <T extends RealmObject> List<T> findAll(Class<T> clazz){
        RealmQuery<T> query = mRealmInstance.where(clazz);
        RealmResults<T> results = query.findAll();
        return results.subList(0, results.size());
    }

    @Override
    public <T extends RealmObject> List<T> findByQuery(Class<T> clazz, String fieldName, String value) {
        RealmQuery<T> query = mRealmInstance.allObjects(clazz).where().equalTo(fieldName, value);
        return query.findAll();
    }

    public <T extends  RealmObject> void removeAllByClass(Class<T> clazz){
        mRealmInstance.beginTransaction();
        mRealmInstance.clear(clazz);
        mRealmInstance.commitTransaction();
    }

}
