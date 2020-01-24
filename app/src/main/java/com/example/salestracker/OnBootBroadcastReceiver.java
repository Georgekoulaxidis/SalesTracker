package com.example.salestracker;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class OnBootBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG_CON_BROADCAST_RECEIVER = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "BroadcastReceiver, action is " + intent.getAction(), Toast.LENGTH_LONG).show();
        Log.i(TAG_CON_BROADCAST_RECEIVER, intent.getAction());
        if("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            debugIntent(intent,"dennis");
            Toast.makeText(context, "Broadcast receiver triggered", Toast.LENGTH_LONG).show();

            Util util = new Util();
            util.scheduleJob(context);
        }

    }

    private void debugIntent(Intent intent, String tag) {
        Log.i(tag, "action: " + intent.getAction());
        Log.i(tag, "component: " + intent.getComponent());
        Bundle extras = intent.getExtras();
        if (extras != null) {
            for (String key: extras.keySet()) {
                Log.i(tag, "key [" + key + "]: " +
                        extras.get(key));
            }
        }
        else {
            Log.i(tag, "no extras");
        }
    }

    private class Util {

        private void scheduleJob(Context context) {
            ComponentName serviceComponent = new ComponentName(context, FetchProductsService.class);
            JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
            builder.setPersisted(true);
            builder.setPeriodic(15*60*1000);
            /*builder.setMinimumLatency(3000);
            builder.setOverrideDeadline(5000);*/
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY); // Requires connectivity to network
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                builder.setMinimumLatency(5000);
            } else {
                builder.setPeriodic(5000);
            }*/
            JobScheduler jobScheduler = (JobScheduler)context.getSystemService(context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(builder.build());
            //Δεν μπορώ να το κάνω να δουλεύει περιοδικά
        }

    }
}
