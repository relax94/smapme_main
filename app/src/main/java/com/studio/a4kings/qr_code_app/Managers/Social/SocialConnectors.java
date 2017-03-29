package com.studio.a4kings.qr_code_app.Managers.Social;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.studio.a4kings.qr_code_app.Callbacks.FacebookCallbacks;
import com.studio.a4kings.qr_code_app.Callbacks.VkCallbacks;
import com.studio.a4kings.qr_code_app.Interfaces.BroadcastMethods;
import com.studio.a4kings.qr_code_app.Interfaces.IAuthentication;
import com.studio.a4kings.qr_code_app.Models.Member;
import com.studio.a4kings.qr_code_app.Models.Enums.AccountType;
import com.vk.sdk.VKSdk;
import com.vk.sdk.util.VKUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by Dmitry Pavlenko on 22.12.2015.
 */
public class SocialConnectors implements BroadcastMethods {

    private static SocialConnectors _instance;
    private Context mContext;
    private FragmentActivity mActivity;
    private IAuthentication mProvideAuthentication;

    private GoogleApiClient mGoogleApiClient;
    public static final int RC_SIGN_IN = 9001;
    public static final int FB_SIGN_IN = 64206;
    private GoogleSignInOptions gso;
    private  CallbackManager callbackManager;
    private LoginManager fbLoginManager ;
    private AccessTokenTracker mAccessTokenTracker;


    public static SocialConnectors getInstance(Context context){
        if(_instance == null)
            _instance = new SocialConnectors(context);
        return _instance;
    }
    public SocialConnectors(Context context){
        this.mContext = context;
        this.mActivity = (FragmentActivity)context;
        this.mProvideAuthentication = (IAuthentication)context;
        this.callbackManager = CallbackManager.Factory.create();
        this.mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
            }
        };
    }


    public void initGPlusConnector(GoogleSignInOptions googleSignInOptions){
        gso = new GoogleSignInOptions.Builder(googleSignInOptions)
                .requestScopes(new Scope(Scopes.PLUS_LOGIN))
                .requestEmail()

                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this.mContext)
                .enableAutoManage(mActivity, gPlusFailed)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();

//        this.setGPlusButtonParams();
    }
    /*private void setGPlusButtonParams(){
        SignInButton signInButton = (SignInButton)mActivity.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
    }*/
    public void gPlusSignIn(){
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            mActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void gPlusSignOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]

                        // [END_EXCLUDE]
                    }
                });
    }
    private GoogleApiClient.OnConnectionFailedListener gPlusFailed = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            Toast.makeText(mContext, "GPlus connection failed : " + connectionResult, Toast.LENGTH_LONG).show();
        }
    };
    public void handleGPlusSignInResult(Intent data) {
        String TAG = "GAUTH";
        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        Log.d("GSI", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Person person  = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
       /*     Log.i(TAG, "--------------------------------");
            Log.i(TAG, "Display Name: " + person.getDisplayName());
            Log.i(TAG, "Gender: " + person.getGender());
            Log.i(TAG, "AboutMe: " + person.getAboutMe());
            Log.i(TAG, "Birthday: " + person.getBirthday());
            Log.i(TAG, "Current Location: " + person.getCurrentLocation());
            Log.i(TAG, "Language: " + person.getLanguage());*/
            Member member = new Member();
            String name = acct.getDisplayName();
            member.setNameSignature(name);
            member.setEmail(acct.getEmail());
            member.getProfile().setFirstName(name.split(" ")[0].trim());
            member.getProfile().setLastName(name.split(" ")[1].trim());
            member.setSocialId(acct.getId());
            member.setAccountType(AccountType.GPlus.ordinal());
            if(person != null) {
                member.getProfile().setGender(person.getGender() + "");
                member.getProfile().setBirthday(person.hasBirthday() ? person.getBirthday() : person.getAgeRange().getMin() + " years");
            }
            BroadcastAuthenticationResult(true, member);

        } else {
            // Signed out, show unauthenticated UI.
            BroadcastAuthenticationResult(false, null);
        }
    }




    @Override
    public void BroadcastAuthenticationResult(boolean result, Member data){
        if(mProvideAuthentication != null)
            mProvideAuthentication.authenticationResult(result, data);
    }





    public boolean isVKConnected(){
        return VKSdk.isLoggedIn();
    }
    public void vkSignIn(String []scopes){
       // if(!isVKConnected()){
            VKSdk.login(this.mActivity,scopes);
        //}
    }
    public void vkSignOut(){
        VKSdk.logout();
    }
    public boolean isVKActivityResult(int requestCode, int resultCode, Intent data){
        VkCallbacks vkCallback = new VkCallbacks(this);
        return VKSdk.onActivityResult(requestCode,resultCode,data, vkCallback);
    }





    /*public void initFBLoginWithUI(){
        FacebookCallbacks<LoginResult> loginFBCb = new FacebookCallbacks<>(this);
        LoginButton loginButton = (LoginButton)this.mActivity.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(callbackManager, loginFBCb);
    }*/

    public void fbSignIn(List<String> scopes){
        this.fbLoginManager = LoginManager.getInstance();
        FacebookCallbacks<LoginResult> loginFBCb = new FacebookCallbacks<>(this);
        this.fbLoginManager.registerCallback(callbackManager, loginFBCb);
        this.fbLoginManager.logInWithReadPermissions(this.mActivity, scopes);
    }
    public void fbSignOut(){
        if(this.fbLoginManager != null)
            this.fbLoginManager.logOut();
    }
    public void handleFBResult(int requestCode, int resultCode, Intent data){
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }






    public String[] getFingerprints(){
        return VKUtil.getCertificateFingerprint(this.mContext, this.mContext.getPackageName());
    }

    public  void showHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.studio.a4kings.qr_code_app", PackageManager.GET_SIGNATURES); //Your package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.v("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

}
