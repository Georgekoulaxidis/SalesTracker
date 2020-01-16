package com.example.salestracker;

import java.util.List;

public class FavsProduct {

    private int userId;
    private String productId;
    private String productTitle;
    private double productPrice;
    private String productSeller;
    private String productUrl;
    private String condition;
    private String eBayStore;   //US, Germany, UK, Australia
    //private String minPrice;
    //private String maxPrice;
    private boolean freeShipping;   //true or false based on the checkbox on search fragment
    //private List<String> paymentMethods;    //ΤΙ ΘΑ ΜΠΕΙ ΕΔΩ

    public FavsProduct(int userId, String productId, String productTitle, double productPrice,
                       String productSeller, String productUrl, String condition, String eBayStore,
                       /*String minPrice, String maxPrice, */boolean freeShipping/*, List<String> paymentMethods*/) {
        this.userId = userId;
        this.productId = productId;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productSeller = productSeller;
        this.productUrl = productUrl;
        this.condition = condition;
        this.eBayStore = eBayStore;
        //this.minPrice = minPrice;
        //this.maxPrice = maxPrice;
        this.freeShipping = freeShipping;
        //this.paymentMethods = paymentMethods;
    }

    public FavsProduct() {

    }

    public int getUserId() { return userId; }

    public String getProductId() { return productId; }

    public String getProductTitle() { return productTitle; }

    public double getProductPrice() { return productPrice; }

    public String getProductSeller() { return productSeller; }

    public String getProductUrl() { return productUrl; }

    public String getCondition() { return condition; }

    public String geteBayStore() { return eBayStore; }

    //public String getMinPrice() { return minPrice; }

    //public String getMaxPrice() { return maxPrice; }

    public boolean getFreeShipping() { return freeShipping; }

    //public List<String> getPaymentMethods() { return paymentMethods; }


    public void setUserId(int userId) { this.userId = userId; }

    public void setProductId(String productId) { this.productId = productId; }

    public void setProductTitle(String productTitle) { this.productTitle = productTitle; }

    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }

    public void setProductSeller(String productSeller) { this.productSeller = productSeller; }

    public void setProductUrl(String productUrl) { this.productUrl = productUrl; }

    public void setCondition(String condition) { this.condition = condition; }

    public void seteBayStore(String eBayStore) { this.eBayStore = eBayStore; }

    //public void setMinPrice(String minPrice) { this.minPrice = minPrice; }

    //public void setMaxPrice(String maxPrice) { this.maxPrice = maxPrice; }

    public void setFreeShipping(boolean freeShipping) { this.freeShipping = freeShipping; }

}
