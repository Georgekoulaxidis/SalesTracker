package com.example.salestracker;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemRecommendation {

    private List<ItemResponse> getSimilarItemsResponse;

    public List<ItemResponse> getGetSimilarItemsResponse() {
        return getSimilarItemsResponse;
    }

    public void setGetSimilarItemsResponse(List<ItemResponse> getSimilarItemsResponse) {
        this.getSimilarItemsResponse = getSimilarItemsResponse;
    }

    public class ItemResponse {
        private String ack;
        private List<ObjectItem> itemRecommendation;

        public String getAck() {
            return ack;
        }

        public void setAck(String ack) {
            this.ack = ack;
        }

        public List<ObjectItem> getItemRecommendation() {
            return itemRecommendation;
        }

        public void setItemRecommendation(List<ObjectItem> itemRecommendation) {
            this.itemRecommendation = itemRecommendation;
        }
    }

    public class ObjectItem {

        private List<RecommendedItem> item;

        public List<RecommendedItem> getItem() {
            return item;
        }

        public void setItem(List<RecommendedItem> item) {
            this.item = item;
        }
    }

    public class RecommendedItem{
        private String itemId;
        private String title;
        private String viewItemURL;
        private String globalId;

        private List<PriceDetails> currentPrice;

        public String getItemId() {
            return itemId;
        }

        public void setItemId(String itemId) {
            this.itemId = itemId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getViewItemURL() {
            return viewItemURL;
        }

        public void setViewItemURL(String viewItemURL) {
            this.viewItemURL = viewItemURL;
        }

        public String getGlobalId() {
            return globalId;
        }

        public void setGlobalId(String globalId) {
            this.globalId = globalId;
        }

        public List<PriceDetails> getCurrentPrice() {
            return currentPrice;
        }

        public void setCurrentPrice(List<PriceDetails> currentPrice) {
            this.currentPrice = currentPrice;
        }
    }

    public class PriceDetails{
        @SerializedName("@currencyId")
        private String currency;
        private String __value__;

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String get__value__() {
            return __value__;
        }

        public void set__value__(String __value__) {
            this.__value__ = __value__;
        }
    }
}
