package a2.csci412.wwu.edu.memorygain;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Jonah on 11/14/2017.
 */

public class ImageAlarmNotificationReceiver extends BroadcastReceiver {
    SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        long[] pattern;
        if (sharedPreferences.getBoolean("vibration", true)) {
            pattern = new long[]{1000, 1000, 1000};
        } else {
            pattern = new long[]{};
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Image Alarm")
                .setContentText("It's time to recall your image.")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("Go to 'Image Recall'")
                .setVibrate(pattern);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (sharedPreferences.getBoolean("notification", true)) {
            notificationManager.notify(0, builder.build());
        }
        MainActivity.setImageGuessReady(true);
    }
}