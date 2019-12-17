package com.example.salestracker;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Request;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.textclassifier.ConversationActions;

public class RequestActivity extends AppCompatActivity {


    public String YT_KEY = "Dionisis-SmartSho-PRD-0388a6d5f-56b83621";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_request );

        String url = "https://svcs.ebay.com/services/search/FindingService/v1?";

        String operation = "OPERATION-NAME=findItemsByKeywords";
        String version = "SERVICE-VERSION=1.0.0";
        String appid = "SECURITY-APPNAME=Dionisis-SmartSho-PRD-0388a6d5f-56b83621";
        String dataType = "RESPONSE-DATA-FORMAT=XML";
        String payload = "REST-PAYLOAD";
        


        //Request request = new Request.Builder()

    }
}
