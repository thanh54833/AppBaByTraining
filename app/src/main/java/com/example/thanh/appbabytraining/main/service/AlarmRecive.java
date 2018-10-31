package com.example.thanh.appbabytraining.main.service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;


import java.util.Calendar;

public class AlarmRecive extends BroadcastReceiver
{
    private PendingIntent pendingIntent;
    private  Intent intentAlarm;


    @Override
    public void onReceive(Context context, Intent intent)
    {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();
        wl.release();
        // Put here YOUR code.
        Toast.makeText(context, "Alram -> Alram", Toast.LENGTH_SHORT).show(); // For example
        Log.i("thanh.Alram","Alram open music ...");
        context.startService(new Intent(context,AlarmSoundService.class));

    }

    public void setAlarm(Context context,long current,int time)
    {
        intentAlarm = new Intent(context, AlarmRecive.class);
        pendingIntent = PendingIntent.getBroadcast(context, 113, intentAlarm, 0);

        Calendar cal = Calendar.getInstance();
        // add alarmTriggerTime seconds to the calendar object
        cal.add(Calendar.SECOND,time);

        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //am.setRepeating(AlarmManager.RTC_WAKEUP, current, time, pendingIntent);
        am.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pendingIntent);
        Log.i("thanh.Alram","Alram start ...");
    }
    public void cancelAlarm(Context context)
    {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        context.stopService(new Intent(context,AlarmSoundService.class));
        Log.i("thanh.Alram","Alram cancle ...");
    }
}