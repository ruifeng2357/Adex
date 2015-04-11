package com.jilcreation.adex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ruifeng on 2015/4/6.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentAlarm = new Intent(context, AlarmActivity.class);
        intentAlarm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentAlarm);
    }
}
