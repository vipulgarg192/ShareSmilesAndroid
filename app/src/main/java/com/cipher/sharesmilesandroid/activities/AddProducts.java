package com.cipher.sharesmilesandroid.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.cipher.sharesmilesandroid.BaseActivity;
import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.ShareSmilesPrefs;
import com.cipher.sharesmilesandroid.ShareSmilesSingleton;
import com.cipher.sharesmilesandroid.chipsSet.Chip;
import com.cipher.sharesmilesandroid.chipsSet.ChipView;
import com.cipher.sharesmilesandroid.chipsSet.ChipViewAdapter;
import com.cipher.sharesmilesandroid.chipsSet.MainChipViewAdapter;
import com.cipher.sharesmilesandroid.chipsSet.OnChipClickListener;
import com.cipher.sharesmilesandroid.chipsSet.Tag;
import com.cipher.sharesmilesandroid.interfaces.BottomSheetInterface;
import com.cipher.sharesmilesandroid.interfaces.DrawableClickListener;
import com.cipher.sharesmilesandroid.modals.Organisations;
import com.cipher.sharesmilesandroid.ui.BottomSheetFragment;
import com.cipher.sharesmilesandroid.ui.CustomDrawableEditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddProducts extends BaseActivity implements OnChipClickListener, BottomSheetInterface {


    private static final String TAG = "AddProducts";
    AppCompatActivity activity = AddProducts.this;

    @BindView(R.id.imgToolbar)
    AppCompatImageView imgToolbar;
    @BindView(R.id.tbHome)
    Toolbar tbHome;
    @BindView(R.id.tbSimple)
    Toolbar tbSimple;
    @BindView(R.id.appBar)
    AppBarLayout appBar;

    @BindView(R.id.etName)
    CustomDrawableEditText etName;
    @BindView(R.id.etDesc)
    CustomDrawableEditText etDesc;
    @BindView(R.id.etPrice)
    CustomDrawableEditText etPrice;

    @BindView(R.id.tvCategory)
    AppCompatTextView tvCategory;

    @BindView(R.id.tags)
    CustomDrawableEditText tags;

    @BindView(R.id.tvOrganisation)
    AppCompatTextView tvOrganisation;

    @BindView(R.id.tvAllTags)
    AppCompatEditText tvAllTags;

    @BindView(R.id.chipView)
    ChipView chipView;

    @BindView(R.id.bt_round)
    RoundButton btRound;

    @BindView(R.id.imgItem)
    AppCompatImageView imgItem;

    int SpannedLength = 0, chipLength = 20;

    boolean newchip = false;

    private FirebaseAuth auth;
    private FirebaseFirestore dRef = FirebaseFirestore.getInstance();


    int beforeSize = 0, afterSize = 0;


    ArrayList<Chip> tagList = new ArrayList<>();

    ArrayList<Organisations> organisationsArrayList = new ArrayList<>();
    ArrayList<String> organisationList = new ArrayList<>();


    BottomSheetInterface bottomSheetInterface;
    BottomSheetFragment bottomSheetFragment;

    private int organisationId;

    private BottomSheetBehavior mBottomSheetBehavior;


    public static final int REQUEST_IMAGE = 100;


    private String itemImg = "";

    public interface PickerOptionListener {
        void onTakeCameraSelected();

        void onChooseGallerySelected();
    }
    FirebaseStorage storage ;
    StorageReference storageRef ;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.addproduct_activity);
        ButterKnife.bind(activity);

        storage = FirebaseStorage.getInstance("gs://sharesmilesandroid.appspot.com/");
        storageRef = storage.getReference();

        imgItem.setDrawingCacheEnabled(true);
        imgItem.buildDrawingCache();

        bottomSheetInterface = this;


        organisationsArrayList.add(new Organisations(1, "World Vision Canada"));
        organisationsArrayList.add(new Organisations(2, "The Canadian Red Cross Society"));
        organisationsArrayList.add(new Organisations(3, "The Church Of Jesus Christ Of Latter-Day Saints In Canada"));
        organisationsArrayList.add(new Organisations(4, "Jewish Community Foundation Of Montreal"));

        organisationsArrayList.add(new Organisations(5, "CanadaHelps Canadon"));
        organisationsArrayList.add(new Organisations(6, "Plan International Canada Inc."));
        organisationsArrayList.add(new Organisations(7, "United Way Of Greater Toronto"));
        organisationsArrayList.add(new Organisations(8, "Heart And Stroke Foundation Of Canada"));

        organisationsArrayList.add(new Organisations(9, "Redemption Ministries Inc."));
        organisationsArrayList.add(new Organisations(10, "Doctors Without Borders"));
        organisationsArrayList.add(new Organisations(11, "Vancouver Foundation"));
        organisationsArrayList.add(new Organisations(12, "Bc Cancer Foundation"));

        for (int i = 0; i < organisationsArrayList.size(); i++) {

            organisationList.add(organisationsArrayList.get(i).getOrganisationName());

        }

        tbSimple.setVisibility(View.VISIBLE);
        tbHome.setVisibility(View.GONE);

        ChipViewAdapter adapterLayout = new MainChipViewAdapter(this);


        chipView.setAdapter(adapterLayout);
        chipView.setChipLayoutRes(R.layout.chip_close);
        chipView.setChipBackgroundColor(getResources().getColor(R.color.md_white_1000));
        chipView.setChipBackgroundColorSelected(getResources().getColor(R.color.md_blue_grey_500));

        chipView.setOnChipClickListener(this);

        setSupportActionBar(tbSimple);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tbSimple.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


        etName.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        etDesc.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        etPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);


        btRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final RoundButton bt = (RoundButton) view;
                bt.startAnimation();
                bt.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bt.revertAnimation();
                    }
                }, 3000);

            }
        });
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    etName.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getDrawable(R.drawable.ic_cancel_dark), null);

                } else {
                    etName.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
                }
            }
        });

        etDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    etDesc.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getDrawable(R.drawable.ic_cancel_dark), null);

                } else {
                    etDesc.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
                }
            }
        });
        etPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    etPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getDrawable(R.drawable.ic_cancel_dark), null);

                } else {
                    etPrice.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
                }
            }
        });


        etName.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                etName.setText("");
            }
        });


        etDesc.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                etDesc.setText("");
            }
        });

        etPrice.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                etPrice.setText("");
            }
        });

        tags.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {

                if (!tags.getText().toString().trim().isEmpty()) {
                    tagList.add(new Tag(tags.getText().toString()));
                    tags.setText("");
                    chipView.setChipList(tagList);
                }

            }
        });

        tvOrganisation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetFragment = new BottomSheetFragment(organisationList, bottomSheetInterface);
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        btRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (valid()) {
                    btRound.startAnimation();
                    if (!itemImg.isEmpty()){
                        try {
                        Uri file = Uri.fromFile(new File(itemImg));
//                        InputStream stream = null;
//
//                             stream = new FileInputStream(new File(itemImg));
//
//
//
                        StorageReference mountainsRef = storageRef.child(file.getLastPathSegment());
//
//                        UploadTask uploadTask = mountainsRef.putStream(stream);


                            Bitmap bitmap = ((BitmapDrawable) imgItem.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();

                            UploadTask uploadTask = mountainsRef.putBytes(data);

// Register observers to listen for when the download is done or if it fails
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
                                        itemImg = downloadUrl.toString();
                                        Log.e(TAG, "onSuccess: "+itemImg );
                                        addProducts(itemImg);

                                    }
                                });




//
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                // ...
                            }
                        });

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    }else {
                        addProducts("");
                    }

                }
            }
        });

    }

    private boolean valid() {

        if (Objects.requireNonNull(etName.getText()).length() == 0) {
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity, getString(R.string.pleaseEnterProductName));
            return false;
        } else if (Objects.requireNonNull(etDesc.getText()).length() == 0) {
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity, getString(R.string.pleaseEnterProductDesc));
            return false;
        } else if (Objects.requireNonNull(etPrice.getText()).length() == 0) {
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity, getString(R.string.pleaseEnterProductPrice));
            return false;
        } else if (0 == tvCategory.getText().length()) {
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity, getString(R.string.pleaseSelectCategory));
            return false;
        } else if (tvOrganisation.getText().length() == 0) {
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity, getString(R.string.pleaseSelectOrganisation));
            return false;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }

    private void addProducts(String path) {

        String userId = auth.getUid();

        HashSet<String> tagSet = new HashSet<>();

        for (int i =0 ; i<tagList.size() ; i++){
            tagSet.add(tagList.get(i).getText());
        }

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("userId", userId);
        productMap.put("productName", etName.getText().toString());
        productMap.put("productDesc", etDesc.getText().toString());
        productMap.put("price", etPrice.getText().toString());
        productMap.put("sellerID", ShareSmilesPrefs.readString(activity, ShareSmilesPrefs.userId, null));
        productMap.put("buyerID", "");
        productMap.put("isSold", false);
        productMap.put("category", tvCategory.getText().toString());
        productMap.put("organisationName", tvOrganisation.getText().toString());
        productMap.put("organisationId", organisationId);
        productMap.put("Tags", tagSet);
        productMap.put("sellerName", ShareSmilesPrefs.readString(getApplicationContext(), ShareSmilesPrefs.userName, null));
        productMap.put("buyerName", "");
        productMap.put("productAddedAt", String.valueOf(System.currentTimeMillis()));
        productMap.put("productSoldTime", String.valueOf(00));
        productMap.put("itemImage", path);


//        CollectionReference userRef = dRef.collection("users").document(userId).collection("products");
        CollectionReference productRef = dRef.collection("products");
        productRef.add(productMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                btRound.setResultState(RoundButton.ResultState.SUCCESS);
                finish();
//                startActivity(new Intent(AddProducts.this, MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                btRound.setResultState(RoundButton.ResultState.FAILURE);
                Log.e(TAG, "failure: " + e.getMessage());
            }
        });


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v.getId() == R.id.tvCategory) {
            selectCategories();
        } else if (v.getId() == R.id.tvOrganisation) {

        }
    }

    private void selectCategories() {
        PopupMenu popup = new PopupMenu(getApplicationContext(), tvCategory);

        /*  The below code in try catch is responsible to display icons*/
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //inflate menu
        popup.getMenuInflater().inflate(R.menu.categories, popup.getMenu());

        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sportsItem:
                        tvCategory.setCompoundDrawablesRelativeWithIntrinsicBounds(getDrawable(R.drawable.ic_sports), null, getDrawable(R.drawable.ic_down), null);
                        tvCategory.setText(getString(R.string.sports));
                        break;
                    case R.id.footwareItem:
                        tvCategory.setCompoundDrawablesRelativeWithIntrinsicBounds(getDrawable(R.drawable.ic_footware), null, getDrawable(R.drawable.ic_down), null);

                        tvCategory.setText(getString(R.string.footware));
                        break;
                    case R.id.clothsItem:
                        tvCategory.setCompoundDrawablesRelativeWithIntrinsicBounds(getDrawable(R.drawable.ic_cloths), null, getDrawable(R.drawable.ic_down), null);

                        tvCategory.setText(getString(R.string.cloths));
                        break;
                    case R.id.entertainmentItem:
                        tvCategory.setCompoundDrawablesRelativeWithIntrinsicBounds(getDrawable(R.drawable.ic_entertainment), getDrawable(R.drawable.ic_down), null, null);

                        tvCategory.setText(getString(R.string.entertainment));
                        break;
                    case R.id.othersItem:
                        tvCategory.setCompoundDrawablesRelativeWithIntrinsicBounds(getDrawable(R.drawable.ic_others), null, getDrawable(R.drawable.ic_down), null);

                        tvCategory.setText(getString(R.string.others));
                        break;
                }
                return true;
            }
        });

    }

    @OnClick(R.id.imgItem)
    public void onViewClicked(View v) {
        if (v.getId()==R.id.imgItem){
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
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sportsItem:
                tvCategory.setText(getString(R.string.sports));
                Log.e(TAG, "onOptionsItemSelected: ");
                break;
            case R.id.footwareItem:
                tvCategory.setText(getString(R.string.footware));
                break;
        }

        return true;

    }

    @Override
    public void onChipClick(Chip chip) {
        chipView.remove(chip);
        tagList.remove(chip);
    }

    @Override
    public void setResult(String result, int position) {
        tvOrganisation.setText(result);
        organisationId = position;
    }

    @Override
    public void setHideSheet() {
        bottomSheetFragment.dismiss();
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
        itemImg = url;
        Glide.with(this).load(url)
                .into(imgItem);
        imgItem.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }
}