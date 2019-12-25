package com.example.salestracker;

import android.util.Log;

import java.util.List;

public class GsonProduct {

    private List<ItemsByKeywords> findItemsByKeywordsResponse;


    public GsonProduct(List<ItemsByKeywords> findItemsByKeywordsResponse){
        super();
        this.findItemsByKeywordsResponse = findItemsByKeywordsResponse;

    }

    public class ItemsByKeywords{



        //private List<String> ack;
        //private List<String> version;
        //private List<String> timestamp;
        private List<searchResult> searchResult;
        //private List<String> paginationOutput;
        //private List<String> itemSearchUrl;

        public ItemsByKeywords(List<searchResult> searchResult){
            super();
            this.searchResult = searchResult;
        }

        /*public List<String> getAck() {
            return ack;
        }

        public void setAck(List<String> ack) {
            this.ack = ack;
        }

        public List<String> getVersion() {
            return version;
        }

        public void setVersion(List<String> version) {
            this.version = version;
        }

        public List<String> getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(List<String> timestamp) {
            this.timestamp = timestamp;
        }*/

        public List<searchResult> getSearchResult() {
            return searchResult;
        }

        public void setSearchResult(List<searchResult> searchResult) {
            this.searchResult = searchResult;
        }

        /*public List<String> getPaginationOutput() {
            return paginationOutput;
        }

        public void setPaginationOutput(List<String> paginationOutput) {
            this.paginationOutput = paginationOutput;
        }

        public List<String> getItemSearchUrl() {
            return itemSearchUrl;
        }

        public void setItemSearchUrl(List<String> itemSearchUrl) {
            this.itemSearchUrl = itemSearchUrl;
        }*/
    }

    public class searchResult{

        private List<item> item;

        public searchResult(List<item> item){
            super();
            this.item = item;
        }

        public List<item> getItem() {
            return item;
        }

        public void setItem(List<item> item) {
            Log.d("Dennis", String.valueOf(item));
            this.item = item;
        }

    }

     public class item{
        private List<String> itemId;
        private List<String> title;
        private List<String> globalId;
        //private List<String> primaryCategory;
        private List<String> galleryURL;
        private List<String> viewItemURL;
        //private List<String> productId;
        private List<String> paymentMethod;
        private List<Boolean> autoPay;
        private List<String> postalCode;
        private List<String> location;
        private List<String> country;
        //private List<String> shippingInfo;
        //private List<String> sellingStatus;
        //private List<String> listingInfo;
        private List<Boolean> returnsAccepted;
        //private List<String> condition;
        private List<Boolean> isMultiVariationListing;
        private List<Boolean> topRatedListing;

        public item(List<String> itemId, List<String> title, List<String> globalId, List<String> galleryURL, List<String> viewItemURL, List<String> paymentMethod, List<Boolean> autoPay, List<String> postalCode, List<String> location, List<String> country, List<Boolean> returnsAccepted, List<Boolean> isMultiVariationListing, List<Boolean> topRatedListing) {
            super();
            this.itemId = itemId;
            this.title = title;
            this.globalId = globalId;
            this.galleryURL = galleryURL;
            this.viewItemURL = viewItemURL;
            this.paymentMethod = paymentMethod;
            this.autoPay = autoPay;
            this.postalCode = postalCode;
            this.location = location;
            this.country = country;
            this.returnsAccepted = returnsAccepted;
            this.isMultiVariationListing = isMultiVariationListing;
            this.topRatedListing = topRatedListing;
        }

        public List<String> getItemId() {
            return itemId;
        }

        public void setItemId(List<String> itemId) {
            this.itemId = itemId;
        }

        public String getTitle(int i) {
            return title.get(i);
        }

        public void setTitle(List<String> title) {
            this.title = title;
        }

        public List<String> getGlobalId() {
            return globalId;
        }

        public void setGlobalId(List<String> globalId) {
            this.globalId = globalId;
        }

        /*public List<String> getPrimaryCategory() {
            return primaryCategory;
        }

        public void setPrimaryCategory(List<String> primaryCategory) {
            this.primaryCategory = primaryCategory;
        }*/

        public List<String> getGalleryURL() {
            return galleryURL;
        }

        public void setGalleryURL(List<String> galleryURL) {
            this.galleryURL = galleryURL;
        }

        public List<String> getViewItemURL() {
            return viewItemURL;
        }

        public void setViewItemURL(List<String> viewItemURL) {
            this.viewItemURL = viewItemURL;
        }

        /*public List<String> getProductId() {
            return productId;
        }

        public void setProductId(List<String> productId) {
            this.productId = productId;
        }*/

        public List<String> getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(List<String> paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public List<Boolean> getAutoPay() {
            return autoPay;
        }

        public void setAutoPay(List<Boolean> autoPay) {
            this.autoPay = autoPay;
        }

        public List<String> getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(List<String> postalCode) {
            this.postalCode = postalCode;
        }

        public List<String> getLocation() {
            return location;
        }

        public void setLocation(List<String> location) {
            this.location = location;
        }

        public List<String> getCountry() {
            return country;
        }

        public void setCountry(List<String> country) {
            this.country = country;
        }

        /*public List<String> getShippingInfo() {
            return shippingInfo;
        }

        public void setShippingInfo(List<String> shippingInfo) {
            this.shippingInfo = shippingInfo;
        }

        public List<String> getSellingStatus() {
            return sellingStatus;
        }

        public void setSellingStatus(List<String> sellingStatus) {
            this.sellingStatus = sellingStatus;
        }

        public List<String> getListingInfo() {
            return listingInfo;
        }

        public void setListingInfo(List<String> listingInfo) {
            this.listingInfo = listingInfo;
        }*/

        public List<Boolean> getReturnsAccepted() {
            return returnsAccepted;
        }

        public void setReturnsAccepted(List<Boolean> returnsAccepted) {
            this.returnsAccepted = returnsAccepted;
        }

        /*public List<String> getCondition() {
            return condition;
        }

        public void setCondition(List<String> condition) {
            this.condition = condition;
        }*/

        public List<Boolean> getIsMultiVariationListing() {
            return isMultiVariationListing;
        }

        public void setIsMultiVariationListing(List<Boolean> isMultiVariationListing) {
            this.isMultiVariationListing = isMultiVariationListing;
        }

        public List<Boolean> getTopRatedListing() {
            return topRatedListing;
        }

        public void setTopRatedListing(List<Boolean> topRatedListing) {
            this.topRatedListing = topRatedListing;
        }
    }


    public List<ItemsByKeywords> getFindItemsByKeywordsResponse() {
        return findItemsByKeywordsResponse;
    }

    public void setFindItemsByKeywordsResponse(List<ItemsByKeywords> findItemsByKeywordsResponse) {
        this.findItemsByKeywordsResponse = findItemsByKeywordsResponse;
    }
}
