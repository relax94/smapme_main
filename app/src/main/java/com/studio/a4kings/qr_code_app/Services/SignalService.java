package com.studio.a4kings.qr_code_app.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.studio.a4kings.qr_code_app.Activities.MainActivity;
import com.studio.a4kings.qr_code_app.Callbacks.SignalListener;
import com.studio.a4kings.qr_code_app.Models.Events.SignalEvent;
import com.studio.a4kings.qr_code_app.Models.Events.SignalSentEvent;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.AppSettings;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Dmitry Pavlenko on 07.06.2016.
 */
public class SignalService extends Service implements Emitter.Listener{

    public static final String SERVICE_ACTION_TAG = "com.studio.a4kings.signalservice";
    private final String CONNECTION_STRING = AppSettings.TRIP_BACKEND;
    private Socket socket;
    private String connectionId;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            if(intent.hasExtra("connectionId")) {
                this.connectionId = intent.getStringExtra("connectionId");
                this.openConnection(this.connectionId);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    private void openConnection(final String connectionId){
        try {
            this.socket = IO.socket(CONNECTION_STRING);
            this.socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    socket.emit("registerConnection", connectionId);
                }
            }).on("serverResponse", this);
            this.socket.connect();

        }catch (URISyntaxException ex){

        }
    }

    @Override
    public void call(Object... args) {
        Log.d("SocketListener", "incoming event");
       showNotification("input notification ", "text");
      // Toast.makeText(getApplicationContext(), "income ", Toast.LENGTH_SHORT).show();
        if(args != null && args.length > 0){
            try{
                JSONObject reciveObj = (JSONObject)args[0];
                EventBus.getDefault().post(new SignalEvent(reciveObj.getString("method"), reciveObj.getString("body")));
            }catch (JSONException jsex){

            }
        }
    }


    private void showNotification(String title, String text){
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                         .setSmallIcon(R.drawable.ic_done)
                        .setContentTitle(title)
                        .setContentText(text);
        int NOTIFICATION_ID = 12345;

       // Intent targetIntent = new Intent(this, MyFavoriteActivity.class);
       // PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
      //  builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Subscribe
    public void sendToServer(SignalSentEvent signalSentEvent){
        if(signalSentEvent != null)
            this.socket.emit(signalSentEvent.getMessage(), signalSentEvent.getArg());
    }

}
