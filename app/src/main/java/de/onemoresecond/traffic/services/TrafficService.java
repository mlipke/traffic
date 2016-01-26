package de.onemoresecond.traffic.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import de.onemoresecond.traffic.R;
import de.onemoresecond.traffic.util.TrafficCallback;
import de.onemoresecond.traffic.util.TrafficData;
import de.onemoresecond.traffic.util.TrafficNotificationReceiver;
import de.onemoresecond.traffic.util.TrafficRequest;

public class TrafficService extends Service {

    SharedPreferences preferences;

    public TrafficService() {
    }

    @Override
    public void onCreate() {
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer timer = new Timer();
        TimerTask trafficCheck = new TimerTask() {
            @Override
            public void run() {
                if (!preferences.getBoolean("NOTIFICATION_SHOWN", false)) {
                    TrafficRequest.requestTraffic(getApplicationContext(), new TrafficCallback() {
                        @Override
                        public void success(TrafficData data) {
                            if (data.getTotalTraffic() / 30.72 > 0.1) {
                                Log.d("Service", "90% Traffic");

                                show_notification();
                            }
                        }

                        @Override
                        public void failure(String message) {
                            Log.d("Service", "Failure: " + message);
                        }
                    });
                }
            }
        };

        timer.scheduleAtFixedRate(trafficCheck, 1000, 1000 * 60 * 1);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void show_notification() {
        Intent intent = new Intent(getApplicationContext(), TrafficNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setContentTitle("90% Waring")
                        .setContentText("You have used 90% of your traffic for today")
                        .setSmallIcon(R.drawable.ic_warning_24dp)
                        .setAutoCancel(true)
                        .setDeleteIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE |
                                Notification.DEFAULT_SOUND);

        NotificationManager manager = (NotificationManager)getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(555, builder.build());
    }

}
