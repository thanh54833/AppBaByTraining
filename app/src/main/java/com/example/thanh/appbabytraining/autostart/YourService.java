package com.example.thanh.appbabytraining.autostart;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.example.thanh.appbabytraining.Utils;

public class YourService extends Service
{
    Alarm alarm = new Alarm();
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        alarm.setAlarm(this);

        Utils.messageDisplay("onStartCommand service ...");
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {

        Utils.messageDisplay("onStart service ...");
        alarm.setAlarm(this);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
