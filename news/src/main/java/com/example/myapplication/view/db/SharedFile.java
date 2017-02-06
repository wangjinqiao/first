package com.example.myapplication.view.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.view.util.Constent;

/**
 * Created by Administrator on 2016/12/29 0029.
 * 共享文件
 */

public class SharedFile {
    public static SharedPreferences sharedPreferences;

    private SharedFile() {

    }

    //在主界面引导界面初始化共享文件
    public static void initSharedPre(Context context) {
        sharedPreferences = context.getSharedPreferences(Constent.NAME_SHSRED_PREFERENCE, 0);
    }
}
