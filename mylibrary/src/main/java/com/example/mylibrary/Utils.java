package com.example.mylibrary;

import android.util.Log;

public class Utils {

    private static final String TAG="com.example.mylibrary";
    public void messageDisplay(String message){
        if(BuildConfig.DEBUG){
            Log.d(TAG,message);
        }
    }
}
