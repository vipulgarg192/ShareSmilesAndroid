package com.cipher.sharesmilesandroid.activities;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddProducts extends BaseActivity {


    private static final String TAG = "AddProducts";
   /* @BindView(R.id.imgToolbar)
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
    AppCompatEditText etPrice;
    @BindView(R.id.tvCategory)
    AppCompatTextView tvCategory;
    @BindView(R.id.tags)
    AppCompatEditText tags;
    @BindView(R.id.tvOrganisation)
    AppCompatTextView tvOrganisation;*/


    CustomDrawableEditText etName;


    private FirebaseAuth auth;
    private FirebaseFirestore dRef = FirebaseFirestore.getInstance();


    RoundButton circularProgressButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.addproduct_activity);

        etName = findViewById(R.id.etName);
        etName.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);


        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()>0){
                     etName.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null, getDrawable(R.drawable.ic_cancel_dark),null);

                }else {
                    Log.e(TAG, "onTextChanged:size 0 " );
                    etName.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null);
                }
            }
        });



       /* circularProgressButton = findViewById(R.id.rbAdd);
        Random random = new Random();


        circularProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                circularProgressButton.startAnimation();
                addProducts();
//                circularProgressButton.setResultState(RoundButton.ResultState.FAILURE);
//                circularProgressButton.revertAnimation();
            }
        });

        circularProgressButton.setButtonAnimationListener(new RoundButton.RoundButtonAnimationListener() {
            @Override
            public void onRevertMorphingEnd() {

            }

            @Override
            public void onApplyMorphingEnd() {

            }

            @Override
            public void onShowProgress() {

            }

            @Override
            public void onShowResultState() {

            }
        });
*/



//       etName.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(etName) {
//           @Override
//           public boolean onDrawableClick() {
//
//               Log.e(TAG, "onDrawableClick: " );
//               return true;
//           }
//       });


      etName.setDrawableClickListener(new DrawableClickListener() {
          @Override
          public void onClick(DrawablePosition target) {
              Log.e(TAG, "onClick: " );
          }
      });

    }



    private void addProducts() {

        String userId = auth.getUid();

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("userId", userId);
        userMap.put("productName",etName.getText().toString());
//        userMap.put("productDesc", etDesc.getText().toString());
//        userMap.put("price",etPrice.getText().toString());
        userMap.put("sellerID", ShareSmilesPrefs.readString(this, ShareSmilesPrefs.userId, null));
        userMap.put("buyerID", "");
        userMap.put("isSold", false);
        userMap.put("category", "All");

        CollectionReference newCityRef = dRef.collection("products");

        newCityRef.add(userMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                circularProgressButton.setResultState(RoundButton.ResultState.SUCCESS);
//                startActivity(new Intent(AddProducts.this, MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                circularProgressButton.setResultState(RoundButton.ResultState.FAILURE);
                Log.e(TAG, "failure: " + e.getMessage());
            }
        });



    }



//    @OnClick(R.id.bottom)
//    public void onViewClicked() {
//    }
}
