package com.example.salestracker;

import java.io.Serializable;

public class SingleProduct implements Serializable {

    private String Ack;
    private itemDetails Item;
    private String Version;


    public String getAck() {
        return Ack;
    }

    public void setAck(String ack) {
        Ack = ack;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public itemDetails getItem() {
        return Item;
    }

    public void setItem(itemDetails item) {
        Item = item;
    }



    public class itemDetails {
        private String ItemID;
        private String EndTime;
        private String Title;
        private String Country;
        private String ConditionID;
        private String ConditionDisplayName;
        private priceData ConvertedCurrentPrice;
        private String GalleryURL;
        private sellerDetails Seller;
        private shippingService ShippingCostSummary;

        public shippingService getShippingCostSummary() {
            return ShippingCostSummary;
        }

        public void setShippingCostSummary(shippingService shippingCostSummary) {
            ShippingCostSummary = shippingCostSummary;
        }

        public sellerDetails getSeller() {
            return Seller;
        }

        public void setSeller(sellerDetails seller) {
            Seller = seller;
        }

        public String getGalleryURL() {
            return GalleryURL;
        }

        public void setGalleryURL(String galleryURL) {
            GalleryURL = galleryURL;
        }

        public String getItemID() {
            return ItemID;
        }

        public void setItemID(String itemID) {
            ItemID = itemID;
        }

        public String getEndTime() {
            return EndTime;
        }

        public void setEndTime(String endTime) {
            EndTime = endTime;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String country) {
            Country = country;
        }

        public String getConditionID() {
            return ConditionID;
        }

        public void setConditionID(String conditionID) {
            ConditionID = conditionID;
        }

        public String getConditionDisplayName() {
            return ConditionDisplayName;
        }

        public void setConditionDisplayName(String conditionDisplayName) {
            ConditionDisplayName = conditionDisplayName;
        }

        public priceData getConvertedCurrentPrice() {
            return ConvertedCurrentPrice;
        }

        public void setConvertedCurrentPrice(priceData convertedCurrentPrice) {
            ConvertedCurrentPrice = convertedCurrentPrice;
        }
    }

    public class priceData{
        private Double Value;
        private String CurrencyID;

        public Double getValue() {
            return Value;
        }

        public void setValue(Double value) {
            Value = value;
        }

        public String getCurrencyID() {
            return CurrencyID;
        }

        public void setCurrencyID(String currencyID) {
            CurrencyID = currencyID;
        }
    }

    public class sellerDetails{
        private String UserID;

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String userID) {
            UserID = userID;
        }
    }

    public class shippingService{
        private double Value;

        public double getValue() {
            return Value;
        }

        public void setValue(double value) {
            Value = value;
        }
    }
}
