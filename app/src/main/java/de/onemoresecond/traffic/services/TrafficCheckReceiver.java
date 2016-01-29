package de.onemoresecond.traffic.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import de.onemoresecond.traffic.R;
import de.onemoresecond.traffic.util.TrafficCallback;
import de.onemoresecond.traffic.util.TrafficData;
import de.onemoresecond.traffic.util.TrafficNotificationReceiver;
import de.onemoresecond.traffic.util.TrafficRequest;

/**
 * Created by matt on 29/01/16.
 */
public class TrafficCheckReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Receiver", "Received");

        final Context innerContext = context;

        TrafficRequest.requestTraffic(context, new TrafficCallback() {
            @Override
            public void success(TrafficData data) {
                if (data.getTotalTraffic() / 30.72 > 0.1) {
                    show_notification(innerContext);
                }
            }

            @Override
            public void failure(String message) {
                Log.d("Service", "Failure: " + message);
            }
        });
    }

    private void show_notification(Context context) {
        Intent intent = new Intent(context, TrafficNotificationReceiver.class);
        intent.setAction("de.onemoresecond.Deleted");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setContentTitle("90% Waring")
                        .setContentText("You have used 90% of your traffic for today")
                        .setSmallIcon(R.drawable.ic_warning_24dp)
                        .setAutoCancel(true)
                        .setDeleteIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_LIGHTS
                                | Notification.DEFAULT_VIBRATE
                                | Notification.DEFAULT_SOUND);

        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(555, builder.build());
    }
}
