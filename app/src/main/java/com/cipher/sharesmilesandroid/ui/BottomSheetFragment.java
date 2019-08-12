package com.cipher.sharesmilesandroid.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.interfaces.BottomSheetInterface;
import com.cipher.sharesmilesandroid.modals.Organisations;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private ArrayList<String > list ;
    private static final String TAG = "BottomSheetFragment";

    BottomSheetInterface bottomSheetInterface;

    public BottomSheetFragment(ArrayList<String> items, BottomSheetInterface bottomSheetInterface) {
        this.list = items;
        this.bottomSheetInterface = bottomSheetInterface;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottomsheet_content, container, false);

        WheelView wheelView = view.findViewById(R.id.wheelview);
        wheelView.setItems(list);

        Button txtCancel = view.findViewById(R.id.btnCancel);
        Button txtDone = view.findViewById(R.id.btnDone);

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = wheelView.getSeletedItem();
                bottomSheetInterface.setResult(a , list.indexOf(a));
                bottomSheetInterface.setHideSheet();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetInterface.setResult("",0);
                bottomSheetInterface.setHideSheet();
            }
        });
        return view;
    }

}
