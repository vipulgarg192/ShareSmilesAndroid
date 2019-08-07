package com.cipher.sharesmilesandroid.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

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
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marozzi.roundbutton.RoundButton;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;



public class AddProducts extends BaseActivity  implements OnChipClickListener  , BottomSheetInterface {


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
    @BindView(R.id.imgItem)
    CircularRevealCardView imgItem;
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

    int SpannedLength = 0,chipLength = 20;

    boolean newchip = false;

    private FirebaseAuth auth;
    private FirebaseFirestore dRef = FirebaseFirestore.getInstance();


    int beforeSize =0 , afterSize =0;


    ArrayList<Chip> tagList = new ArrayList<>();

    ArrayList<Organisations> organisationsArrayList = new ArrayList<>();
    ArrayList<String> organisationList = new ArrayList<>();



    BottomSheetInterface bottomSheetInterface;
    BottomSheetFragment bottomSheetFragment;

    private int organisationId;

    private BottomSheetBehavior mBottomSheetBehavior;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.addproduct_activity);
        ButterKnife.bind(activity);

        bottomSheetInterface = this;



        organisationsArrayList.add(new Organisations(1,"World Vision Canada"));
        organisationsArrayList.add(new Organisations(2,"The Canadian Red Cross Society"));
        organisationsArrayList.add(new Organisations(3,"The Church Of Jesus Christ Of Latter-Day Saints In Canada"));
        organisationsArrayList.add(new Organisations(4,"Jewish Community Foundation Of Montreal"));

        organisationsArrayList.add(new Organisations(5,"CanadaHelps Canadon"));
        organisationsArrayList.add(new Organisations(6,"Plan International Canada Inc."));
        organisationsArrayList.add(new Organisations(7,"United Way Of Greater Toronto"));
        organisationsArrayList.add(new Organisations(8,"Heart And Stroke Foundation Of Canada"));

        organisationsArrayList.add(new Organisations(9,"Redemption Ministries Inc."));
        organisationsArrayList.add(new Organisations(10,"Doctors Without Borders"));
        organisationsArrayList.add(new Organisations(11,"Vancouver Foundation"));
        organisationsArrayList.add(new Organisations(12,"Bc Cancer Foundation"));

        for (int i=0;i<organisationsArrayList.size();i++){

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

                if (!tags.getText().toString().trim().isEmpty()){
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

                if (valid()){
                    btRound.startAnimation();
                 addProducts();
                }
            }
        });

    }

    private boolean valid() {
        if (Objects.requireNonNull(etName.getText()).length()==0){
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,getString(R.string.pleaseEnterProductName));
        return false;
        }else if (Objects.requireNonNull(etDesc.getText()).length()==0){
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,getString(R.string.pleaseEnterProductDesc));
            return false;
        }else if (Objects.requireNonNull(etPrice.getText()).length()==0){
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,getString(R.string.pleaseEnterProductPrice));
            return false;
        }else if (0 == tvCategory.getText().length()){
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,getString(R.string.pleaseSelectCategory));
            return false;
        }else if (tvOrganisation.getText().length()==0){
            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,getString(R.string.pleaseSelectOrganisation));
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

    private void addProducts() {

        String userId = auth.getUid();



        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("userId", userId);
        productMap.put("productName", etName.getText().toString());
        productMap.put("productDesc", etDesc.getText().toString());
        productMap.put("price",etPrice.getText().toString());
        productMap.put("sellerID", ShareSmilesPrefs.readString(activity, ShareSmilesPrefs.userId, null));
        productMap.put("buyerID", "");
        productMap.put("isSold", false);
        productMap.put("category", tvCategory.getText().toString());
        productMap.put("organisationName",tvOrganisation.getText().toString());
        productMap.put("organisationId",organisationId);
        productMap.put("Tags",tagList);


        CollectionReference newCityRef = dRef.collection("products");

        newCityRef.add(productMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
                switch (item.getItemId()){
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




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sportsItem:
                tvCategory.setText(getString(R.string.sports));
                Log.e(TAG, "onOptionsItemSelected: " );
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
    public void setResult(String result , int position) {
      tvOrganisation.setText(result);
      organisationId = position;
    }

    @Override
    public void setHideSheet() {
        bottomSheetFragment.dismiss();
    }

}
