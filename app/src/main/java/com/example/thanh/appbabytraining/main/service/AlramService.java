package com.example.thanh.appbabytraining.main.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.thanh.appbabytraining.R;
import com.example.thanh.appbabytraining.Utils;
import com.example.thanh.appbabytraining.main.view.Main;


import java.util.Calendar;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class AlramService extends Service {

    private  Intent intentAlarm;
    private ExecutorService mExec;
    private CompletionService<Runnable> mEcs;
    private AlarmRecive alarm;
    private  boolean RESULT = true;
    private boolean CANCEL_TIME = true;



    //add by notifycation ...
    private NotificationManager alarmNotificationManager;
    private NotificationCompat.Builder alamNotificationBuilder;

    // get put intent...
    private int TIME;
    private int ID;

    @Override
    public void onStart(Intent intent, int startId) {

        Utils.messageDisplay("onStart ...");
        super.onStart(intent, startId);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        TIME=intent.getIntExtra(getString(R.string.TIME),1000);
        ID=intent.getIntExtra(getString(R.string.ID),1000);

        String name=intent.getStringExtra("name");

        //sendNotifications(intent);

        mEcs.submit(new AlramService.AlrmTask(ID+"upload time :"+ID, ID,TIME), null);

        Utils.messageDisplay("onStartCommand ...: "+name+"Time :"+TIME+" ID :"+ID);

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {


        Utils.messageDisplay("onStartCommand ...");
        super.onDestroy();
    }
    @Override
    public void onCreate() {

        alarm = new AlarmRecive();
        mExec = Executors.newFixedThreadPool(5);
        mEcs = new ExecutorCompletionService<Runnable>(mExec);

        //mEcs.submit(new AlramService.AlrmTask("upload time 2 :",10),null);
        Utils.messageDisplay("onCreate ...");
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Utils.messageDisplay("onBind ...");
        return null;
    }


    class AlrmTask implements Runnable {
        private String message;
        private Integer id;
        private int time;

        public AlrmTask(String message, Integer id, int time) {
            this.message = message;
            this.id = id;
            this.time = time;
        }

        @Override
        public void run() {
            //add time alarm ...
            alarm.setAlarm(getApplicationContext(), System.currentTimeMillis(), time);

            while (RESULT) {
                try {
                    sleep(1000);
                    time += 1000;
                    if (time == 10000) {
                        time = 0;
                    }
                    //Utils.messageDisplay("Upload time ...: " + message + " - time :" + getTime(System.currentTimeMillis()));
                    //sendNotification(message + getTime(System.currentTimeMillis()) + " CANCEL_TIME :" + CANCEL_TIME, id);
                    sendNotification(message +" ID : "+id, id);
                } catch (Exception e) {
                    Utils.messageDisplay("Error : " + e.getMessage());
                }
            }
            alarm.cancelAlarm(getApplicationContext());

            Utils.messageDisplay("cancle alram service ...");

            sendNotification("Alram --> Alram", id);
        }
    }

    private void sendNotification(String msg, int id) {

        alarmNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        final PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, Main.class), PendingIntent.FLAG_UPDATE_CURRENT);

        alamNotificationBuilder = new NotificationCompat.Builder(getApplicationContext()).setContentTitle("Alarm").setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg)).setContentText(msg)
                .setAutoCancel(true);//

        alamNotificationBuilder.setContentIntent(contentIntent);

        alarmNotificationManager.notify(id, alamNotificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotifications(Intent intent){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Title")
                .setContentText("New Message received");
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle("doUdo");

        // Add your All messages here or use Loop to generate messages

        inboxStyle.addLine("Messgare 1");
        inboxStyle.addLine("Messgare 2");
        inboxStyle.addLine("Messgare n");


        mBuilder.setStyle(inboxStyle);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());

        stackBuilder.addNextIntent(intent);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pIntent);

        NotificationManager mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(0, mBuilder.build());

    }

    public String getTime(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        int hours = c.get(Calendar.HOUR);
        int minutes = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        return "hours : " + hours + " minutes :" + minutes + " second :" + second;
    }

}
