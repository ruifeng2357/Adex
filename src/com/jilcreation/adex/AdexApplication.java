package com.jilcreation.adex;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import com.jilcreation.model.STNotification;
import com.jilcreation.server.ServerManager;
import com.jilcreation.server.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ruifeng on 3/12/2015.
 */
public class AdexApplication extends Application {
    ArrayList<STNotification> g_arrNotification = new ArrayList<STNotification>();

    private AlarmManager g_alarmManager = null;

    private AsyncHttpResponseHandler handler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(String content) {
            super.onSuccess(content);

            try {
                JSONArray jsonArray = new JSONArray(content);
                for (int i = 0; i < jsonArray.length(); i++) {
                    STNotification newNotification = STNotification.decodeFromJSON(jsonArray.getJSONObject(i));

                    g_arrNotification.add(newNotification);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void onFailure(Throwable error, String content) {}
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        g_alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent;

        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, 3);
        cal.set(Calendar.DAY_OF_MONTH, 10);
        cal.set(Calendar.HOUR_OF_DAY, 19);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        g_alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        cal.set(Calendar.DAY_OF_MONTH, 11);
        cal.set(Calendar.HOUR_OF_DAY, 18);
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);
        g_alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        cal.set(Calendar.DAY_OF_MONTH, 12);
        cal.set(Calendar.HOUR_OF_DAY, 17);
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);
        g_alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        ServerManager.getNotification(handler);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public ArrayList<STNotification> getNotificationInfos() {
        return g_arrNotification;
    }
}
