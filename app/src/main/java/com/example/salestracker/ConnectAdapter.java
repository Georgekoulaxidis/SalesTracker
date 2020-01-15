package com.example.salestracker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ConnectAdapter extends ArrayAdapter<GsonProduct.item> {


    private final LayoutInflater inflater;
    private final int layoutResource;
    private List<GsonProduct.item> products;

    public ConnectAdapter(@NonNull Context context, int resource, @NonNull List<GsonProduct.item> products) {
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
        View view = inflater.inflate(layoutResource, parent, false);

        GsonProduct.item currentProduct = products.get(position);

        TextView titleTxt = view.findViewById(R.id.titleTxt);
        TextView priceTxt = view.findViewById(R.id.priceTxt);
        TextView sellerTxt = view.findViewById(R.id.sellerTxt);
        ImageView imageView = view.findViewById(R.id.productImage);


        Log.v("Dennis", currentProduct.getGalleryURL(0));
        Picasso.get()
                .load(currentProduct.getGalleryURL(0))
                .resize( 250, 250 )
                .centerInside()
                .into(imageView);

        titleTxt.setText(currentProduct.getTitle(0));
        priceTxt.setText( String.valueOf(currentProduct.getSellingStatus().get(0).getPriceDetails().get(0).get__value__()));

        return view;
    }
}
