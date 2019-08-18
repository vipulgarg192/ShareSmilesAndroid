package com.cipher.sharesmilesandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.modals.Users;

import java.util.ArrayList;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.MyViewHolder> {
    AppCompatActivity activity;
    ArrayList<Users> usersArrayList;



    public AllUserAdapter(AppCompatActivity mActivity, ArrayList<Users> usersArrayList) {
        this.activity = mActivity;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_users, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         Users users = usersArrayList.get(position);
         holder.tvName.setText(users.getFullName());
         holder.tvDesc.setText(users.getDescription());

         if (users.getUserImage()!=null || !users.getUserImage().equalsIgnoreCase("")){
             Glide.with(activity).load(users.getUserImage()).circleCrop().placeholder(R.drawable.ic_profile).into(holder.imgProfile);
         }
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout llItem;
        AppCompatImageView imgProfile;
        TextView tvName , tvDesc;


         MyViewHolder(@NonNull View itemView) {
            super(itemView);
            llItem = itemView.findViewById(R.id.llItem);
            imgProfile = itemView.findViewById(R.id.imgProfile);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDesc);
        }
    }
}
