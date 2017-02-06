package com.example.myapplication.view.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.view.util.Constent;

/**
 * Created by Administrator on 2016/12/30 0030.
 * 创建数据库
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    public DbOpenHelper(Context context) {
        //创建数据库
        /**
         * context:上下文对象
         * Constent.DB_FILENAME:数据库名
         * null:游标工厂
         * Constent.DB_VERSION:版本号
         */
        super(context, Constent.DB_FILENAME, null, Constent.DB_VERSION);
    }


    /**
     * 创建表
     * String TABLE_COLUMN_NID = "nid";
     * String TABLE_COLUMN_ICON = "icon";
     * String TABLE_COLUMN_SUMMARY = "summary";
     * String TABLE_COLUMN_TITLE = "title";
     * String TABLE_COLUMN_TIME= "time";
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + Constent.TABLE_NAME + "("
                + Constent.TABLE_COLUMN_NID
                + " integer," +
                Constent.TABLE_COLUMN_ICON+" text,"
                + Constent.TABLE_COLUMN_SUMMARY+" text,"
                + Constent.TABLE_COLUMN_TITLE+" text,"
                + Constent.TABLE_COLUMN_LINK+" text,"
                + Constent.TABLE_COLUMN_TIME+" text"+")");
    }

    //更新版本
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
