package com.example.mylibrary;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Util {

    public static void messageDisplay(String message) {
        if (message != null) {
            if (BuildConfig.DEBUG) {
                Log.i("com.example.mylibrary", message);
            }
        }
    }

    public static void toast(Context context, String message) {
        if (context != null || message != null) {
            if (BuildConfig.DEBUG) {
                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}