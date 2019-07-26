package com.cipher.sharesmilesandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class LoginActivity extends BaseActivity {

    ImageView imgLogin;
    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
    }

    @Override
    public void init() {
        super.init();

        imgLogin = findViewById(R.id.imgLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v.getId()== R.id.btnLogin){
            Toast.makeText(this,"",Toast.LENGTH_SHORT).show();
        }
    }
}
