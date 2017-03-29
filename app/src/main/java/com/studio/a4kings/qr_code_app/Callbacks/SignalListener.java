package com.studio.a4kings.qr_code_app.Callbacks;

import android.util.Log;

import com.studio.a4kings.qr_code_app.Models.Events.SignalEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;

/**
 * Created by Dmitry Pavlenko on 06.06.2016.
 */
public class SignalListener implements Emitter.Listener {
    @Override
    public void call(Object... args) {
        Log.d("SocketListener", "incoming event");
        if(args != null && args.length > 0){
            try{
                JSONObject reciveObj = (JSONObject)args[0];
                EventBus.getDefault().post(new SignalEvent(reciveObj.getString("method"), reciveObj.getString("body")));
            }catch (JSONException jsex){

            }
        }
    }
}
