package com.cipher.sharesmilesandroid.adapters;

import android.content.Context;
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

import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.modals.ProductUser;
import com.cipher.sharesmilesandroid.modals.Products;
import com.cipher.sharesmilesandroid.modals.Users;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeView> {


    private static final String TAG = "HomeAdapter";
    Context activity;
    ArrayList<ProductUser> productUserArrayList;

    public HomeAdapter(Context activity, ArrayList<ProductUser> productUserArrayList){
        this.activity = activity;
        this.productUserArrayList = productUserArrayList;
    }


    @NonNull
    @Override
    public HomeView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_home, parent, false);
        return new HomeView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeView holder, int position) {


        Typeface custom_font = Typeface.createFromAsset(activity.getAssets(),  "fonts/BeautifulPeoplePersonalUse-dE0g.ttf");

        Products products = productUserArrayList.get(position).getProducts();
        Users users = productUserArrayList.get(position).getUsers();


        holder.tvTitle.setTypeface(custom_font);
        if (users!=null){
            holder.tvTitle.setText(users.getFullName());
            Log.e(TAG, "onBindViewHolder: "+users.getDescription() );
            holder.tvDesc.setText(users.getDescription());
        }

        holder.imgBSave.setChecked(true);

        holder.tvProductName.setText(products.getProductName());
        holder.tvProductDesc.setText(products.getProductDescription());

        Typeface dollarFont = Typeface.createFromAsset(activity.getAssets(),  "fonts/DollarBill.ttf");
        holder.tvPrice.setTypeface(dollarFont);
        String price = "$ "+products.getProductPrice();
        holder.tvPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return productUserArrayList.size();
    }

     class HomeView extends RecyclerView.ViewHolder{

        MaterialCardView cardView;
        TextView tvTitle,tvProductName,tvDesc,tvProductDesc;
        AppCompatCheckBox imgBSave;
        TextView tvPrice;

        AppCompatImageView imgProduct,imgSold;

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
        }
    }
}
