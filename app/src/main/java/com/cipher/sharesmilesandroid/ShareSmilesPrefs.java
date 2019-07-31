package com.cipher.sharesmilesandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

public class ShareSmilesPrefs  {

    public static final String isLogin = "IsLoginIn";

    public static final String userId = "UserID";
    public static final String emailId = "EmailID";
    public static final String userRole = "userRole";
    public static final String userName = "userName";

    static  final String PrefName = "ShareSmile1";
    static  final int Mode = Context.MODE_PRIVATE;

    public static SharedPreferences getPreference(Context context){
        return context.getSharedPreferences(PrefName,Mode);
    }

    public static SharedPreferences.Editor getEditor(Context context){
        return  getPreference(context).edit();
    }

    public static void  writeString(Context context , String key , String value){
        getEditor(context).putString(key,value).commit();
    }

    public static String readString (Context  context , String key , String defaultValue){
        return getPreference(context).getString(key,defaultValue);
    }

    public static void  writeInteger(Context context , String key , int value){
        getEditor(context).putInt(key,value).commit();
    }

    public static int readInteger (Context  context , String key , int defaultValue){
        return getPreference(context).getInt(key,defaultValue);
    }

    public static void  writeBool(Context context , String key , int value){
        getEditor(context).putInt(key,value).commit();
    }

    public static Boolean readBool (Context  context , String key , boolean defaultValue){
        return getPreference(context).getBoolean(key,defaultValue);
    }

    public static void logout(Context context){
        getPreference(context).edit().clear().commit();
    }

}
