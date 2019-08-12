package com.cipher.sharesmilesandroid.modals;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.io.Serializable;
import java.util.ArrayList;

public class Products extends BaseObservable implements Serializable {

    private String productId;
    private String productName;
    private String productDescription;
    private String productPrice;
    private String productImage;
    private String sellerID;
    private String buyerID;
    private String productAddedTime;
    private String productSoldTime;
    private boolean isSold;
    private String organisationName;
    private int organisationID;

    private ArrayList<ProductTags> productTagsArrayList;
    private String productCategory;


    public Products(String productId, String productName, String productDescription, String price, String productImage,
                    String sellerID, String buyerID, String productAddedTime, String productSoldTime, boolean isSold
            , String organisationName, int organisationID, String productCategory, ArrayList<ProductTags> productTagsArrayList){
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = price;
        this.productImage = productImage;
        this.sellerID = sellerID;
        this.buyerID = buyerID;
        this.productAddedTime = productAddedTime;
        this.productSoldTime = productSoldTime;
        this.isSold = isSold;
        this.organisationName = organisationName;
        this.organisationID = organisationID;
        this.productCategory = productCategory;
        this.productTagsArrayList = productTagsArrayList;
    }


    protected Products(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        productDescription = in.readString();
        productPrice = in.readString();
        productImage = in.readString();
        sellerID = in.readString();
        buyerID = in.readString();
        productAddedTime = in.readString();
        productSoldTime = in.readString();
        isSold = in.readByte() != 0;
        organisationName = in.readString();
        organisationID = in.readInt();
        productCategory = in.readString();
    }


    @Bindable
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
        notifyPropertyChanged(BR.productId);
    }

    @Bindable
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
        notifyPropertyChanged(BR.productName);
    }

    @Bindable
    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
        notifyPropertyChanged(BR.productDescription);
    }

    @Bindable
    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;

    }

    public String getBuyerID() {
        return buyerID;
    }

    public void setBuyerID(String buyerID) {
        this.buyerID = buyerID;
    }

    public String getProductAdded() {
        return productAddedTime;
    }

    public void setProductAdded(String productAdded) {
        this.productAddedTime = productAdded;
    }

    public String getProductSold() {
        return productSoldTime;
    }

    public void setProductSold(String productSold) {
        this.productSoldTime = productSold;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }


    public String getProductAddedTime() {
        return productAddedTime;
    }

    public void setProductAddedTime(String productAddedTime) {
        this.productAddedTime = productAddedTime;
    }

    public String getProductSoldTime() {
        return productSoldTime;
    }

    public void setProductSoldTime(String productSoldTime) {
        this.productSoldTime = productSoldTime;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public int getOrganisationID() {
        return organisationID;
    }

    public void setOrganisationID(int organisationID) {
        this.organisationID = organisationID;
    }

    @Bindable
    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
        notifyPropertyChanged(BR.productPrice);
    }

    @Bindable
    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
        notifyPropertyChanged(BR.productCategory);
    }

    public ArrayList<ProductTags> getProductTagsArrayList() {
        return productTagsArrayList;
    }

    public void setProductTagsArrayList(ArrayList<ProductTags> productTagsArrayList) {
        this.productTagsArrayList = productTagsArrayList;
    }



}
