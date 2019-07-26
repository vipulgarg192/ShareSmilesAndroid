package com.cipher.sharesmilesandroid;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();


    }


    public void init(){

    }

    public void onClick(View v){




    }

    public void onStartTransition(){

    }

    public void onEndTransition(){

    }



}
