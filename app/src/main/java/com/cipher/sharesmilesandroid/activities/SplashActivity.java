package com.cipher.sharesmilesandroid.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.cipher.sharesmilesandroid.BaseActivity;
import com.cipher.sharesmilesandroid.LoginActivity;
import com.cipher.sharesmilesandroid.R;


public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_acitivity);

        startActivity(new Intent(this, IntroActivity.class));
    }
}
