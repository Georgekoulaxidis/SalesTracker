package com.example.salestracker;

import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.salestracker.GsonParsing.GsonProduct;
import com.example.salestracker.db.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import java.util.List;

public class PopupClass {

    public void showPopup(List<GsonProduct.item> products, final View view, int position, final ArrayAdapter adapter) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);

        final View popupView = inflater.inflate(R.layout.popup_layout, null);
        Display display = view.getDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width-300, height-400, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        final GsonProduct.item currentProduct = products.get(position);

        if(!currentProduct.getGalleryURL(0).equals("")) {
            ImageView productImage = popupView.findViewById(R.id.productImage);
            Picasso.get()
                    .load(currentProduct.getGalleryURL(0))
                    .resize( 700, 700 )
                    .centerInside()
                    .into(productImage);
        }

        TextView titleTextView = popupView.findViewById(R.id.titleTxt);
        titleTextView.setText(currentProduct.getTitle(0));

        TextView priceTextView = popupView.findViewById(R.id.priceTxt);
        String currencySign = "";
        if(currentProduct.getSellingStatus().get(0).getPriceDetails().get(0).getCurrency().equals("USD"))
            currencySign = "$";
        else if(currentProduct.getSellingStatus().get(0).getPriceDetails().get(0).getCurrency().equals("EUR"))
            currencySign = "\u20ac";
        else if(currentProduct.getSellingStatus().get(0).getPriceDetails().get(0).getCurrency().equals("GBP"))
            currencySign = "\u00a3";
        priceTextView.append(String.valueOf(currentProduct.getSellingStatus()
                                .get(0).getPriceDetails().get(0).get__value__()) + " " +
                                currencySign);

        TextView sellerTextView = popupView.findViewById(R.id.sellerTxt);
        sellerTextView.append(currentProduct.getSellerInfo()
                .get(0).getSellerUsername(0));

        TextView globalIdTextView = popupView.findViewById(R.id.globalIdTxt);
        globalIdTextView.setText(currentProduct.getGlobalId().get(0));

        TextView locationTextView = popupView.findViewById(R.id.locationTxt);
        locationTextView.append(currentProduct.getLocation().get(0));

        TextView shippingTextView = popupView.findViewById(R.id.shippingTypeTxt);
        shippingTextView.append(currentProduct.getShippingInfo().get(0).getShippingType(0));

        TextView shippingCostTextView = popupView.findViewById(R.id.shippingCostTxt);
        if(currentProduct.getShippingInfo().get(0).getShippingType(0).equals("Calculated")) {
            shippingCostTextView.append("-");
        }
        else {
            shippingCostTextView.append(currentProduct.getShippingInfo().get(0).getShippingServiceCost().get(0).get__value__() + " " + currencySign);
        }

        TextView paymentMethodTextView = popupView.findViewById(R.id.paymentMethodTxt);
        paymentMethodTextView.append(currentProduct.getPaymentMethod().get(0));

        TextView conditionTextView = popupView.findViewById(R.id.conditionTxt);
        conditionTextView.append(currentProduct.getCondition().get(0).getConditionDisplayName(0));

        TextView itemURLTextView = popupView.findViewById(R.id.itemURLTxt);
        itemURLTextView.append(currentProduct.getViewItemURL().get(0));

        CheckBox favCheckBox = popupView.findViewById(R.id.checkBox);
        Log.d("Favourites", MainActivity.favourites.toString());
        for(GsonProduct.item fp: MainActivity.favourites) {
            if(fp.getItemId().get(0).equals(currentProduct.getItemId().get(0))) {
                favCheckBox.setChecked(true);
            }
        }
        favCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseHelper dbHelper = new DatabaseHelper(popupView.getContext());
                if(isChecked) {
                    MainActivity.favourites.add(currentProduct);
                    dbHelper.addProductToFavs(MainActivity.loggedInUser.getId(), currentProduct);
                    Snackbar.make(popupView, "Product added in your favourites list!",
                            Snackbar.LENGTH_LONG).show();
                }
                else {
                    for(GsonProduct.item fp: MainActivity.favourites) {
                        if(fp.getItemId().get(0).equals(currentProduct.getItemId().get(0))) {
                            MainActivity.favourites.remove(currentProduct);
                            dbHelper.deleteProductFromFavs(MainActivity.loggedInUser.getId(), currentProduct.getItemId().get(0));
                        }
                    }

                    Snackbar.make(popupView,"Product deleted from your favourites list..",
                            Snackbar.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
            }
        });

        ImageButton closeButton = popupView.findViewById(R.id.ib_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                adapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                adapter.notifyDataSetChanged();
                popupWindow.dismiss();
                return true;
            }
        });
    }


    /*public void showSngleItem(SingleProduct recproduct, final View view, final ArrayAdapter adapter) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);

        final View popupView = inflater.inflate(R.layout.popup_layout, null);
        Display display = view.getDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        boolean focusable = true;
        final PopupWindow popupWindow = new PopupWindow(popupView, width-300, height-400, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        final SingleProduct.itemDetails currentProduct = recproduct.getItem();

        if(!currentProduct.getGalleryURL().equals("")) {
            ImageView productImage = popupView.findViewById(R.id.productImage);
            Picasso.get()
                    .load(currentProduct.getGalleryURL())
                    .resize( 700, 700 )
                    .centerInside()
                    .into(productImage);
        }

        TextView titleTextView = popupView.findViewById(R.id.titleTxt);
        titleTextView.setText(currentProduct.getTitle());

        TextView priceTextView = popupView.findViewById(R.id.priceTxt);
        priceTextView.append(String.valueOf(currentProduct.getConvertedCurrentPrice().getCurrencyID()));

        *//*TextView sellerTextView = popupView.findViewById(R.id.sellerTxt);
        sellerTextView.append(currentProduct.getSellerInfo()
                .get(0).getSellerUsername(0));*//*

        CheckBox favCheckBox = popupView.findViewById(R.id.checkBox);
        Log.d("Favourites", MainActivity.favourites.toString());
        for(FavsProduct fp: MainActivity.favourites) {
            if(fp.getProductId().equals(currentProduct.getItemID())) {
                favCheckBox.setChecked(true);
            }
        }


        if(currentProduct.getShippingCostSummary().getValue()== 0.00)
            freeShipping = true;
        else
            freeShipping = false;
        favCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DatabaseHelper dbHelper = new DatabaseHelper(popupView.getContext());
                FavsProduct tempFav = new FavsProduct();
                if(isChecked) {
                    tempFav = new FavsProduct(MainActivity.loggedInUser.getId(),
                            currentProduct.getItemID(),
                            currentProduct.getTitle(),
                            currentProduct.getConvertedCurrentPrice().getValue(),
                            currentProduct.getSeller().getUserID(),
                            currentProduct.getGalleryURL(),
                            currentProduct.getConditionID(),
                            currentProduct.getCountry(),
                            SearchFragment.min,
                            SearchFragment.max,
                            SearchFragment.keywords,
                            freeShipping);

                    MainActivity.favourites.add(tempFav);
                    dbHelper.addProductToFavs(tempFav);
                    Snackbar.make(popupView, "Product add in Favourites list!",
                            Snackbar.LENGTH_LONG).show();
                }
                else {
                    for(FavsProduct fp: MainActivity.favourites) {
                        if(fp.getProductId().equals(currentProduct.getItemID())) {
                            tempFav = fp;
                        }
                    }

                    MainActivity.favourites.remove(tempFav);
                    dbHelper.deleteProductFromFavs(tempFav);
                    Snackbar.make(popupView,"Product deleted from Favourites List!",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });

        ImageButton closeButton = popupView.findViewById(R.id.ib_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                adapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                adapter.notifyDataSetChanged();
                popupWindow.dismiss();
                return true;
            }
        });
    }*/

}
