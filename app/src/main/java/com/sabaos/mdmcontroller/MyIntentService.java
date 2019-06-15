package com.sabaos.mdmcontroller;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyIntentService extends IntentService {


    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // This is necessary for service to run, otherwise Android will destroy it after 5 secs.
        NotificationManager notificationManager;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My_channel";
            String description = "Saba_channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("123", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "123")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Saba Market")
                    .setContentText("Initializing")
                    .setAutoCancel(false);
            startForeground(1, builder.build());
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "123")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Saba Market")
                    .setContentText("Initializing")
                    .setAutoCancel(false);
            notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());
            startForeground(1, builder.build());
        }


        SharedPref sharedPref = new SharedPref(getApplicationContext());
        Bundle bundle = intent.getExtras();
        if (bundle.getString("type").equalsIgnoreCase("appServerRegister")) {

            sharedPref.saveData("marketToken", bundle.getString("token"));
            Log.i("saved market token", sharedPref.loadData("marketToken"));
            Intent responseIntent = new Intent();
            responseIntent.putExtra("type", "appServerRegister");
            responseIntent.putExtra("result", "success");
            responseIntent.setComponent(new ComponentName("com.sabaos.testmarketapp", "com.sabaos.testmarketapp.SabaClientService"));
            if (Build.VERSION.SDK_INT >= 26) {

                getApplicationContext().startForegroundService(responseIntent);
            } else getApplicationContext().startService(responseIntent);
        }
    }
}
