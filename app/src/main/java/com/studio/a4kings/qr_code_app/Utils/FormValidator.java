package com.studio.a4kings.qr_code_app.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by DUX on 15.01.2016.
 */
public class FormValidator {

    static final String emailRegEx = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    static final String passwordRegEx= "^[a-zA-Z0-9_-]{6,30}$";

    public static boolean isEmailValid(String email) {
        Pattern emailPattern =  Pattern.compile(emailRegEx);
        Matcher m = emailPattern.matcher(email);
        return m.matches();
    }

    public static boolean isPasswordValid(String password) {
        Pattern passwordPattern =  Pattern.compile(passwordRegEx);
        Matcher m = passwordPattern.matcher(password);
        return m.matches();
    }
}
