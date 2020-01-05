package com.example.salestracker;


import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchProductsTask extends AsyncTask<String, Void, GsonProduct> {

    private final String LOG_TAG = "Dennis";
    private String keyword;
    private String newProduct;
    private String usedProduct;
    private String freeShipping;
    private String payment;
    private String min;
    private String max;
    private String currency;
    private String jsonString;
    Context context;
    ListView productsList;
    GsonProduct product;
    private List<String> items = new ArrayList<>();


    public FetchProductsTask(Context context, String keywords, String newProduct, boolean freeShipping, boolean payment, String min, String max, String currency) {
        keyword = keywords;
        this.context = context;
        this.productsList = productsList;
        this.newProduct = newProduct;
        this.usedProduct = usedProduct;
        this.freeShipping = String.valueOf(freeShipping);
        this.payment = String.valueOf(payment);
        this.min = min;
        this.max = max;
        this.currency = currency;
    }

    @Override
    protected GsonProduct doInBackground(String... strings) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        Log.d(LOG_TAG,"ASAP!");

        try{

            String basicUrl = "https://svcs.ebay.com/services/search/FindingService/v1?";

            String operation = "OPERATION-NAME";
            String version = "SERVICE-VERSION";
            String appid = "SECURITY-APPNAME";
            String dataType = "RESPONSE-DATA-FORMAT";
            String payload = "REST-PAYLOAD";
            String keywordsParam = "keywords";
            String pages = "paginationInput.entriesPerPage";
            String filterName = "itemFilter.name";
            String filterValue = "itemFilter.value";


            Uri builtUri = Uri.parse(basicUrl).buildUpon()
                    .appendQueryParameter(operation, "findItemsByKeywords")
                    .appendQueryParameter(version, "1.0.0")
                    .appendQueryParameter(appid, "Dionisis-SmartSho-PRD-0388a6d5f-56b83621")
                    .appendQueryParameter(dataType, "JSON")
                    .appendQueryParameter(payload,"")
                    .appendQueryParameter(keywordsParam, keyword)
                    .appendQueryParameter(filterName, "FreeShippingOnly")
                    .appendQueryParameter(filterValue, freeShipping)
                    .appendQueryParameter(filterName, "Condition")
                    .appendQueryParameter(filterValue, newProduct)
                    .appendQueryParameter(filterName, "MinPrice")
                    .appendQueryParameter(filterValue, min)
                    .appendQueryParameter(filterName, "MaxPrice")
                    .appendQueryParameter(filterValue, max)
                    .appendQueryParameter( pages, "20" )
                    .build();

            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            jsonString = buffer.toString();
            Log.v(LOG_TAG, "Forecast JSON String: " + jsonString);
            Gson gson = new Gson();
            product = gson.fromJson(jsonString, GsonProduct.class);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }


        int size = product.getFindItemsByKeywordsResponse().get(0).getSearchResult().get(0).getItem().size();
        for(int i = 0; i <size;i++){
            items.add( String.valueOf( product.getFindItemsByKeywordsResponse().get(0).getSearchResult().get(0).getItem().get(i).getShippingInfo().get(0).getShippingServiceCost().get(0).get__value__() ) );
        }


        return product;
    }

    /*
    @Override
    protected void onPostExecute(GsonProduct p) {
        if(p != null) {
            ArrayAdapter<String> products = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, items);
            productsList.setAdapter(products);
            
        }

    }*/
}
