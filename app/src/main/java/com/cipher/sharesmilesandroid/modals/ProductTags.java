package com.cipher.sharesmilesandroid.modals;

import java.io.Serializable;

public class ProductTags implements Serializable {

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    private String tagName;
}
