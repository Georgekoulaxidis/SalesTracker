package com.example.salestracker;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.salestracker.NotificationChan.CHANNEL_1_ID;
import static java.security.AccessController.getContext;


public class FetchProductsService extends JobService {

    private final String LOG_TAG = "Dennis";
    private String keyword;
    private String title;
    private double currentPrice;
    private String payment;
    private String freeShipping;
    private int min;
    private int max;
    private SingleProduct product;
    private String jsonString;
    private String code;
    private String siteId;
    private int action = 0;



    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("BootReceiver", "Job started");
        //doBackgroundWork(params, this);
        //FetchProductsTask task = new FetchProductsTask("aKeyword", null);
        //task.execute();

        return false;
    }

    private void doBackgroundWork(final JobParameters params, final Context c) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                CheckAvaialability(code);
                if(product.getAck().equals("Failure") || !product.getItem().getTitle().equals(title)){
                    //Product doesn't exist anymore! (RIP you should have order it earlier)
                    BuildNotification(MainActivity.getContext(),"Product out of stock", title + " has gone out of stock");
                } else if(product.getItem().getConvertedCurrentPrice().getValue() < currentPrice){
                    //Notification to inform the user about the price drop.
                    BuildNotification(MainActivity.getContext(),"Price dropped", title + " price has dropped");

                }
                else{
                    action = 1;
                    CheckAvaialability(code);
                    action = 0;
                    
                }

                //BuildNotification(getContext(),"You have failed Anakin", "I have the high ground");
                Log.d("BootReceiver", "Thread running");
                jobFinished(params, true);
            }
        }).start();
    }

    public void CheckAvaialability(String code){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        Log.d(LOG_TAG,"ASAP!");

        try{
            Uri builtUri;
            String basicUrl;
            String appid;
            String version;
            String itemId;

            if(action == 0) {
                basicUrl = "https://open.api.ebay.com/shopping?";
                String call = "callname";
                String datatype = "responseencoding";
                appid = "appid";
                String siteId = "siteid";
                version = "version";
                itemId = "itemID";

                builtUri = Uri.parse( basicUrl ).buildUpon()
                        .appendQueryParameter( call, "GetSingleItem" )
                        .appendQueryParameter( datatype, "JSON" )
                        .appendQueryParameter( appid, "Dionisis-SmartSho-PRD-0388a6d5f-56b83621" )
                        .appendQueryParameter( siteId, "0" )
                        .appendQueryParameter( version, "967" )
                        .appendQueryParameter( itemId, code )
                        .build();
            }
            else{

                basicUrl = "http://svcs.ebay.com/MerchandisingService?";
                String operation = "OPERATION-NAME";
                String servName = "SERVICE-NAME";
                version = "SERVICE-VERSION";
                appid = "CONSUMER-ID";
                String dataType = "RESPONSE-DATA-FORMAT";
                String payload = "REST-PAYLOAD";
                itemId = "itemId";
                String results = "maxResults";

                builtUri = Uri.parse( basicUrl ).buildUpon()
                        .appendQueryParameter( operation, "getSimilarItems" )
                        .appendQueryParameter( servName, "MerchandisingService" )
                        .appendQueryParameter( version, "1.1.0" )
                        .appendQueryParameter( appid, "Dionisis-SmartSho-PRD-0388a6d5f-56b83621" )
                        .appendQueryParameter(dataType, "JSON")
                        .appendQueryParameter(payload, "")
                        .appendQueryParameter( itemId, code)
                        .appendQueryParameter(results, "1")
                        .build();

            }


            URL url = new URL(builtUri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                //return null;
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
                //return null;
            }

            jsonString = buffer.toString();
            Log.v(LOG_TAG, "Forecast JSON String: " + jsonString);
            Gson gson = new Gson();
            product = gson.fromJson(jsonString, SingleProduct.class);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            //return null;
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


        Log.v("JOJO", product.getAck());
       //return product;
    }

    public void BuildNotification(final Context context, final String Title, final String msg){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("favourite", "favouriteMenu");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder( Objects.requireNonNull(context), CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_favourites)
                .setContentTitle(Title)
                .setContentText(msg)
                .setVibrate(new long[] {1000, 1000})
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setColor( Color.BLUE )
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setOnlyAlertOnce( true )
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, notification);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.v("BootReceiver", "Job cancelled");
        return true;
    }
}
