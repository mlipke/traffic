package de.onemoresecond.traffic.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import de.onemoresecond.traffic.services.TrafficService;

/**
 * Created by matt on 29/01/16.
 */
public class TrafficServiceControl {

    public static void start(Context context) {
        Intent serviceIntent = new Intent(context, TrafficService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, serviceIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, 2);

        AlarmManager manager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public static void stop(Context context) {
        Intent serviceIntent = new Intent(context, TrafficService.class);
        context.stopService(serviceIntent);
    }
}
