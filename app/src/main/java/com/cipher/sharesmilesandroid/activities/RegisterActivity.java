package com.cipher.sharesmilesandroid.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.cipher.sharesmilesandroid.BaseActivity;
import com.cipher.sharesmilesandroid.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.tilFirstName)
    TextInputLayout tilFirstName;

    @BindView(R.id.tilLastName)
    TextInputLayout tilLastName;

    @BindView(R.id.tilEmail)
    TextInputLayout tilEmail;

    @BindView(R.id.tilPassword)
    TextInputLayout tilPassword;

    @BindView(R.id.tilCPassword)
    TextInputLayout tilCPassword;

    @BindView(R.id.tilPhone)
    TextInputLayout tilPhone;

    @BindView(R.id.etFirstName)
    TextInputEditText etFirstName;

    @BindView(R.id.etLastName)
    TextInputEditText etLastName;

    @BindView(R.id.etEmail)
    TextInputEditText etEmail;

    @BindView(R.id.etPassword)
    TextInputEditText etPassword;

    @BindView(R.id.etCPassword)
    TextInputEditText etCPassword;

    @BindView(R.id.etPhone)
    TextInputEditText etPhone;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
    }
}
