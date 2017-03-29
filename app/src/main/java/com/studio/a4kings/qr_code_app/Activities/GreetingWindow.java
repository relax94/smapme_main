package com.studio.a4kings.qr_code_app.Activities;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.studio.a4kings.qr_code_app.Adapters.PagerAdapter;
import com.studio.a4kings.qr_code_app.Fragments.SignIn;
import com.studio.a4kings.qr_code_app.Fragments.SignUp;
import com.studio.a4kings.qr_code_app.Interfaces.IAuthentication;
import com.studio.a4kings.qr_code_app.Interfaces.IInternetConnectionMethods;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.SignInUpService;
import com.studio.a4kings.qr_code_app.Managers.Api.Services.UserProfileService;
import com.studio.a4kings.qr_code_app.Managers.PrefsManager;
import com.studio.a4kings.qr_code_app.Managers.SignalManager;
import com.studio.a4kings.qr_code_app.Managers.Social.SocialConnectors;
import com.studio.a4kings.qr_code_app.Models.Enums.PrefsParam;
import com.studio.a4kings.qr_code_app.Models.Enums.ProfileStatus;
import com.studio.a4kings.qr_code_app.Models.Member;
import com.studio.a4kings.qr_code_app.Models.Response.ProfileStatusResponse;
import com.studio.a4kings.qr_code_app.Models.Response.SignResponse;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.AppSettings;
import com.studio.a4kings.qr_code_app.Utils.Constants;
import com.studio.a4kings.qr_code_app.Utils.FormValidator;
import com.studio.a4kings.qr_code_app.Utils.InternetAvailability;
import com.studio.a4kings.qr_code_app.Utils.Helper;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GreetingWindow extends AppCompatActivity implements IAuthentication, IInternetConnectionMethods, Callback<SignResponse> {


    @Bind(R.id.progress)
    View mProgressView;
    private SocialConnectors socialConnectors;
    private Member member;
    Context context;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    SignInUpService signInUpService;
    UserProfileService userProfileService;


    private void setMember(String email, String password) {
        member = new Member();
        member.setAccountType(0);
        member.setEmail(email);
        member.setPassword(password);
        Helper.swapViews(mProgressView, viewPager);
    }

    public void SignUp(String email, String password) {
        setMember(email, password);

        signInUpService.signUpAsync(member, this);
    }

    public void SignIn(String email, String password) {
        setMember(email, password);
        signInUpService.signInAsync(member, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting_window);
        ButterKnife.bind(this);
        context = this;

        this.signInUpService = new SignInUpService(Constants.SITE_URL);
        this.userProfileService = new UserProfileService(Constants.SITE_URL);

        socialConnectors = SocialConnectors.getInstance(this);
        socialConnectors.initGPlusConnector(GoogleSignInOptions.DEFAULT_SIGN_IN);
        //socialConnectors.initFBLogin();
        socialConnectors.getFingerprints();
        socialConnectors.showHashKey(context);
        Helper.swapViews(mProgressView, viewPager);
        InternetAvailability internetAvailability = new InternetAvailability(this);
        internetAvailability.execute();
        createInputDialog();
        initViewPagerAndTabs();

       // socialConnectors.showHashKey(this);


    }

    private void initViewPagerAndTabs() {
        PagerAdapter pagerAdapter = new PagerAdapter(this.getSupportFragmentManager());
        pagerAdapter.addFragment(new SignIn(), "LOGIN");
        pagerAdapter.addFragment(new SignUp(), "SIGN UP");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void authenticationResult(boolean isAuthenticate, Member member) {
        Toast.makeText(context, "Authentication result " + isAuthenticate, Toast.LENGTH_SHORT).show();

        if (isAuthenticate) {
            this.member = member;
            member.setPassword("");
            if (member.getAccountType() == 1) {
                mAlertDialog.show();
            } else {
                Helper.swapViews(mProgressView, viewPager);
                signInUpService.signInAsync(member, this);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!socialConnectors.isVKActivityResult(requestCode, resultCode, data)) {
            if (requestCode == SocialConnectors.RC_SIGN_IN) {
                socialConnectors.handleGPlusSignInResult(data);
            }
            socialConnectors.handleFBResult(requestCode, resultCode, data);

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onInternetAvailable() {
        Helper.swapViews(viewPager, mProgressView);
    }

    @Override
    public void onNoInternetAvailable() {
        //TODO: do something when user don't have internet
        Toast.makeText(this, "You don't have internet", Toast.LENGTH_LONG).show();
        Helper.swapViews(viewPager, mProgressView);
    }

    AlertDialog mAlertDialog;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void createInputDialog() {
        AlertDialog.Builder sayWindows = new AlertDialog.Builder(
                context);
        final EditText saySomething = new EditText(context);
        sayWindows.setPositiveButton("send", null)
                .setTitle("Email verification")
                .setMessage("Please enter your email")
                .setView(saySomething);

        mAlertDialog = sayWindows.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {

                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        Editable text = saySomething.getText();
                        if (FormValidator.isEmailValid(text.toString())) {
                            member.setEmail(text.toString());
                            Helper.swapViews(mProgressView, viewPager);
                            signInUpService.signInAsync(member, (Callback<SignResponse>) context);
                            mAlertDialog.dismiss();
                        } else
                            Toast.makeText(GreetingWindow.this, "Not valid email", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    Callback<ProfileStatusResponse> profileStatusResponseCallback = new Callback<ProfileStatusResponse>() {
        @Override
        public void onResponse(Call<ProfileStatusResponse> call, Response<ProfileStatusResponse> response) {
            if (response.body() != null) {
                if (response.body().getCode() == 200) {
                    member.getProfile().setStatus(response.body().getStatus());

                    Intent intent;
                    if (response.body().getStatus() == -1)
                        intent = new Intent(context, FillProfileForm.class);
                    else {
                        ProfileStatus status = ProfileStatus.values()[response.body().getStatus()];
                        if (status == ProfileStatus.Updated) {
                            intent = new Intent(context, MainActivity.class);
                        } else if (status == ProfileStatus.Created) {
                            intent = new Intent(context, FillProfileForm.class);
                            intent.putExtra("profile", member);
                        } else return;
                    }
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    Helper.swapViews(viewPager, mProgressView);
                    startActivity(intent);
                }
                else
                    Toast.makeText(GreetingWindow.this, response.message(), Toast.LENGTH_SHORT).show();
            } else {
                Helper.swapViews(viewPager, mProgressView);
                Toast.makeText(context, "error on server(get status)", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<ProfileStatusResponse> call, Throwable t) {
            Helper.swapViews(mProgressView, viewPager);
            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onResponse(Call<SignResponse> call, Response<SignResponse> response) {
        if (response.body() != null) {
            if (response.body().getAccess_token() != null) {
                PrefsManager.getInstance(context).put(PrefsParam.TOKEN, response.body().getAccess_token());
                PrefsManager.getInstance(context).put(PrefsParam.USER_ID, response.body().getUserId());
                PrefsManager.getInstance(context).putMember(member);
                userProfileService.setOperationToken(response.body().getAccess_token());
                userProfileService.getProfileStatusAsync(profileStatusResponseCallback);
            } else if (response.body().getCode() != null && response.body().getCode() == 200) {
                signInUpService.signInAsync(member, this);
            }
        } else {
            Helper.swapViews(viewPager, mProgressView);
            Toast.makeText(context, "error on server(log in)", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<SignResponse> call, Throwable t) {
        Helper.swapViews(viewPager, mProgressView);
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}