package com.example.salestracker;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.util.Log;

public class FetchProductsService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("BootReceiver", "Job started");
        //doBackgroundWork(params, this);
        FetchProductsTask task = new FetchProductsTask("aKeyword", null);
        task.execute();

        return false;
    }

    /*private void doBackgroundWork(final JobParameters params, final Context c) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                *//*NotificationCompat.Builder builder = new NotificationCompat.Builder(c)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("My notification")
                            .setContentText("Hello World!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            // Set the intent that will fire when the user taps the notification
                            .setAutoCancel(true);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);
                notificationManager.notify(1, builder.build());*//*

                Log.d("BootReceiver", "Thread running");
                jobFinished(params, true);
            }
        }).start();
    }*/

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.v("BootReceiver", "Job cancelled");
        return true;
    }
}
