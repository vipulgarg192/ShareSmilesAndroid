package com.cipher.sharesmilesandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cipher.sharesmilesandroid.BaseActivity;
import com.cipher.sharesmilesandroid.LoginActivity;
import com.cipher.sharesmilesandroid.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class IntroActivity extends BaseActivity {

    BaseActivity activity = IntroActivity.this;

//    @BindView(R.id.btnLogin)
//    MaterialButton btnLogin;
//
//    @BindView(R.id.btnSignIn)
//    MaterialButton btnSignUp;

    private static final String TAG = "IntroActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity);

        printHashKey(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId()== R.id.btnSignIn){
            startActivity(new Intent(activity , LoginActivity.class));
        }else if (v.getId() == R.id.btnLogin){
            startActivity(new Intent(activity, RegisterActivity.class));
        }else {
//           printToast(activity,"Something went wrong");
        }
    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }
}
