package com.example.thanh.appbabytraining.intenservice;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.thanh.appbabytraining.R;
import com.example.thanh.appbabytraining.Utils;

import java.util.Calendar;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class ExampleService extends Service {

    private  boolean RESULT = true;


    private int TIME = 1;
    private boolean CANCEL_TIME = true;
    private IBinder iBinder = new BoundService();

    private Alarm alarm;


    public class BoundService extends Binder {
        public ExampleService getService() {

            return ExampleService.this;

        }
    }
    public String show() {
        Toast.makeText(getApplicationContext(), "show", Toast.LENGTH_SHORT).show();
        return "thanh ne !";
    }
    public void cancelAlarm() {
        CANCEL_TIME = false;
        RESULT=false;
        Utils.messageDisplay(" CANCEL_TIME : " + CANCEL_TIME);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarm = new Alarm();

        return super.onStartCommand(intent, flags, startId);
    }
    private String SEND;
    @Override
    public void onStart(Intent intent, int startId) {




        Utils.messageDisplay("on start service ...");
        super.onStart(intent, startId);
    }
    @Override
    public void onCreate() {


        alarm = new Alarm();
        mExec = Executors.newFixedThreadPool(5);
        mEcs = new ExecutorCompletionService<Runnable>(mExec);

        mEcs.submit(new AlrmTask("upload time 1 :", 1), null);

        mEcs.submit(new AlrmTask("upload time 2 :",10),null);



        //mEcs.submit(new AlrmTask("upload time 3 :",7123),null);
        //mEcs.submit(new AlrmTask("upload time 4 :",8123),null);
        //mEcs.submit(new AlrmTask("upload time 5 :",8123),null);

        Utils.messageDisplay("on create service ...");
        super.onCreate();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private ExecutorService mExec;
    private CompletionService<Runnable> mEcs;

    //add by notifycation ...
    private NotificationManager alarmNotificationManager;
    private NotificationCompat.Builder alamNotificationBuilder;


    //create task ...
    class AlrmTask implements Runnable {
        private String message;
        private Integer id;

        public AlrmTask(String message, Integer id) {
            this.message = message;
            this.id = id;
        }

        @Override
        public void run() {
            long time = 0;

            //add time alarm ...
            alarm.setAlarm(getApplicationContext(), System.currentTimeMillis(), id);

            while (RESULT) {
                try {
                    sleep(1000);
                    time += 1000;
                    if (time == 10000) {
                        time = 0;
                    }
                    Utils.messageDisplay("Upload time ...: " + message + " - time :" + getTime(System.currentTimeMillis()));
                    sendNotification(message + getTime(System.currentTimeMillis()) + " CANCEL_TIME :" + CANCEL_TIME, id);
                } catch (Exception e) {
                    Utils.messageDisplay("Error : " + e.getMessage());
                }
            }
            alarm.cancelAlarm(getApplicationContext());

            Utils.messageDisplay("cancle alram service ...");
            sendNotification("Alram --> Alram", id);
        }
    }

    public String getTime(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        int hours = c.get(Calendar.HOUR);
        int minutes = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        return "hours : " + hours + " minutes :" + minutes + " second :" + second;
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

}
