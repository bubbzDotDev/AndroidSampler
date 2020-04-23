package macbeth.androidsampler.Notifications;



import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.google.gson.Gson;


public class AppNotificationService extends NotificationListenerService {

    String appBlockName = "";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.i("AppNotificationService","onNotificationPosted called");
        Notification mNotification=sbn.getNotification();
        if (mNotification!=null) {
            Bundle extras = mNotification.extras;
            Log.i("AppNotificationService", extras.toString());
            ApplicationInfo ai = (ApplicationInfo) extras.get("android.appInfo");
            //Gson gson = new Gson();
            //System.out.println(gson.toJson(ai));
            Log.i("AppNotificationService", ai.processName);

            // Look for notifications to clear/block
            if (ai.processName.equals(appBlockName)) {
                cancelNotification(sbn.getKey());
                Log.i("AppNotificationService", "Clearing Notification for requested app.");
            }
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i("AppNotificationService","onNotificationRemoved called");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Register for broadcast messages sent from the Notifications activity
        Log.i("AppNotificationService","Starting Service");
        AppNotificationServiceListener listener = new AppNotificationServiceListener();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("macbeth.androidsampler.Notifications.Broadcasts");
        registerReceiver(listener,intentFilter);
    }

    class AppNotificationServiceListener extends BroadcastReceiver {
        @Override
            public void onReceive(Context context, Intent intent) {
            // Look in the intent for the app name to block
            if (intent.hasExtra("block_app")) {
                appBlockName = intent.getStringExtra("block_app");
                Log.i("AppNotificationService","Received broadcast from activity to block.");
                System.out.println("Received broadcast from activity: " + appBlockName);
            }

        }
    }
}
