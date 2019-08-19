package com.cipher.sharesmilesandroid.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.activities.DetailActivity;
import com.cipher.sharesmilesandroid.interfaces.Clicklisteners;
import com.cipher.sharesmilesandroid.modals.ProductUser;
import com.cipher.sharesmilesandroid.modals.Products;
import com.cipher.sharesmilesandroid.modals.Users;
import com.cipher.sharesmilesandroid.utilities.TimeShow;
import com.google.android.material.card.MaterialCardView;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeView> {


    private static final String TAG = "HomeAdapter";
    Context activity;
    ArrayList<ProductUser> productUserArrayList;

    Clicklisteners clicklisteners;

    TimeShow timeShow ;
    public HomeAdapter(Context activity, ArrayList<ProductUser> productUserArrayList, Clicklisteners clicklisteners){
        this.activity = activity;
        this.productUserArrayList = productUserArrayList;
        this.clicklisteners = clicklisteners;
        this.timeShow = new TimeShow();
    }


    @NonNull
    @Override
    public HomeView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);
        return new HomeView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeView holder,final int position) {


        Typeface custom_font = Typeface.createFromAsset(activity.getAssets(),  "fonts/BeautifulPeoplePersonalUse-dE0g.ttf");


        Products products = productUserArrayList.get(position).getProducts();
        Users users = productUserArrayList.get(position).getUsers();


        holder.tvTitle.setTypeface(custom_font);
        if (users!=null){
            holder.tvTitle.setText(users.getFullName());
            Log.e(TAG, "onBindViewHolder: "+users.getDescription() );
            holder.tvDesc.setText(users.getDescription());

            String image = users.getUserImage();
            Log.e(TAG, "onBindViewHolder: "+image );
            if (image!=null)
            if (!image.equalsIgnoreCase("")){

                Glide.with(activity).load(users.getUserImage()).into(holder.imgProfile);
            }
        }


        holder.imgBSave.setChecked(true);

        holder.tvProductName.setText(products.getProductName());
        holder.tvProductDesc.setText(products.getProductDescription());

        Typeface dollarFont = Typeface.createFromAsset(activity.getAssets(),  "fonts/DollarBill.ttf");
        holder.tvPrice.setTypeface(dollarFont);
        String price = "$ "+products.getProductPrice();
        holder.tvPrice.setText(price);

        if (!products.getProductImage().isEmpty()){

            Glide.with(activity).load(products.getProductImage()).into(holder.imgProduct);
        }

        if (products.isSold()){
            holder.imgSold.setVisibility(View.VISIBLE);
        }else {
            holder.imgSold.setVisibility(View.GONE);
        }



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicklisteners.onClickListeners(position);
            }
        });

        holder.tvTime.setText(timeShow.getTime(Long.parseLong(products.getProductAddedTime())));
    }

    @Override
    public int getItemCount() {
        return productUserArrayList.size();
    }

     class HomeView extends RecyclerView.ViewHolder{

        MaterialCardView cardView;
        TextView tvTitle,tvProductName,tvDesc,tvProductDesc;
        AppCompatCheckBox imgBSave;
        TextView tvPrice,tvTime;

        AppCompatImageView imgProduct,imgSold ,imgProfile;

         HomeView(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);

            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvDesc = itemView.findViewById(R.id.tvDesc);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            imgSold = itemView.findViewById(R.id.imgSold);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgBSave = itemView.findViewById(R.id.imgBSave);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvProductDesc = itemView.findViewById(R.id.tvProductDesc);
             imgProfile = itemView.findViewById(R.id.imgProfile);

             tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
