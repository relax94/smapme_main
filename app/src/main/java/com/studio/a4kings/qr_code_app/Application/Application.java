package com.studio.a4kings.qr_code_app.Application;

import android.support.annotation.Nullable;
import android.util.Log;
import com.facebook.FacebookSdk;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by Dmitry Pavlenko on 22.12.2015.
 */
public class Application extends android.app.Application {
   // DIComponent component;
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            vkAccessTokenTracker.startTracking();
            VKSdk.initialize(this);
            FacebookSdk.sdkInitialize(getApplicationContext());

            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                                                        .name("default_db3")
                                                        .schemaVersion(3)
                                                        .deleteRealmIfMigrationNeeded()
                                                        .build();
            Realm.setDefaultConfiguration(realmConfiguration);

        //  component  = DaggerDIComponent.builder().build();
        }
        catch (Exception appException){
            Log.d("APPLICATION EXCEPTION ", appException.getMessage());
        }
    }

  /*  public DIComponent getComponent(){
        return component;
    }*/

    private VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker(){
        @Override
        public void onVKAccessTokenChanged(@Nullable VKAccessToken oldToken, @Nullable VKAccessToken newToken) {
            if(newToken == null)
                Log.d("VK ACCESS TOKEN INVALID", newToken.toString());
        }
    };
}
