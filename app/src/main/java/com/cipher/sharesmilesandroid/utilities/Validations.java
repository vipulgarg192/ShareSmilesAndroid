package com.cipher.sharesmilesandroid.utilities;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Pattern;

public class Validations {

    final static Pattern USER_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9@.#$%^&*_&\\\\]+$");


    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public static boolean isValidPassword(CharSequence target) {
        return (!TextUtils.isEmpty(target) && USER_NAME_PATTERN.matcher(target).matches());
    }

}
