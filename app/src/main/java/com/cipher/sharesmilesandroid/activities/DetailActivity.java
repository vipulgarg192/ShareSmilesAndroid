package com.cipher.sharesmilesandroid.activities;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.cipher.sharesmilesandroid.BaseActivity;
import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.databinding.ActivityDetailsBinding;
import com.cipher.sharesmilesandroid.modals.ProductUser;

public class DetailActivity extends BaseActivity {

    ProductUser productUser;
    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this,R.layout.activity_details);

        if (getIntent().getExtras()!=null){
            productUser = (ProductUser) getIntent().getSerializableExtra("data");
            binding.setProductsusers(productUser);
        }
    }
}
