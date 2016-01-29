package de.onemoresecond.traffic.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by matt on 26/01/16.
 */
public class TrafficNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        TrafficServiceControl.stop(context);
    }
}
