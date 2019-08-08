package com.cipher.sharesmilesandroid.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.modals.Products;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class UserAddedProdAdapter extends RecyclerView.Adapter<UserAddedProdAdapter.MyViewHolder>{

    private FragmentActivity activity;
    private ArrayList<Products> productsArrayList;

    public UserAddedProdAdapter(FragmentActivity activity, ArrayList<Products> productsArrayList) {
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

        Products products = productsArrayList.get(position);

        holder.tvDesc.setVisibility(View.INVISIBLE);
        holder.tvTitle.setTypeface(custom_font);
        holder.tvTitle.setText(products.getSellerID());
        holder.imgBSave.setChecked(true);
        holder.tvProductName.setText(products.getProductName());
        holder.tvDesc.setText(products.getProductDescription());

        Typeface dollarFont = Typeface.createFromAsset(activity.getAssets(),  "fonts/DollarBill.ttf");
        holder.tvPrice.setTypeface(dollarFont);
        String price = "$ "+products.getProductPrice();
        holder.tvPrice.setText(price);

    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        TextView tvTitle,tvProductName,tvDesc;
        AppCompatCheckBox imgBSave;
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
        }
    }

}
