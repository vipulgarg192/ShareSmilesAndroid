package com.cipher.sharesmilesandroid.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.modals.Products;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeView> {


    Context activity;
    ArrayList<Products> productsArrayList;

    public HomeAdapter(Context activity, ArrayList<Products> productsArrayList){
        this.activity = activity;
        this.productsArrayList = productsArrayList;
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

        holder.tvTitle.setTypeface(custom_font);
        holder.tvTitle.setText("Drake Champion");


        holder.imgBSave.setChecked(true);

        holder.tvProductName.setText(productsArrayList.get(position).getProductName());
        holder.tvDesc.setText(productsArrayList.get(position).getProductDescription());

        Typeface dollorFont = Typeface.createFromAsset(activity.getAssets(),  "fonts/DollarBill.ttf");
        holder.tvPrice.setTypeface(dollorFont);
        holder.tvPrice.setText("$ 2000");
    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    public class HomeView extends RecyclerView.ViewHolder{

        MaterialCardView cardView;
        TextView tvTitle,tvProductName,tvDesc;
        AppCompatCheckBox imgBSave;
        TextView tvPrice;

        AppCompatImageView imgProduct,imgSold;

        public HomeView(@NonNull View itemView) {
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
