package com.example.myapplication.view.util;

import android.util.Log;

import com.example.myapplication.BuildConfig;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class LogWrapper {
    static boolean isDebug=true;
    public static void e(String tag, String msg) {
        if (isDebug && BuildConfig.DEBUG)
            Log.e(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (isDebug && BuildConfig.DEBUG)
            Log.w(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (isDebug && BuildConfig.DEBUG)
            Log.i(tag, msg);
    }
}
