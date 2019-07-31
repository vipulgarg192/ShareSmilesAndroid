package com.cipher.sharesmilesandroid.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.cipher.sharesmilesandroid.BaseActivity;
import com.cipher.sharesmilesandroid.ShareSmilesPrefs;


public class SplashActivity extends BaseActivity {

    BaseActivity activity = SplashActivity.this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isLoggedIn = ShareSmilesPrefs.readBool(activity, ShareSmilesPrefs.isLogin, false);


        if (isLoggedIn){
            startActivity(new Intent(activity, MainActivity.class));
        }else {
            startActivity(new Intent(activity, IntroActivity.class));
        }

        finish();
    }
}
