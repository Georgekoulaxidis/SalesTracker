package com.example.salestracker;


import android.net.Uri;

import java.io.BufferedReader;
import java.net.HttpURLConnection;

public class RequestEbay {
    static final String PRODUCT_TITLE = "title";
    static final String PRODUCT_PRICE = "currentPrice";


    //From here we can pass every user's choices and create the Ebay request.
    protected String MakeRequest(String[] keywords){


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try{

            String basicUrl = "https://svcs.ebay.com/services/search/FindingService/v1?";

            String operation = "OPERATION-NAME=findItemsByKeywords";
            String version = "SERVICE-VERSION=1.0.0";
            String appid = "SECURITY-APPNAME=Dionisis-SmartSho-PRD-0388a6d5f-56b83621";
            String dataType = "RESPONSE-DATA-FORMAT=XML";
            String payload = "REST-PAYLOAD";

            Uri builtUri = Uri.parse(basicUrl).buildUpon()
                    .appendQueryParameter(operation, "findItemsByKeywords")
                    .appendQueryParameter(version, "1.0.0")
                    .appendQueryParameter(appid, "Dionisis-SmartSho-PRD-0388a6d5f-56b83621")
                    .appendQueryParameter(dataType, "JSON")
                    .appendQueryParameter(payload,"")
                    .build();



        } finally {

        }


    }


}
