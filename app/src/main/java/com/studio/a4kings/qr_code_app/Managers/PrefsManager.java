package com.studio.a4kings.qr_code_app.Managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.Member;
import com.studio.a4kings.qr_code_app.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

/**
 * Created by DUX on 09.01.2016.
 */
public class PrefsManager {

    private static PrefsManager _instance;
    Context mContext;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Hashtable<String,String> hashTable = new Hashtable<>();

    public static PrefsManager getInstance(Context context){
        if(_instance == null)
            _instance = new PrefsManager(context);
        return _instance;
    }

    public PrefsManager(Context context){
        this.mContext = context;
        preferences  = mContext.getSharedPreferences( mContext.getString(R.string.localStorage), Context.MODE_PRIVATE);
        hashTable.put(PrefsParam.TOKEN.name(),preferences.getString(PrefsParam.TOKEN.name(),""));
        hashTable.put(PrefsParam.MEMBER.name(),preferences.getString(PrefsParam.MEMBER.name(),""));
    }

    public String get(PrefsParam param){
        String temp = hashTable.get(param.name());
        if(TextUtils.isEmpty(temp)){
            temp = preferences.getString(param.name(), "");
            //hashTable.put(param.name(),temp);
        }
        return temp;
    }

    public String getFromCore(PrefsParam param){
        return preferences.getString(param.name(), "");
    }

    public void putMember(Member object){
        editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        editor.putString("MyObject", json);
        editor.apply();
    }

    public Member getMember() {
        Gson gson = new Gson();
        String json = preferences.getString("MyObject", "");
        return gson.fromJson(json, Member.class);
    }

    public void put(PrefsParam param, String paramValue){
        editor = preferences.edit();
        editor.putString(param.name(), paramValue);
        editor.apply();
    }

    public void clearAll(){
        editor = preferences.edit();
        editor.clear().apply();
    }
}
