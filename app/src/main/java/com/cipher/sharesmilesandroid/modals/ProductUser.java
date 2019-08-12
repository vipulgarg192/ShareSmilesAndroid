package com.cipher.sharesmilesandroid.modals;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.io.Serializable;


public class ProductUser extends BaseObservable implements Serializable {

    private Products products;

    @Bindable
    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
        notifyPropertyChanged(BR.products);

    }

    @Bindable
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
        notifyPropertyChanged(BR.users);
    }

    private Users users;

}
