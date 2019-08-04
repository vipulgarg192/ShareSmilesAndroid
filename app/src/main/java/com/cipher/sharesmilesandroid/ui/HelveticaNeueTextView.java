package com.cipher.sharesmilesandroid.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;


import androidx.appcompat.widget.AppCompatTextView;


public class HelveticaNeueTextView extends AppCompatTextView {


    public HelveticaNeueTextView(Context context) {
        super(context);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/HelveticaNeue.ttf");
        this.setTypeface(custom_font);
    }

    public HelveticaNeueTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/HelveticaNeue.ttf");
        this.setTypeface(custom_font);
    }

    public HelveticaNeueTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/HelveticaNeue.ttf");
        this.setTypeface(custom_font);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}