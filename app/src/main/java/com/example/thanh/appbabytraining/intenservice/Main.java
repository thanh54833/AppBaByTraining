package com.example.thanh.appbabytraining.intenservice;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanh.appbabytraining.R;
import com.example.thanh.appbabytraining.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main extends AppCompatActivity {

    //Pending intent instance
    // private PendingIntent pendingIntent;

    private RadioButton secondsRadioButton, minutesRadioButton, hoursRadioButton;

    //Alarm Request Code
    private static final int ALARM_REQUEST_CODE = 133;

    private Intent intent;
    private AlarmNotificationService alarmNotificationService;

    private int id_1 = 3512;
    private int id_2 = 8591;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inten_service);

        //Find id of all radio buttons
        secondsRadioButton = (RadioButton) findViewById(R.id.seconds_radio_button);
        minutesRadioButton = (RadioButton) findViewById(R.id.minutes_radio_button);
        hoursRadioButton = (RadioButton) findViewById(R.id.hours_radio_button);


        final EditText editText = (EditText) findViewById(R.id.input_interval_time);

        /*findViewById(R.id.start_alarm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getInterval = editText.getText().toString().trim();//get interval from edittext
                if (!getInterval.equals("") && !getInterval.equals("0")) {
                    //finally trigger alarm manager
                    triggerAlarmManager(getTimeInterval(String.valueOf(10)));

                    triggerAlarmManager(getTimeInterval(String.valueOf(20)));
                }
            }
        });*/


        /*Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        alarmIntent.putExtra("id",id_1);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), ALARM_REQUEST_CODE, alarmIntent, 0);
        triggerAlarmManager(getTimeInterval(String.valueOf(5)), pendingIntent);

        /*Intent alarmIntent1 = new Intent(getApplicationContext(), AlarmReceiver.class);
        alarmIntent1.putExtra("id",id_2);

        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), ALARM_REQUEST_CODE+1, alarmIntent1, 0);
        triggerAlarmManager(getTimeInterval(String.valueOf(2)), pendingIntent1);*/

       /* intent=new Intent(getApplicationContext(),AlarmNotificationService.class);
        intent.putExtra("id_receive",id_1);
        intent.putExtra("time","on");
        startService(intent);


        Intent intent2=new Intent(getApplicationContext(),AlarmNotificationService.class);
        stopService(intent2);*/


        /*Intent alarmIntent1 = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), ALARM_REQUEST_CODE, alarmIntent1, 0);

        try {
            pendingIntent1.send();
            Toast.makeText(getApplicationContext(),"Success ...",Toast.LENGTH_SHORT).show();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error ...",Toast.LENGTH_SHORT).show();
        }

        Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), ALARM_REQUEST_CODE, alarmIntent, 0);

        try {
            pendingIntent.send();
            Toast.makeText(getApplicationContext(),"Success ...",Toast.LENGTH_SHORT).show();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error ...",Toast.LENGTH_SHORT).show();
        }*/

        //set on click over stop alarm button

        /*findViewById(R.id.stop_alarm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Stop alarm manager
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                manager.cancel(pendingIntent);//cancel the alarm manager of the pending intent

                //Stop the Media Player Service to stop sound
                stopService(new Intent(getApplicationContext(), AlarmSoundService.class));
                //remove the notification from notification tray
                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);
                Toast.makeText(getApplicationContext(), "Alarm Canceled/Stop by User.", Toast.LENGTH_SHORT).show();
            }
        });*/



        /*Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
        alarmIntent.putExtra("id",id_1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), ALARM_REQUEST_CODE, alarmIntent, 0);
        triggerAlarmManager(getTimeInterval(String.valueOf(0)), pendingIntent);*/

        Utils.messageDisplay("start panding intent ...");
        onStartServiceAlarm();

    }

    private void onStartServiceAlarm() {

        intent = new Intent(getApplicationContext(), AlarmNotificationService.class);
        intent.putExtra("thanh", "thanh");
        intent.putExtra("id_receive", id_1);
        intent.putExtra("time", "on");
        startService(intent);

        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

    }


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            AlarmNotificationService.BoundService BoundService = (AlarmNotificationService.BoundService) iBinder;
            alarmNotificationService = BoundService.getService();
           // alarmNotificationService.sendNotification("the gioi cua thanh ...");
            //Utils.messageDisplay("start service by activity ... : \n tine : " + alarmNotificationService.show() + "\n");

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            Utils.messageDisplay("stop service by activity ...");
        }
    };


    //get time interval to trigger alarm manager
   /* private int getTimeInterval(String getInterval) {
        int interval = Integer.parseInt(getInterval);//convert string interval into integer
        //Return interval on basis of radio button selection
        if (secondsRadioButton.isChecked())
            return interval;
        if (minutesRadioButton.isChecked())
            return interval * 60;//convert minute into seconds
        if (hoursRadioButton.isChecked()) return interval * 60 * 60;//convert hours into seconds
        //else return 0
        return 0;
    }*/

    //Trigger alarm manager with entered time interval
   /* public void triggerAlarmManager(int alarmTriggerTime, PendingIntent pendingIntent) {
        // get a Calendar object with current time
        Calendar cal = Calendar.getInstance();

        // add alarmTriggerTime seconds to the calendar object
        cal.add(Calendar.SECOND, alarmTriggerTime);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);//get instance of alarm manager

        manager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);//set alarm manager with entered timer by converting into milliseconds

        // Utils.messageDisplay("time : "+);
        Toast.makeText(this, "Alarm Set for " + alarmTriggerTime + " seconds.", Toast.LENGTH_SHORT).show();

    }*/


    //Stop/Cancel alarm manager
    /*public void stopAlarmManager() {

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        manager.cancel(pendingIntent);//cancel the alarm manager of the pending intent

        //Stop the Media Player Service to stop sound
        stopService(new Intent(getApplicationContext(), AlarmSoundService.class));

        //remove the notification from notification tray
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AlarmNotificationService.NOTIFICATION_ID);

        Toast.makeText(this, "Alarm Canceled/Stop by User.", Toast.LENGTH_SHORT).show();
    }*/

}