package com.cipher.sharesmilesandroid.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.cipher.sharesmilesandroid.R;
import com.google.android.material.card.MaterialCardView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeView> {


    Context activity;
    public HomeAdapter(Context activity ){
        this.activity = activity;
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

//        holder.imgBSave.setFocusable(true);
//        holder.imgBSave.setClickable(true);
//        holder.imgBSave.setEnabled(false);
//
//        holder.imgBSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(activity,"asd",Toast.LENGTH_SHORT).show();
//            }
//        });

        holder.imgBSave.setChecked(true);
        Typeface dollorFont = Typeface.createFromAsset(activity.getAssets(),  "fonts/DollarBill.ttf");
        holder.tvPrice.setTypeface(dollorFont);
        holder.tvPrice.setText("$ 2000");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class HomeView extends RecyclerView.ViewHolder{

        MaterialCardView cardView;
        TextView tvTitle;
        AppCompatCheckBox imgBSave;
        TextView tvPrice;
        public HomeView(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgBSave = itemView.findViewById(R.id.imgBSave);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
