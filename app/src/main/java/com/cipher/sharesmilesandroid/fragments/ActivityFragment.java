package com.cipher.sharesmilesandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.adapters.ActivityAdapter;

import java.util.ArrayList;

public class ActivityFragment extends Fragment {

    private RecyclerView recyclerView;
    private ActivityAdapter activityAdapter;

    ArrayList<String> activityList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment,
                container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        setAdapter();
        return view;
    }

    private void setAdapter() {
        activityAdapter = new ActivityAdapter(getActivity(),activityList);

       RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
       recyclerView.setLayoutManager(layoutManager);
       recyclerView.setAdapter(activityAdapter);
    }
}
