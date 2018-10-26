package com.example.thanh.appbabytraining.autostart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.thanh.appbabytraining.R;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_start);

       /* Alarm alarm = new Alarm();
        alarm.setAlarm(getApplicationContext());*/
        Intent intent=new Intent(this,YourService.class);


        startService(intent);

    }
}
