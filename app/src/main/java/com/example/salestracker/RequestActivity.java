package com.example.salestracker;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Request;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.textclassifier.ConversationActions;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_request );

        //TextView testTextView = findViewById(R.id.testTextView);
        //TextView JustTextView = findViewById(R.id.justTextView);

        ListView productsList = findViewById(R.id.productsList);
        context = getApplicationContext();

        String keywordInput = new String();
        String newProduct = new String();
        String usedProduct = new String();
        boolean freeShipping = false;
        boolean payment = false;
        String min = new String();
        String max = new String();
        String currency = new String();
        //ArrayList<String> keywords = new ArrayList<String>();

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            keywordInput = extras.getString( "keywords" );
            if(extras.getBoolean("new"))
                newProduct = "New";


            if(extras.getBoolean("used"))
                usedProduct = "Used";

            freeShipping = extras.getBoolean("freeShipping");
            payment = extras.getBoolean("payment");
            min = extras.getString( "min" );
            max = extras.getString( "max" );
            currency = extras.getString( "currency" );


        }






        FetchProductsTask task = new FetchProductsTask(context, keywordInput, productsList, newProduct, usedProduct, freeShipping, payment, min, max, currency);
        task.execute();

    }
}
