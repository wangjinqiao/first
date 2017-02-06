package com.example.myapplication.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    /**
     * 界面对象，数据源
     */
    protected Context context;
    protected List<T> date;

    public MyBaseAdapter(Context context, List<T> date) {
        this.context = context;
        this.date = date;
    }

    @Override
    public int getCount() {
        return null == date ? 0 : date.size();
    }

    @Override
    public T getItem(int position) {
        return date.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getItemView(position, convertView);
    }

    /**
     * 获取子条目视图
     * @param position
     * @param convertView
     * @return
     */
    public abstract View getItemView(int position, View convertView);

    /***
     * 更新适配器
     * @param newDate
     */
    public void update( List<T> newDate){
        this.date=newDate;
        this.notifyDataSetChanged();
    }
}
