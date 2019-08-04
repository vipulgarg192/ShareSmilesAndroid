package com.cipher.sharesmilesandroid.utilities;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.ui.HelveticaNeueTextView;
import com.google.android.material.card.MaterialCardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogBoxs {

    Dialog dialog;
    MaterialCardView cardDialog;

    public void showDialog(AppCompatActivity activity, String msg) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alert_dialogbox);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ButterKnife.bind(activity);


        HelveticaNeueTextView tvTitle = dialog.findViewById(R.id.tvTitle);
        HelveticaNeueTextView tvMsg = dialog.findViewById(R.id.tvMsg);
        HelveticaNeueTextView tvPositive = dialog.findViewById(R.id.tvPositive);
        HelveticaNeueTextView tvNegative = dialog.findViewById(R.id.tvNegative);



        dialog.show();

        tvNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

}
