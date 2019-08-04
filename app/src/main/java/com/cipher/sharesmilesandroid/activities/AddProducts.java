package com.cipher.sharesmilesandroid.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

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
import com.cipher.sharesmilesandroid.interfaces.DrawableClickListener;
import com.cipher.sharesmilesandroid.ui.CustomDrawableEditText;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.circularreveal.cardview.CircularRevealCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.marozzi.roundbutton.RoundButton;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;



public class AddProducts extends BaseActivity {


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
    AppCompatEditText tags;
    @BindView(R.id.tvOrganisation)
    AppCompatTextView tvOrganisation;


    @BindView(R.id.bt_round)
    RoundButton btRound;


    private FirebaseAuth auth;
    private FirebaseFirestore dRef = FirebaseFirestore.getInstance();


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.addproduct_activity);
        ButterKnife.bind(activity);

        tbSimple.setVisibility(View.VISIBLE);
        tbHome.setVisibility(View.GONE);

        setSupportActionBar(tbSimple);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


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

        btRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btRound.startAnimation();
                addProducts();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void addProducts() {

        String userId = auth.getUid();

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("userId", userId);
        userMap.put("productName", etName.getText().toString());
        userMap.put("productDesc", etDesc.getText().toString());
        userMap.put("price",etPrice.getText().toString());
        userMap.put("sellerID", ShareSmilesPrefs.readString(this, ShareSmilesPrefs.userId, null));
        userMap.put("buyerID", "");
        userMap.put("isSold", false);
        userMap.put("category", "All");

        CollectionReference newCityRef = dRef.collection("products");

        newCityRef.add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                btRound.setResultState(RoundButton.ResultState.SUCCESS);
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

}
