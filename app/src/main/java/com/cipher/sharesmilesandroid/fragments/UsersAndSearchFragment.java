package com.cipher.sharesmilesandroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.activities.CategoriesActivity;
import com.google.android.material.card.MaterialCardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UsersAndSearchFragment extends Fragment {


    @BindView(R.id.imgSports)
    AppCompatImageView imgSports;
    @BindView(R.id.cardSports)
    MaterialCardView cardSports;
    @BindView(R.id.imgEntertainment)
    AppCompatImageView imgEntertainment;
    @BindView(R.id.cardEntertainment)
    MaterialCardView cardEntertainment;
    @BindView(R.id.imgFootware)
    AppCompatImageView imgFootware;
    @BindView(R.id.cardFootware)
    MaterialCardView cardFootware;
    @BindView(R.id.imgCloths)
    AppCompatImageView imgCloths;
    @BindView(R.id.cardCloths)
    MaterialCardView cardCloths;
    @BindView(R.id.imgOthers)
    AppCompatImageView imgOthers;
    @BindView(R.id.cardOthers)
    MaterialCardView cardOthers;
    @BindView(R.id.cardUsers)
    MaterialCardView cardUsers;
    private RecyclerView recyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.usersnsearch_fragment,
                container, false);
        ButterKnife.bind(this, view);

        onViewClicked(view);

        return view;
    }

    @OnClick({R.id.cardSports, R.id.cardEntertainment, R.id.cardFootware, R.id.cardCloths, R.id.cardOthers, R.id.cardUsers})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cardSports:
                sendIntent("Sports");
                break;
            case R.id.cardEntertainment:
                sendIntent("Entertainment");
                break;
            case R.id.cardFootware:
                sendIntent("Footware");
                break;
            case R.id.cardCloths:
                sendIntent("Cloths");
                break;
            case R.id.cardOthers:
                sendIntent("Others");
                break;
            case R.id.cardUsers:
                sendIntent("Sports");
                break;
        }
    }

    private void sendIntent(String data) {
        Intent intent = new Intent(getActivity(), CategoriesActivity.class);
        intent.putExtra("Data",data);
        getActivity().startActivity(intent);
    }
}
