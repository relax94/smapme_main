package com.studio.a4kings.qr_code_app.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.studio.a4kings.qr_code_app.Activities.GreetingWindow;
import com.studio.a4kings.qr_code_app.Managers.Social.SocialConnectors;
import com.studio.a4kings.qr_code_app.R;
import com.studio.a4kings.qr_code_app.Utils.FormValidator;
import com.vk.sdk.VKScope;

import java.util.Arrays;

public class SignIn extends Fragment implements View.OnClickListener {

    private SocialConnectors socialConnectors;
    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        socialConnectors = SocialConnectors.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_sign_in, container, false);

        v.findViewById(R.id.vkSignIn).setOnClickListener(this);
        v.findViewById(R.id.fbSignIn).setOnClickListener(this);
        v.findViewById(R.id.gplSignIn).setOnClickListener(this);

        mEmailView = (EditText)v.findViewById(R.id.email);
        mPasswordView = (EditText) v.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) v.findViewById(R.id.login_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.vkSignIn:
                socialConnectors.vkSignOut();
                socialConnectors.vkSignIn(new String[]{VKScope.OFFLINE});
                break;
            case R.id.fbSignIn:
                socialConnectors.fbSignOut();
                socialConnectors.fbSignIn(Arrays.asList("email", "user_friends, user_birthday, user_hometown"));
                break;
            case R.id.gplSignIn:
                socialConnectors.gPlusSignOut();
                socialConnectors.gPlusSignIn();
                break;
        }
    }

    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;



        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !FormValidator.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!FormValidator.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            ((GreetingWindow) getActivity()).SignIn(email,password);
        }
    }
}
