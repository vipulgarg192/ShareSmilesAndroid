package com.cipher.sharesmilesandroid.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.cipher.sharesmilesandroid.BaseActivity;



public class SplashActivity extends BaseActivity {

    BaseActivity activity = SplashActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(activity, IntroActivity.class));
        finish();
    }
}
