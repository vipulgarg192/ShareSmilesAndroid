package com.cipher.sharesmilesandroid.modals;

import android.os.Parcel;
import android.os.Parcelable;

public class Products implements Parcelable {

    private String productId;
    private String productName;
    private String productDescription;
    private String productImage;
    private String sellerID;
    private String buyerID;
    private long productAddedTime;
    private long productSoldTime;
    private boolean isSold;

    protected Products(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        productDescription = in.readString();
        productImage = in.readString();
        sellerID = in.readString();
        buyerID = in.readString();
        productAddedTime = in.readLong();
        productSoldTime = in.readLong();
        isSold = in.readByte() != 0;
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

    public long getProductAdded() {
        return productAddedTime;
    }

    public void setProductAdded(long productAdded) {
        this.productAddedTime = productAdded;
    }

    public long getProductSold() {
        return productSoldTime;
    }

    public void setProductSold(long productSold) {
        this.productSoldTime = productSold;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
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
        parcel.writeString(productImage);
        parcel.writeString(sellerID);
        parcel.writeString(buyerID);
        parcel.writeLong(productAddedTime);
        parcel.writeLong(productSoldTime);
        parcel.writeByte((byte) (isSold ? 1 : 0));
    }
}
