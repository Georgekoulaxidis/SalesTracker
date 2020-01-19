package com.example.salestracker;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
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

public class FetchProductsTask extends AsyncTask<String, Void, List<GsonProduct.item>> {

    private final String LOG_TAG = "Dennis";
    private String keyword;
    private String newProduct;
    private String usedProduct;
    private String freeShipping;
    private String payment;
    private String min;
    private String max;
    private String country;
    private String jsonString;
    private Context context;
    ListView productsList;
    GsonProduct product;
    List<GsonProduct.item> itemList = new ArrayList<>(  );
    private Integer size = 0;

    private ProgressDialog progressDialog;

    public FetchProductsTask(Context context, String keywords, String newProduct, String usedProduct, boolean freeShipping, boolean payment, String min, String max, String country) {
        keyword = keywords;
        this.context = context;
        //this.productsList = productsList;
        this.newProduct = newProduct;
        this.usedProduct = usedProduct;
        this.freeShipping = String.valueOf(freeShipping);
        this.payment = String.valueOf(payment);
        this.min = min;
        this.max = max;
        this.country = country;

        progressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setTitle("Please wait.. The data you asked are being fetched..");
        progressDialog.setMessage("The data you asked are being fetched..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    protected List<GsonProduct.item> doInBackground(String... strings) {
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
            String outputSelectorParam = "outputSelector";
            String pages = "paginationInput.entriesPerPage";
            //String filterName = "itemFilter.name";
            //String filterValue = "itemFilter.value";
            //String paramName = "itemFilter.paramName";
            //String paramValue = "itemFilter.paramValue";


            Uri builtUri = Uri.parse(basicUrl).buildUpon()
                    .appendQueryParameter(operation, "findItemsByKeywords")
                    .appendQueryParameter(version, "1.0.0")
                    .appendQueryParameter(appid, "Dionisis-SmartSho-PRD-0388a6d5f-56b83621")
                    .appendQueryParameter(dataType, "JSON")
                    .appendQueryParameter("GLOBAL-ID", country)
                    .appendQueryParameter(payload,"")
                    .appendQueryParameter(keywordsParam, keyword)
                    .appendQueryParameter("itemFilter(0).name", "FreeShippingOnly")
                    .appendQueryParameter("itemFilter(0).value", freeShipping)
                    .appendQueryParameter("itemFilter(1).name","MinPrice")
                    .appendQueryParameter("itemFilter(1).value", min)
                    .appendQueryParameter("itemFilter(2).name","MaxPrice")
                    .appendQueryParameter("itemFilter(2).value", max)
                    .appendQueryParameter("itemFilter(3).name", "Condition")
                    .appendQueryParameter("itemFilter(3).value(0)", newProduct)
                    .appendQueryParameter("itemFilter(3).value(1)", usedProduct)
                    .appendQueryParameter("itemFilter(4).name", "HideDuplicateItems")
                    .appendQueryParameter("itemFilter(4).value", "true")
                    .appendQueryParameter(outputSelectorParam, "SellerInfo")
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


        size = product.getFindItemsByKeywordsResponse().get(0).getSearchResult().get(0).getCount();
        Log.v(LOG_TAG,String.valueOf(size));
        for(int i = 0; i <size;i++){
            itemList.add(product.getFindItemsByKeywordsResponse().get(0).getSearchResult().get(0).getItem().get(i));
        }


        return itemList;
    }

    public int getSize(){
        return size;
    }

    @Override
    protected void onPostExecute(List<GsonProduct.item> items) {
        progressDialog.dismiss();
    }
}
