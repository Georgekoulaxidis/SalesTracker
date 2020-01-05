package com.example.salestracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ProductsFragment extends Fragment {



    private String testText = "";

    public ProductsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.custom_row, container, false );

        testText = getArguments().getString("json");
        //Log.d("Dennis",testText);

        TextView justText = view.findViewById(R.id.titleTxt);

        justText.setText( testText );

        return view;
    }


}
