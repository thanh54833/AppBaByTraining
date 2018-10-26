package com.example.thanh.appbabytraining.alarmsevice;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.thanh.appbabytraining.R;
import com.example.thanh.appbabytraining.Utils;

public class Main extends AppCompatActivity {


    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Intent myIntent = new Intent(getApplicationContext() , MyService.class);
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        pendingIntent = PendingIntent.getService(getApplicationContext(), 0, myIntent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000, pendingIntent);  //set repeating every 60 seconds

        Utils.messageDisplay("start project ...");
    }
}
