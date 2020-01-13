package com.example.salestracker;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ProductsFragment extends Fragment {

    private ArrayList<GsonProduct.item> itemList =  new ArrayList<>();
    private static WeakReference<Activity> mActivityRef;
    private static ListView listProducts;

    public static void updateActivity(Activity activity){
        mActivityRef = new WeakReference<Activity>(activity);
    }

    public ProductsFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_request, container, false );


        if (getArguments() != null) {
            itemList = getArguments().getParcelableArrayList("Json");
        }
        Log.d("Dennis", String.valueOf( itemList) );


        if(itemList.size() != 0) {
            listProducts = view.findViewById( R.id.productsList );
            ConnectAdapter myAdapter = new ConnectAdapter( mActivityRef.get(), R.layout.custom_row, itemList );
            listProducts.setAdapter( myAdapter );
            listProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    new PopupClass().showPopup(itemList, view, position);
                }
            });
        }else{
            TextView titleResult = view.findViewById( R.id.titleResult);
            titleResult.setText("We didn't find any product");
        }

        return view;
    }


}
