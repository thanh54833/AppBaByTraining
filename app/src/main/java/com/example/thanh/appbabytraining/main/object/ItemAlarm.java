package com.example.thanh.appbabytraining.main.object;

import android.content.Intent;

public class ItemAlarm {

    private int id;
    private Intent intent;
    private int time;
    private String music;
    private String image;
    private boolean flag;
    private String icon;
    private String decription;
    private String timeRemain;


    public ItemAlarm(int id, Intent intent, int time, String music, String image, boolean flag, String icon, String decription, String timeRemain) {
        this.id = id;
        this.intent = intent;
        this.time = time;
        this.music = music;
        this.image = image;
        this.flag = flag;
        this.icon = icon;
        this.decription = decription;
        this.timeRemain = timeRemain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getTimeRemain() {
        return timeRemain;
    }

    public void setTimeRemain(String timeRemain) {
        this.timeRemain = timeRemain;
    }
}
