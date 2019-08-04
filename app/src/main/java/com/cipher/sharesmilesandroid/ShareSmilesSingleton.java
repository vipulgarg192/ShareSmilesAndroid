package com.cipher.sharesmilesandroid;

import com.cipher.sharesmilesandroid.utilities.DialogBoxs;

public class ShareSmilesSingleton {

    static DialogBoxs dialogBoxs;

    private static final ShareSmilesSingleton ourInstance = new ShareSmilesSingleton();

    public static ShareSmilesSingleton getInstance() {
        dialogBoxs = new DialogBoxs();
        return ourInstance;
    }

    private ShareSmilesSingleton() {
    }

    public DialogBoxs getDialogBoxs() {
        return dialogBoxs;
    }
}
