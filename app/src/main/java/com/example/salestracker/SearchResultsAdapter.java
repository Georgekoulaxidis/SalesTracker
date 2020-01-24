
package com.example.salestracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SearchResultsAdapter extends ArrayAdapter<GsonProduct.item> {


    private final LayoutInflater inflater;
    private final int layoutResource;
    private List<GsonProduct.item> products;
    private boolean favs;

    public SearchResultsAdapter(@NonNull Context context, int resource, @NonNull List<GsonProduct.item> products) {
        super( context, resource, products);
        inflater = LayoutInflater.from(context);
        layoutResource = resource;
        this.products = products;
    }

    public int getCount(){
        return products.size();
    }

    @NotNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        final View view = inflater.inflate(layoutResource, parent, false);

        final GsonProduct.item currentProduct = products.get(position);

        TextView titleTxt = view.findViewById(R.id.titleTxt);
        TextView priceTxt = view.findViewById(R.id.priceTxt);
        TextView sellerTxt = view.findViewById(R.id.sellerTxt);
        ImageView imageView = view.findViewById(R.id.productImage);
        CheckBox chBox = view.findViewById(R.id.favsCheckBox);

        Log.v("Dennis", currentProduct.getGalleryURL(0));
        Picasso.get()
                .load(currentProduct.getGalleryURL(0))
                .resize( 250, 250 )
                .centerInside()
                .into(imageView);

        titleTxt.setText(currentProduct.getTitle(0));
        sellerTxt.setText(currentProduct.getSellerInfo().get(0).getSellerUsername(0));
        priceTxt.setText( String.valueOf(currentProduct.getSellingStatus().get(0).getPriceDetails().get(0).get__value__())
                            + " " + currentProduct.getSellingStatus().get(0).getPriceDetails().get(0).getCurrency());
        Log.d("Favourites", MainActivity.favourites.toString());
        if(favs){
            chBox.setVisibility(View.INVISIBLE);
        }
        else {
            for(GsonProduct.item fp: MainActivity.favourites) {
                if(fp.getItemId().get(0).equals(currentProduct.getItemId().get(0))) {
                    chBox.setChecked(true);
                }
            }
            chBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                    DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                    if(isChecked) {
                        MainActivity.favourites.add(currentProduct);
                        dbHelper.addProductToFavs(MainActivity.loggedInUser.getId(), currentProduct);
                        Snackbar.make(view, "Checked!",
                                Snackbar.LENGTH_LONG).show();
                    }
                    else {
                        for(GsonProduct.item fp: MainActivity.favourites) {
                            if (fp.getItemId().get(0).equals(currentProduct.getItemId().get(0))) {
                                MainActivity.favourites.remove(currentProduct);
                                dbHelper.deleteProductFromFavs(MainActivity.loggedInUser.getId(), currentProduct.getItemId().get(0));
                            }
                        }

                        Snackbar.make(view,"NOT Checked!",
                                Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }

        return view;
    }

    public void setFavs(boolean favs) {
        this.favs = favs;
    }
}