package com.cipher.sharesmilesandroid.modals;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Products implements Parcelable  {

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

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

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


    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }


    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public ArrayList<ProductTags> getProductTagsArrayList() {
        return productTagsArrayList;
    }

    public void setProductTagsArrayList(ArrayList<ProductTags> productTagsArrayList) {
        this.productTagsArrayList = productTagsArrayList;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productId);
        parcel.writeString(productName);
        parcel.writeString(productDescription);
        parcel.writeString(productPrice);
        parcel.writeString(productImage);
        parcel.writeString(sellerID);
        parcel.writeString(buyerID);
        parcel.writeString(productAddedTime);
        parcel.writeString(productSoldTime);
        parcel.writeByte((byte) (isSold ? 1 : 0));
        parcel.writeString(organisationName);
        parcel.writeInt(organisationID);
        parcel.writeString(productCategory);
    }
}
