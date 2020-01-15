package com.example.salestracker;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
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
    private String newProduct;
    private String usedProduct;
    private boolean freeShipping = false;
    private boolean payment = false;
    private String min = new String();
    private String max = new String();
    private String country = new String();

    private FetchProductsTask task;
    private ArrayList<GsonProduct.item> item = new ArrayList<>();

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
        currencySpinner = view.findViewById( R.id.countrySpinner );
        minPlain = view.findViewById( R.id.minPlain );
        maxPlain = view.findViewById( R.id.maxPlain );

        searchBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keywords = searchPlain.getText().toString(); //Take input from user
                if (newBox.isChecked() && !usedBox.isChecked()) {
                    usedProduct = "1000";
                    newProduct = "1000";
                }
                else if(!newBox.isChecked() && usedBox.isChecked()) {
                    usedProduct = "3000";
                    newProduct = "3000";
                }
                else if(newBox.isChecked() && usedBox.isChecked()){
                    usedProduct = "3000";
                    newProduct = "1000";
                }
                else {
                    newProduct = "New";
                    usedProduct = "Used";
                }

                Log.d( "Dennis", newProduct);
                freeShipping = freeShippingBox.isChecked();
                payment = paymentBox.isChecked();
                min = minPlain.getText().toString();
                if(min.isEmpty())
                    min = "0.0";
                max = maxPlain.getText().toString();
                if(max.isEmpty())
                    max = "999999999999999.0";
                 if(currencySpinner.getSelectedItem().equals("USA")){
                     country = "EBAY-US";
                 }
                 else if(currencySpinner.getSelectedItem().equals("DEU"))
                    country = "EBAY-DE";
                 else if(currencySpinner.getSelectedItem().equals("UK"))
                     country = "EBAY-GB";

                MainActivity mHelper = (MainActivity) getActivity();
                //Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
                //intent.putExtra("item", GetJson());

                item = GetJson();
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                //if(item.size() != 0)
                mHelper.ReceiveJson( item );
                //else
                    //mHelper.NullPossibility();
            }
        } );


        return view;
    }


    public ArrayList<GsonProduct.item> GetJson() {
        task = new FetchProductsTask(getActivity(), keywords, newProduct, usedProduct, freeShipping, payment, min, max, country);
        try {
            if(task!= null)
                return (ArrayList<GsonProduct.item>) task.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
