package com.example.myapplication.view;

import android.app.Application;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2017/1/4 0004.
 * 应用入口
 * 初始化sdk
 */

public class Myapplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化分享的sdk ,"1a7de218f7c8a"
        ShareSDK.initSDK(this.getApplicationContext());


    }
}
