package de.onemoresecond.traffic.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import de.onemoresecond.traffic.services.TrafficService;


/**
 * Created by matt on 26/01/16.
 */
public class TrafficNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, TrafficService.class);
        context.stopService(serviceIntent);
    }
}
