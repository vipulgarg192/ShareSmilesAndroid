package com.cipher.sharesmilesandroid.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;

import com.cipher.sharesmilesandroid.BaseActivity;
import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.ShareSmiles;
import com.cipher.sharesmilesandroid.ShareSmilesPrefs;
import com.cipher.sharesmilesandroid.ShareSmilesSingleton;
import com.cipher.sharesmilesandroid.modals.ProductUser;
import com.cipher.sharesmilesandroid.modals.Users;
import com.cipher.sharesmilesandroid.utilities.DialogBoxs;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.marozzi.roundbutton.RoundButton;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class EditProfile extends BaseActivity{

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


        getUserProfile();
    }

    private void getUserProfile() {

        Users users = new Users();
        DocumentReference docRef = FirebaseFirestore.getInstance().collection("users").document(ShareSmilesPrefs.readString(getApplicationContext(),ShareSmilesPrefs.userId,null));


        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {

                String  firstName =  documentSnapshot.getData().get("firstName").toString();
                String  lastName =  documentSnapshot.getData().get("lastName").toString();

                String profileImage ="";
                if (documentSnapshot.getData().get("profilePic")!=null){
                    profileImage =  documentSnapshot.getData().get("profilePic").toString();
                }

                users.setFirstName(firstName);
                users.setLastName(lastName);
                users.setUserImage(profileImage);

                if (documentSnapshot.getData().get("description")!=null){
                    users.setDescription( documentSnapshot.getData().get("description").toString());
                }

                if (documentSnapshot.getData().get("dob")!=null){
                    users.setDob( documentSnapshot.getData().get("dob").toString());
                }

                if (documentSnapshot.getData().get("phone")!=null){
                    users.setPhone( documentSnapshot.getData().get("phone").toString());
                }

                if (documentSnapshot.getData().get("address")!=null){
                    users.setAddress( documentSnapshot.getData().get("address").toString());
                }

                if (documentSnapshot.getData().get("city")!=null){
                    users.setCity( documentSnapshot.getData().get("city").toString());
                }

                if (documentSnapshot.getData().get("zipcode")!=null){
                    users.setZipcode(documentSnapshot.getData().get("zipcode").toString());
                }

                etFirstName.setText(users.getFirstName());
                etLastName.setText(users.getLastName());
                etDesc.setText(users.getDescription());
                etPhone.setText(users.getPhone());
                etAddress.setText(users.getAddress());
                etCity.setText(users.getCity());
                etZipcode.setText(users.getZipcode());
                etDOB.setText(users.getDob());
                etFirstName.setSelection(users.getFirstName().length());


            }
        });
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

                Calendar date = Calendar.getInstance(TimeZone.getDefault());

                DatePickerDialog datePickerDialog =
                        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                String dob = datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+"/"+datePicker.getYear();
                                etDOB.setText(dob);

                            }
                        }, date.get(Calendar.YEAR)-18, date.get(Calendar.DATE), date.get(Calendar.MONTH));
//                datePickerDialog.getDatePicker().setMaxDate(1665199688);1665199688
                datePickerDialog.show();


                break;
            case R.id.btUpdate:
                if(valid()){
                    btUpdate.startAnimation();
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
        userMap.put("dob",etDOB.getText().toString());
        userMap.put("phone",etPhone.getText().toString());
        userMap.put("city",etCity.getText().toString());
        userMap.put("zipcode",etZipcode.getText().toString());

        DocumentReference newCityRef = dRef.collection("users").document(userId);
        newCityRef.set(userMap, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                btUpdate.setResultState(RoundButton.ResultState.SUCCESS);
                btUpdate.revertAnimation();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                btUpdate.setResultState(RoundButton.ResultState.FAILURE);
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
        else if (etCity.getText().toString().trim().isEmpty()){
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,getString(R.string.pleaseSelectCity));
            return false;
        }else if (etZipcode.getText().toString().trim().isEmpty()){
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,getString(R.string.pleaseEnterZipCode));
            return false;
        }else if (etDOB.getText().toString().trim().isEmpty()){
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,getString(R.string.pleaseSelectDOB));
            return false;
        }
        return true;
    }

}
