package com.cipher.sharesmilesandroid.interfaces;

public interface DrawableClickListener {

    public static enum DrawablePosition { TOP, BOTTOM, START, END };
    public void onClick(DrawablePosition target);

}

