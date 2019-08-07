package com.cipher.sharesmilesandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import com.cipher.sharesmilesandroid.BaseActivity;
import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.ShareSmiles;
import com.cipher.sharesmilesandroid.ShareSmilesSingleton;
import com.cipher.sharesmilesandroid.utilities.DialogBoxs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marozzi.roundbutton.RoundButton;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.facebook.appevents.internal.InAppPurchaseActivityLifecycleTracker.update;

public class EditProfile extends BaseActivity {

    BaseActivity activity = EditProfile.this;
    private static final String TAG = "EditProfile";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.imgProfile)
    AppCompatImageView imgProfile;
    @BindView(R.id.cdProfile)
    MaterialCardView cdProfile;
    @BindView(R.id.etFirstName)
    TextInputEditText etFirstName;
    @BindView(R.id.tilFirstName)
    TextInputLayout tilFirstName;
    @BindView(R.id.etLastName)
    TextInputEditText etLastName;
    @BindView(R.id.tilLastName)
    TextInputLayout tilLastName;
    @BindView(R.id.etDesc)
    TextInputEditText etDesc;
    @BindView(R.id.tilDesc)
    TextInputLayout tilDesc;
    @BindView(R.id.etPhone)
    TextInputEditText etPhone;
    @BindView(R.id.tilPhone)
    TextInputLayout tilPhone;
    @BindView(R.id.etAddress)
    TextInputEditText etAddress;
    @BindView(R.id.tilAddress)
    TextInputLayout tilAddress;
    @BindView(R.id.etCity)
    TextInputEditText etCity;
    @BindView(R.id.tilcity)
    TextInputLayout tilcity;
    @BindView(R.id.etZipcode)
    TextInputEditText etZipcode;
    @BindView(R.id.tilZipcode)
    TextInputLayout tilZipcode;
    @BindView(R.id.etDOB)
    TextInputEditText etDOB;
    @BindView(R.id.tildob)
    TextInputLayout tildob;
    @BindView(R.id.btUpdate)
    RoundButton btUpdate;

    private FirebaseAuth auth;

    private FirebaseFirestore dRef = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile_activity);
        ButterKnife.bind(activity);

        auth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }

    @OnClick({R.id.etCity, R.id.etDOB, R.id.btUpdate })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.etCity:
                ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,"city");
                break;
            case R.id.etDOB:
                ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,"dob");

                break;
            case R.id.btUpdate:
                if(valid()){
                    updateUser();
                }
                break;
        }
    }

    private void updateUser() {

        String userId = auth.getUid();

        Log.e(TAG, "updateUser: "+userId );

        HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("userId",userId);
        userMap.put("firstName", etFirstName.getText().toString());
        userMap.put("lastName",etLastName.getText().toString());
        userMap.put("description",etDesc.getText().toString());
        userMap.put("address",etAddress.getText().toString());

        DocumentReference newCityRef = dRef.collection("users").document(userId.toString());
        newCityRef.set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e(TAG, "onSuccess: "+aVoid.toString() );
                startActivity(new Intent(activity,MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "failure: " +e.getMessage());
            }
        });

    }

    private boolean valid() {
        String firstName = etFirstName.getText().toString().trim();
        if(firstName.isEmpty()){
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,getString(R.string.pleaseEnterFirstName));
            return false;
        }else if (etLastName.getText().toString().trim().isEmpty()){
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,getString(R.string.pleaseEnterLastName));
            return false;
        }else if (etAddress.getText().toString().trim().isEmpty()){
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,getString(R.string.pleaseEnterAddress));
            return false;
        }
        /*else if (etCity.getText().toString().trim().isEmpty()){
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,getString(R.string.pleaseSelectCity));
            return false;
        }else if (etZipcode.getText().toString().trim().isEmpty()){
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,getString(R.string.pleaseEnterZipCode));
            return false;
        }else if (etDOB.getText().toString().trim().isEmpty()){
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,getString(R.string.pleaseSelectDOB));
            return false;
        }*/
        return true;
    }
}
