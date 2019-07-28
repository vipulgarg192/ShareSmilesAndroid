package com.cipher.sharesmilesandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();


    }


    public void init(){

    }

    public void onClick(View v){

    }



    public void onStartTransition(AppCompatActivity appCompatActivity , AppCompatActivity nextActivity){

//        startActivity(new Intent(appCompatActivity,nextActivity));

    }

    public void onEndTransition(){

    }

    public void printToast(AppCompatActivity activity, String msg){
        Toast.makeText(activity, msg , Toast.LENGTH_SHORT).show();
    }



}
