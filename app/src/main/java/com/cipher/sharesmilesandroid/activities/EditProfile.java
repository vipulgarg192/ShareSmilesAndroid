package com.cipher.sharesmilesandroid.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.marozzi.roundbutton.RoundButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
    @BindView(R.id.imgAddprofile)
    AppCompatImageView imgAddprofile;
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

    String strImg="";
    public static final int REQUEST_IMAGE = 100;

    FirebaseStorage storage ;
    StorageReference storageRef ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile_activity);
        ButterKnife.bind(activity);

        auth = FirebaseAuth.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        storage = FirebaseStorage.getInstance("gs://sharesmilesandroid.appspot.com/");
        storageRef = storage.getReference();

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
                    ShareSmilesPrefs.writeString(getApplication(),ShareSmilesPrefs.userPic,profileImage);
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


                if (!profileImage.equalsIgnoreCase("")){
                    Glide.with(activity).load(profileImage).into(imgAddprofile);
                }
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

    @OnClick({R.id.etCity, R.id.etDOB, R.id.btUpdate , R.id.imgAddprofile})
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
                    if (!strImg.isEmpty()){
                        try {
                            Uri file = Uri.fromFile(new File(strImg));

                            StorageReference mountainsRef = storageRef.child(file.getLastPathSegment());

                            Bitmap bitmap = ((BitmapDrawable) imgAddprofile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();

                            UploadTask uploadTask = mountainsRef.putBytes(data);

                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    exception.printStackTrace();
                                    // Handle unsuccessful uploads
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // getting image uri and converting into string
                                            Uri downloadUrl = uri;
                                            strImg = downloadUrl.toString();
                                            Log.e(TAG, "onSuccess: "+strImg );
                                            ShareSmilesPrefs.writeString(getApplication(),ShareSmilesPrefs.userPic,strImg);
                                            updateUser();

                                        }
                                    });
                                }
                            });

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        updateUser();
                    }

                }
                break;

            case R.id.imgAddprofile:
                Dexter.withActivity(this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                if (report.areAllPermissionsGranted()) {
                                    showImagePickerOptions();
                                }

                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
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
        userMap.put("profilePic",strImg);

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


    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(activity, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(activity, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    private void loadProfile(String url) {
        strImg = url;
        Glide.with(this).load(url)
                .into(imgAddprofile);
        imgAddprofile.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

}
