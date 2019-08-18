package com.cipher.sharesmilesandroid.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.modals.ProductUser;
import com.cipher.sharesmilesandroid.modals.Products;
import com.cipher.sharesmilesandroid.modals.Users;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class UserAddedProdAdapter extends RecyclerView.Adapter<UserAddedProdAdapter.MyViewHolder>{

    private FragmentActivity activity;
    private ArrayList<ProductUser> productsArrayList;

    public UserAddedProdAdapter(FragmentActivity activity, ArrayList<ProductUser> productsArrayList) {
        this.activity = activity;
        this.productsArrayList = productsArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Typeface custom_font = Typeface.createFromAsset(activity.getAssets(),  "fonts/BeautifulPeoplePersonalUse-dE0g.ttf");

        Products products = productsArrayList.get(position).getProducts();

        Users users = productsArrayList.get(position).getUsers();

        holder.tvDesc.setText(users.getDescription());
        holder.tvTitle.setTypeface(custom_font);
        holder.tvTitle.setText(users.getFirstName());

        if (users.getUserImage()!=null&& !users.getUserImage().equalsIgnoreCase("")){
            Glide.with(activity).load(users.getUserImage()).into(holder.imgProfile);
        }
        holder.imgBSave.setChecked(true);
        holder.tvProductName.setText(products.getProductName());

        Typeface dollarFont = Typeface.createFromAsset(activity.getAssets(),  "fonts/DollarBill.ttf");
        holder.tvPrice.setTypeface(dollarFont);
        String price = "$ "+products.getProductPrice();
        holder.tvPrice.setText(price);

        if (!products.isSold()){
            holder.imgSold.setVisibility(View.GONE);
        }

        if(products.getProductImage()!=null && !products.getProductImage().equalsIgnoreCase("")){
            Glide.with(activity).load(products.getProductImage()).placeholder(R.drawable.placeholer).into(holder.imgProduct);
        }
        holder.imgBSave.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        TextView tvTitle,tvProductName,tvDesc;
        AppCompatCheckBox imgBSave ;
        ImageView imgProfile;
        TextView tvPrice;

        AppCompatImageView imgProduct,imgSold;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);

            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvDesc = itemView.findViewById(R.id.tvDesc);

            imgProduct = itemView.findViewById(R.id.imgProduct);
            imgSold = itemView.findViewById(R.id.imgSold);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgBSave = itemView.findViewById(R.id.imgBSave);
            tvPrice = itemView.findViewById(R.id.tvPrice);

            imgProfile = itemView.findViewById(R.id.imgProfile);
        }
    }

}
