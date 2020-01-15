package com.example.salestracker;

import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class PopupClass {

    public void showPopup(List<GsonProduct.item> products, final View view, int position) {
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

        if(!products.get(position).getGalleryURL(0).equals("")) {
            ImageView productImage = popupView.findViewById(R.id.productImage);
            Picasso.get()
                    .load(products.get(position).getGalleryURL(0))
                    .resize( 700, 700 )
                    .centerInside()
                    .into(productImage);
        }

        TextView titleTextView = popupView.findViewById(R.id.titleTxt);
        titleTextView.setText(products.get(position).getTitle(0));

        TextView priceTextView = popupView.findViewById(R.id.priceTxt);
        priceTextView.append(String.valueOf(products.get(position).getSellingStatus()
                                .get(0).getPriceDetails().get(0).get__value__()) + " " +
                                products.get(position).getSellingStatus().get(0)
                                .getPriceDetails().get(0).getCurrency());

        TextView sellerTextView = popupView.findViewById(R.id.sellerTxt);
        sellerTextView.append(products.get(position).getSellerInfo()
                .get(0).getSellerUsername(0));

        ImageButton closeButton = popupView.findViewById(R.id.ib_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                popupWindow.dismiss();
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }

}
