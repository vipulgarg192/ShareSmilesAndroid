package com.cipher.sharesmilesandroid.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class BeautifullTextView extends AppCompatTextView {


    public BeautifullTextView(Context context) {
        super(context);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/BeautifulPeoplePersonalUse-dE0g.ttf");
        this.setTypeface(custom_font);
    }

    public BeautifullTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/BeautifulPeoplePersonalUse-dE0g.ttf");
        this.setTypeface(custom_font);
    }

    public BeautifullTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(),  "fonts/BeautifulPeoplePersonalUse-dE0g.ttf");
        this.setTypeface(custom_font);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}