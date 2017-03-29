package com.studio.a4kings.qr_code_app.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Base64;
import android.util.Log;

import com.studio.a4kings.qr_code_app.Models.Member;
import com.studio.a4kings.qr_code_app.Models.MemberProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

/**
 * Created by Dmitry Pavlenko on 23.12.2015.
 */
public class Utils {

    private static Utils _instance;
    public static final String LOG_TAG = "UTILS_ERROR";

    public static Utils getInstance(){
        if(_instance == null)
            _instance = new Utils();
        return _instance;
    }

    public void redirectToActivity(Context context, Class activity){
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    public String getCurrentTimestamp(){
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        return ts;
    }

    public String uploadFileToBase64(String path){
        Bitmap bm = BitmapFactory.decodeFile(path);
        return bitmapToBase64(bm);
    }

    public String bitmapToBase64(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public Bitmap drawBackgroundForBitmap(Bitmap source, int backgroundColor){
        try {
            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
            Bitmap bmp = Bitmap.createBitmap(64, 64, conf);
            Canvas canvas = new Canvas(bmp);
           // canvas.drawARGB(255, 255, 0, 0);
            canvas.drawColor(backgroundColor);
            canvas.drawBitmap(source, 0, 0, new Paint());

            return bmp;
        }
        catch (Exception ex){
            Log.d(LOG_TAG, "Exception, when draw bitmap = " + ex.getMessage());
            return source;
        }
    }

    public MemberProfile stringToMember(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        MemberProfile profile = new MemberProfile();
        profile.setLastName(jsonObject.getString("LastName"));
        profile.setUserId(jsonObject.getString("UserId"));
        profile.setGender(jsonObject.getString("Gender"));
        profile.setFirstName(jsonObject.getString("FirstName"));
        profile.setAboutMe(jsonObject.getString("AboutMe"));
        profile.setStatus(jsonObject.getInt("Status"));
        profile.setPhoto(jsonObject.getString("Photo"));
        profile.setWallpaper(jsonObject.getString("Wallpaper"));
        profile.setBirthday(jsonObject.getString("Birthday"));
        return profile;
    }


    /*public redirectToActivityWithData(Context context, Class activity, ){
        Intent intent = new Intent(context, activity);
        intent.put
    }*/

}
