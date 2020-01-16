package com.example.salestracker;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GsonProduct {

    private List<ItemsByKeywords> findItemsByKeywordsResponse;


    public class ItemsByKeywords{

        //private List<String> ack;
        //private List<String> version;
        //private List<String> timestamp;
        private List<searchResult> searchResult;
        //private List<String> paginationOutput;
        //private List<String> itemSearchUrl;



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
        @SerializedName("@count")
        private Integer count;

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public List<item> getItem() {
            return item;
        }

        public void setItem(List<item> item) {
            this.item = item;
        }

    }

     @SuppressLint("ParcelCreator")
     public class item extends GsonProduct implements Parcelable {
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
        private List<Shipping> shippingInfo;
        private List<SellingStatus> sellingStatus;
        private List<Seller> sellerInfo;
        //private List<String> listingInfo;
        private List<Boolean> returnsAccepted;
        private List<ConditionDetails> condition;
        private List<Boolean> isMultiVariationListing;
        private List<Boolean> topRatedListing;



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

        public String getGalleryURL(int i) {
            return galleryURL.get(i);
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

        public List<Shipping> getShippingInfo() {
            return shippingInfo;
        }

        public void setShippingInfo(List<Shipping> shippingInfo) {
            this.shippingInfo = shippingInfo;
        }

        public List<SellingStatus> getSellingStatus() {
            return sellingStatus;
        }

        public void setSellingStatus(List<SellingStatus> sellingStatus) {
            this.sellingStatus = sellingStatus;
        }

        public List<Seller> getSellerInfo() { return sellerInfo; }

        public void setSellerInfo(List<Seller> sellerInfo) { this.sellerInfo = sellerInfo; }


        /*
        public List<String> getListingInfo() {
            return listingInfo;
        }

        public void setListingInfo(List<String> listingInfo) {
            this.listingInfo = listingInfo;
        }

        public List<Boolean> getReturnsAccepted() {
            return returnsAccepted;
        }

        public void setReturnsAccepted(List<Boolean> returnsAccepted) {
            this.returnsAccepted = returnsAccepted;
        }*/

        public List<ConditionDetails> getCondition() {
            return condition;
        }

        public void setCondition(List<ConditionDetails> condition) {
            this.condition = condition;
        }

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

         @Override
         public int describeContents() {
             return 0;
         }

         @Override
         public void writeToParcel(Parcel parcel, int i) {

         }
     }

     public class ConditionDetails {
        private List<String> conditionDisplayName;

        public String getConditionDisplayName(int i) { return conditionDisplayName.get(i); }
     }

     public class SellingStatus{
        private List<PriceDetails> currentPrice;

         public List<PriceDetails> getPriceDetails() {
             return currentPrice;
         }

         public void setPriceDetails(List<PriceDetails> priceDetails) {
             this.currentPrice = priceDetails;
         }
     }

     public class PriceDetails{
        @SerializedName("@currencyId")
        private String currency;
        private double __value__;

         public String getCurrency() {
             return currency;
         }

         public void setCurrency(String currency) {
             this.currency = currency;
         }

         public double get__value__() {
             return __value__;
         }

         public void set__value__(double __value__) {
             this.__value__ = __value__;
         }
     }


    public class Seller {
        private List<String> sellerUserName;

        public String getSellerUsername(int i) { return sellerUserName.get(i); }
    }

    public class Shipping{
        private List<shippingCost> shippingServiceCost;
        private List<String> shippingType;

        public List<shippingCost> getShippingServiceCost() {
            return shippingServiceCost;
        }

        public String getShippingType(int i) { return shippingType.get(0); }

        public void setShippingServiceCost(List<shippingCost> shippingServiceCost) {
            this.shippingServiceCost = shippingServiceCost;
        }
    }

    public class shippingCost{
        private String currencyId;
        private double __value__;

        public String getCurrencyId() {
            return currencyId;
        }

        public void setCurrencyId(String currencyId) {
            this.currencyId = currencyId;
        }

        public double get__value__() {
            return __value__;
        }

        public void set__value__(double __value__) {
            this.__value__ = __value__;
        }
    }


    public List<ItemsByKeywords> getFindItemsByKeywordsResponse() {
        return findItemsByKeywordsResponse;
    }

    public void setFindItemsByKeywordsResponse(List<ItemsByKeywords> findItemsByKeywordsResponse) {
        this.findItemsByKeywordsResponse = findItemsByKeywordsResponse;
    }
}
