package com.example.myapplication.view.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.view.entity.NewsDetil;
import com.example.myapplication.view.util.Constent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/29 0029.
 * <p>
 * 对数据库进行 增，删，改，查
 *
 * @author Administrator
 */


public class Sqldatebase {

    public DbOpenHelper dbhelper;
    public SQLiteDatabase db;//数据库

    public static Sqldatebase sqlDb;

    private Sqldatebase(Context context) {
        //创建数据库
        dbhelper = new DbOpenHelper(context);

    }

    //单例模式
    public static Sqldatebase getSqldatebase(Context context) {
        if (sqlDb == null) {
            synchronized (Sqldatebase.class) {
                if (sqlDb == null) {
                    sqlDb = new Sqldatebase(context);
                }
            }
        }
        return sqlDb;
    }

    /**
     * 增添新闻的信息
     */
    public void addNews(NewsDetil newsDetil) {
        db = dbhelper.getWritableDatabase();
        //ContentValues:This class is used to store a set of values that the ContentResolver can process.
        ContentValues values = new ContentValues();
        values.put(Constent.TABLE_COLUMN_NID, newsDetil.nid);
        values.put(Constent.TABLE_COLUMN_ICON, newsDetil.icon);
        values.put(Constent.TABLE_COLUMN_SUMMARY, newsDetil.summary);
        values.put(Constent.TABLE_COLUMN_TIME, newsDetil.stamp);
        values.put(Constent.TABLE_COLUMN_TITLE, newsDetil.title);
        values.put(Constent.TABLE_COLUMN_LINK, newsDetil.link);
        db.insert(Constent.TABLE_NAME, null, values);
        db.close();

    }

    /**
     * 删除
     */
    public void deleteNews(int nid) {
        db = dbhelper.getWritableDatabase();
        db.delete(Constent.TABLE_NAME, Constent.TABLE_COLUMN_NID + "=?",
                new String[]{nid + ""});
        db.close();

    }


    /**
     * 查询所有数据
     *
     * @return
     */
    public List<NewsDetil> searchNid() {
        List<NewsDetil> newses = new ArrayList<>();
        /**
         * 表名
         * 需要查询的列的名称 null:所有的列
         * 查询的条件 _id=？
         * 条件的参数
         * 分组
         * 分组条件
         * 排序 asc :升序 desc:降序eg："age asc"
         */
        db = dbhelper.getWritableDatabase();
        Cursor cursor = db.query(Constent.TABLE_NAME, null, null, null, null,
                null, null);
        while (cursor.moveToNext()) {
            int nid = cursor.getInt(cursor.getColumnIndex(Constent.TABLE_COLUMN_NID));
            String icon = cursor.getString(cursor.getColumnIndex(Constent.TABLE_COLUMN_ICON));
            String title = cursor.getString(cursor.getColumnIndex(Constent.TABLE_COLUMN_TITLE));
            String summary = cursor.getString(cursor.getColumnIndex(Constent.TABLE_COLUMN_SUMMARY));
            String stamp = cursor.getString(cursor.getColumnIndex(Constent.TABLE_COLUMN_TIME));
            String link = cursor.getString(cursor.getColumnIndex(Constent.TABLE_COLUMN_LINK));
            NewsDetil newsDetil = new NewsDetil(summary, icon, stamp, title, nid, link, 0);
            newses.add(newsDetil);
        }
        cursor.close();

        return newses;

    }

    /**
     * 查询单个数据
     *
     * @return：是否已收藏
     */
    public boolean searchNid(int nid) {
        db = dbhelper.getWritableDatabase();
        Cursor cursor = db.query(Constent.TABLE_NAME, null, null, null, null,
                null, null);
        while (cursor.moveToNext()) {
            int newsId = cursor.getInt(cursor
                    .getColumnIndex(Constent.TABLE_COLUMN_NID));
            if (newsId == nid) {
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;

    }

}


