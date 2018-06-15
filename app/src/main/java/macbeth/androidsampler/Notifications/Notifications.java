package macbeth.androidsampler.Notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import macbeth.androidsampler.R;

public class Notifications extends AppCompatActivity {

    private Spinner spinner;
    private String channel_id = "macbeth.androidsampler.notifications.NOTIFICATION_CHANNEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        setTitle("Notifications");
        spinner = findViewById(R.id.spinner);
        List<String> appNames = new ArrayList<String>();
        appNames.add("");
        for (PackageInfo app : getPackageManager().getInstalledPackages(0)) {
            appNames.add(app.packageName);
        }
        ArrayAdapter spinnerData = new ArrayAdapter(this, android.R.layout.simple_spinner_item, appNames);
        spinner.setAdapter(spinnerData);

        NotificationChannel channel = new NotificationChannel(channel_id,
                "SampleNotificationChannel", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tv = findViewById(R.id.textView);
        String enabledNotificationListeners = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (enabledNotificationListeners != null && enabledNotificationListeners.contains(getPackageName())) {
            tv.setText("The user has granted this app permission for notification listeners.");
        }
         else {
            tv.setText("The user has not granted this app permission for notification listeners.");
        }

    }

    public void buttonPermissions(View view) {
        Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
        startActivity(intent);
    }

    public void buttonBlock(View view) {
        // Send broadcast message to the App Notification Service to start blocking an app
        Intent intent = new Intent("macbeth.androidsampler.Notifications.Broadcasts");
        intent.putExtra("block_app", spinner.getSelectedItem().toString());
        sendBroadcast(intent);
        Log.i("Notifications","Sending broadcast from activity to block." + spinner.getSelectedItem().toString());
    }

    public void buttonDoNotDisturb(View view) {
        // Do Not Disturb Enabled

        // INTERRUPTION_FILTER_NONE - All notifications are suppressed and all audio stream (except those
        //                            used for phone calls) and vibrations are muted.
        // INTERRUPTION_FILTER_ALARM - All notifications except those of category CATEGORY_ALARM are suppressed
        // INTERRUPTION_FILTER_PRIORITY - All notifications are suppressed except those that match the
        //                                priority criteria (see the policy)
        // INTERRUPTION_FILTER_ALL - No notifications are suppressed

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALARMS);
        Log.i("Notifications",notificationManager.getNotificationPolicy().toString());

    }

    public void buttonNotificationsOkay(View view) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
        Log.i("Notifications",notificationManager.getNotificationPolicy().toString());
    }

    public void buttonSendNotification(View view) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Notification Test")
                .setContentText("You have been notified!")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(0, mBuilder.build());

    }
}
