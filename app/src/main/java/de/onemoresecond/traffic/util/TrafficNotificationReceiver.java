package de.onemoresecond.traffic.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by matt on 26/01/16.
 */
public class TrafficNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        Date today = Calendar.getInstance().getTime();

        preferences.edit()
                .putBoolean("NOTIFICATION_SHOWN", true)
                .putString("LAST_NOTIFICATION_DATE", fmt.format(today))
                .apply();
    }
}
