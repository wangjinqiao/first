package com.example.myapplication;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class MapApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        SDKInitializer.initialize(getApplicationContext());
    }


}
