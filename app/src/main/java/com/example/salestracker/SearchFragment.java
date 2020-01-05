package com.example.salestracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {

    private EditText searchPlain;
    private Button searchBtn;
    private CheckBox newBox;
    private CheckBox usedBox;
    private CheckBox freeShippingBox;
    private CheckBox paymentBox;
    private Spinner currencySpinner;
    private EditText minPlain;
    private EditText maxPlain;
    private String keywords = new String();
    private String newProduct = new String();
    private boolean freeShipping = false;
    private boolean payment = false;
    private String min = new String();
    private String max = new String();
    private String currency = new String();

    private FetchProductsTask task;
    private GsonProduct item;

    //private OnFragmentInteractionListener mListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.search_layout, container, false );

        searchPlain = view.findViewById( R.id.searchPlain );
        searchBtn = view.findViewById( R.id.searchBtn );
        newBox = view.findViewById( R.id.newBox );
        usedBox = view.findViewById( R.id.usedBox );
        freeShippingBox = view.findViewById( R.id.freeShippingBox );
        paymentBox = view.findViewById( R.id.paymentBox );
        currencySpinner = view.findViewById( R.id.currenciesSpinner );
        minPlain = view.findViewById( R.id.minPlain );
        maxPlain = view.findViewById( R.id.maxPlain );

        searchBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywords = searchPlain.getText().toString(); //Take input from user
                if (newBox.isChecked())
                    newProduct = "New";
                Log.d( "Dennis", String.valueOf( newProduct ) );
                freeShipping = freeShippingBox.isChecked();
                payment = paymentBox.isChecked();
                min = minPlain.getText().toString();
                max = maxPlain.getText().toString();
                currency = (String) currencySpinner.getSelectedItem();

                MainActivity mHelper = (MainActivity) getActivity();
                //Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                //intent.putExtra("item", GetJson());
                mHelper.ReceiveJson( GetJson() );
            }
        } );


        return view;
    }


    public GsonProduct GetJson() {
        task = new FetchProductsTask( getActivity(), keywords, newProduct, freeShipping, payment, min, max, currency );
        try {
            return task.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
