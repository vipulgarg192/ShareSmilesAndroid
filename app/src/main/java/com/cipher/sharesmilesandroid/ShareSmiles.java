package com.cipher.sharesmilesandroid;

import android.app.Application;
import android.app.Dialog;

import com.cipher.sharesmilesandroid.utilities.DialogBoxs;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class ShareSmiles extends Application {

    static DialogBoxs dialogBoxs ;

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

//    private static volatile ShareSmiles mInstance = new ShareSmiles();
//
//    //private constructor.
//    private ShareSmiles(){}
//
//    public static ShareSmiles getInstance() {
//        dialogBoxs = new DialogBoxs();
//        return mInstance;
//    }
//
//    public DialogBoxs getDialogBoxs(){
//        dialogBoxs = new DialogBoxs();
//        return dialogBoxs;
//
//    }
}
