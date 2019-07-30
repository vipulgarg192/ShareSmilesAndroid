package com.cipher.sharesmilesandroid.modals;

public class Products {

    private String productId;
    private String productName;
    private String productDescription;
    private String productImage;
    private String sellerID;
    private String buyerID;
    private long productAdded;
    private long productSold;
    private boolean isSold;

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
        return productAdded;
    }

    public void setProductAdded(long productAdded) {
        this.productAdded = productAdded;
    }

    public long getProductSold() {
        return productSold;
    }

    public void setProductSold(long productSold) {
        this.productSold = productSold;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }




}
