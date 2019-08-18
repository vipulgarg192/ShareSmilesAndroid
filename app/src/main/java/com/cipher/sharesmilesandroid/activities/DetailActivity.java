package com.cipher.sharesmilesandroid.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.cipher.sharesmilesandroid.BaseActivity;
import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.ShareSmilesPrefs;
import com.cipher.sharesmilesandroid.chipsSet.Chip;
import com.cipher.sharesmilesandroid.chipsSet.ChipViewAdapter;
import com.cipher.sharesmilesandroid.chipsSet.MainChipViewAdapter;
import com.cipher.sharesmilesandroid.chipsSet.OnChipClickListener;
import com.cipher.sharesmilesandroid.chipsSet.Tag;
import com.cipher.sharesmilesandroid.databinding.ActivityDetailsBinding;
import com.cipher.sharesmilesandroid.modals.ProductUser;
import com.cipher.sharesmilesandroid.modals.Products;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.marozzi.roundbutton.RoundButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class DetailActivity extends BaseActivity  implements OnChipClickListener {

    AppCompatActivity activity = DetailActivity.this;
    ProductUser productUser;
    private ActivityDetailsBinding binding;
    private static final String TAG = "DetailActivity";
    String tags="";

    private FirebaseFirestore dRef = FirebaseFirestore.getInstance();

    ArrayList<Chip> chipArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this,R.layout.activity_details);

        ChipViewAdapter adapterLayout = new MainChipViewAdapter(this);

        binding.chipView.setAdapter(adapterLayout);
        binding.chipView.setChipLayoutRes(R.layout.chip_tag);
        binding.chipView.setChipBackgroundColor(getResources().getColor(R.color.md_white_1000));
        binding.chipView.setChipBackgroundColorSelected(getResources().getColor(R.color.md_blue_grey_500));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getExtras()!=null){
            productUser = (ProductUser) getIntent().getSerializableExtra("data");
            binding.setProductsusers(productUser);
        }
        String userId = ShareSmilesPrefs.readString(getApplicationContext(),ShareSmilesPrefs.userId,null);

        if (productUser.getProducts().getSellerID().equalsIgnoreCase(userId)){
            if (!productUser.getProducts().getBuyerID().equalsIgnoreCase("")){
                binding.btnBuy.setText("Sold");
                binding.btnBuy.setEnabled(false);
            }else {
                binding.btnBuy.setText("Posted for selling");
                binding.btnBuy.setEnabled(false);
            }
        }else {
            if (!productUser.getProducts().getBuyerID().equalsIgnoreCase(userId)){
                binding.btnBuy.setText("Not Available");
                binding.btnBuy.setEnabled(false);
            }else if (productUser.getProducts().getBuyerID().equalsIgnoreCase(userId)){
                binding.btnBuy.setText("Buyed");
                binding.btnBuy.setEnabled(false);
            }
            else {
                binding.btnBuy.setText("Buy");
                binding.btnBuy.setEnabled(true);
            }
        }

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(getString(R.string.detailScreen));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        for (int i =0 ; i< productUser.getProducts().getProductTagsArrayList().size() ; i++){
            chipArrayList.add(new Tag("#"+productUser.getProducts().getProductTagsArrayList().get(i).getTagName()));
//            tags = tags + productUser.getProducts().getProductTagsArrayList().get(i).getTagName()+" ";
        }
        binding.chipView.setChipList(chipArrayList);
    }

    public void onViewClicked(View v){
        if (v.getId()==R.id.btnBuy){
           addProducts();
        }
    }

    private void addProducts() {

        String userId = ShareSmilesPrefs.readString(getBaseContext(),ShareSmilesPrefs.userId,null);
        String userName = ShareSmilesPrefs.readString(getBaseContext(),ShareSmilesPrefs.userName,null);

        Products products = binding.getProductsusers().getProducts();
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("userId", products.getSellerID());
        productMap.put("productName", products.getProductName());
        productMap.put("productDesc", products.getProductDescription());
        productMap.put("price", products.getProductPrice());
        productMap.put("sellerID",products.getSellerID());
        productMap.put("buyerID", userId);
        productMap.put("isSold", true);
        productMap.put("buyerName", userName);
        productMap.put("productSoldTime",  String.valueOf(System.currentTimeMillis()));

        DocumentReference productRef = dRef.collection("products").document(products.getProductId());
       productRef.set(productMap,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void aVoid) {
               Log.e(TAG, "onSuccess: " );
               binding.btnBuy.setText("Buyed");
               binding.btnBuy.setEnabled(false);
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               e.printStackTrace();
           }
       });
    }

    @Override
    public void onChipClick(Chip chip) {
        printToast(activity,chip.getText().toString().replace("#",""));
    }
}
