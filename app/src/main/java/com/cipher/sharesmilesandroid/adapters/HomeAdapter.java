package com.cipher.sharesmilesandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cipher.sharesmilesandroid.R;
import com.google.android.material.card.MaterialCardView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeView> {


    public HomeAdapter(Context activity ){

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

    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class HomeView extends RecyclerView.ViewHolder{

        MaterialCardView cardView;
        public HomeView(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
