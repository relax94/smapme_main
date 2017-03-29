package com.studio.a4kings.qr_code_app.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.studio.a4kings.qr_code_app.Callbacks.ResponseCallback;
import com.studio.a4kings.qr_code_app.Callbacks.ResponseMethods;
import com.studio.a4kings.qr_code_app.Interfaces.IInternetConnectionMethods;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.UserProfileService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.Enums.ProfileStatus;
import com.studio.a4kings.qr_code_app.Models.Response.MainResponse;
import com.studio.a4kings.qr_code_app.Models.Response.ProfileStatusResponse;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.Constants;
import com.studio.a4kings.qr_code_app.Utils.InternetAvailability;

import java.io.IOException;

import retrofit2.Response;


public class SplashScreenActivity extends AppCompatActivity implements IInternetConnectionMethods {

    AlertDialog alert;
    Context context;
    InternetAvailability internetAvailability;
    UserProfileService userProfileService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        context = this;
        userProfileService = new UserProfileService(Constants.SITE_URL);
        userProfileService.setOperationToken(PrefsManager.getInstance(context).getFromCore(PrefsParam.TOKEN));
        startCheckInternet();
        createDialog();
    }


    private void startCheckInternet() {
        internetAvailability = new InternetAvailability((IInternetConnectionMethods) context);
        internetAvailability.execute();
    }

    private void createDialog() {
        alert = new AlertDialog.Builder(this)
                .setTitle("You don't have internet")
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                        startCheckInternet();
                    }
                })
                .setCancelable(false)
                .create();
    }

    @Override
    public void onInternetAvailable() {
            userProfileService.getProfileStatusAsync(
                    new ResponseCallback<ProfileStatusResponse>(new ResponseMethods() {
                        @Override
                        public void onSuccess(Response<? extends MainResponse> response1) {
                            ProfileStatusResponse response = (ProfileStatusResponse) response1.body();
                            Intent intent = new Intent(context, GreetingWindow.class);
                            if (!(response.getStatus() == -1)) {
                                ProfileStatus status = ProfileStatus.values()[response.getStatus()];
                                if (status == ProfileStatus.Updated) {
                                    intent = new Intent(context, MainActivity.class);
                                } else if (status == ProfileStatus.Created) {
                                    intent = new Intent(context, FillProfileForm.class);
                                }
                            }
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onNullBody() {
                            startActivity(new Intent(context, GreetingWindow.class));
                            finish();
                        }

                        @Override
                        public void onError(Throwable t) {
                            Toast.makeText(SplashScreenActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }));
    }

    @Override
    public void onNoInternetAvailable() {
        alert.show();
    }
}
