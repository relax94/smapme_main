package com.studio.a4kings.qr_code_app.Utils;

import android.os.AsyncTask;
import com.studio.a4kings.qr_code_app.Interfaces.IInternetConnectionMethods;

import java.net.InetAddress;

/**
 * Created by DUX on 27.11.2015.
 */
public class InternetAvailability extends AsyncTask<Void, Void, Boolean>  {
    IInternetConnectionMethods internet;

    public InternetAvailability(IInternetConnectionMethods internet){
        this.internet = internet;
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }


    @Override
    protected Boolean doInBackground(Void... params) {
        return isInternetAvailable();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(internet != null){
            if(aBoolean)
                internet.onInternetAvailable();
            else{
                internet.onNoInternetAvailable();
            }
        }
    }
}
