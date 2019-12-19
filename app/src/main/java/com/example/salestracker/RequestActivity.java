package com.example.salestracker;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Request;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.textclassifier.ConversationActions;
import android.widget.TextView;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {


    private String keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_request );

        TextView testTextView = findViewById(R.id.testTextView);

        String keywordInput = new String();
        //ArrayList<String> keywords = new ArrayList<String>();

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            keywordInput = extras.getString( "keywords" );
        }

       //keywords = keywordInput.replace(" ", "%20");


        FetchProductsTask task = new FetchProductsTask(keywordInput, testTextView);
        task.execute();

    }
}
