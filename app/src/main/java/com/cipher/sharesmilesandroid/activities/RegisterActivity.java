package com.cipher.sharesmilesandroid.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cipher.sharesmilesandroid.BaseActivity;
import com.cipher.sharesmilesandroid.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity {

    AppCompatActivity activity = RegisterActivity.this;
    private static final String TAG = "RegisterActivity";

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

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");

        myRef.setValue("Hello, World!");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.e(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
