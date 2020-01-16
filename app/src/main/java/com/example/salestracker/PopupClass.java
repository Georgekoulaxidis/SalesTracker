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

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import java.util.List;

public class PopupClass {

    public void showPopup(List<GsonProduct.item> products, final View view, int position, final ArrayAdapter adapter) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);

        final View popupView = inflater.inflate(R.layout.popup_layout, null);
        //int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        //int height = LinearLayout.LayoutParams.WRAP_CONTENT;
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
        priceTextView.append(String.valueOf(currentProduct.getSellingStatus()
                                .get(0).getPriceDetails().get(0).get__value__()) + " " +
                                currentProduct.getSellingStatus().get(0)
                                .getPriceDetails().get(0).getCurrency());

        TextView sellerTextView = popupView.findViewById(R.id.sellerTxt);
        sellerTextView.append(currentProduct.getSellerInfo()
                .get(0).getSellerUsername(0));

        CheckBox favCheckBox = popupView.findViewById(R.id.checkBox);
        Log.d("Favourites", MainActivity.favourites.toString());
        for(FavsProduct fp: MainActivity.favourites) {
            if(fp.getProductId().equals(currentProduct.getItemId().get(0))) {
                favCheckBox.setChecked(true);
            }
        }
        favCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                DatabaseHelper dbHelper = new DatabaseHelper(popupView.getContext());
                FavsProduct tempFav = new FavsProduct();
                if(isChecked) {
                    tempFav = new FavsProduct(MainActivity.loggedInUser.getId(),
                            currentProduct.getItemId().get(0),
                            currentProduct.getTitle(0),
                            currentProduct.getSellingStatus().get(0).getPriceDetails().get(0).get__value__(),
                            currentProduct.getSellerInfo().get(0).getSellerUsername(0),
                            currentProduct.getGalleryURL(0),
                            currentProduct.getCondition().get(0).getConditionDisplayName(0),
                            currentProduct.getGlobalId().get(0),
                            (currentProduct.getShippingInfo().get(0).getShippingType(0)).equals("Free"));

                    MainActivity.favourites.add(tempFav);
                    dbHelper.addProductToFavs(tempFav);
                    Snackbar.make(popupView, "Product add in Favourites list!",
                            Snackbar.LENGTH_LONG).show();
                }
                else {
                    for(FavsProduct fp: MainActivity.favourites) {
                        if(fp.getProductId().equals(currentProduct.getItemId())) {
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
    }

}
