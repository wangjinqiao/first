package com.example.myapplication.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;

import com.example.myapplication.view.entity.NewsDetil;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class NewsAdapter extends MyBaseAdapter<NewsDetil> {

    List<NewsDetil> date;

    public NewsAdapter(Context context, List<NewsDetil> date) {
        super(context, date);
        this.date = date;

    }

    @Override
    public View getItemView(int position, View convertView) {
        ViewHolder mholder;
        if (convertView == null) {
            mholder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lst_news_main, null);
            mholder.mimgIcon = (ImageView) convertView.findViewById(R.id.img_lst_news_icon);
            mholder.mtxtTitle = (TextView) convertView.findViewById(R.id.txt_lst_news_title);
            mholder.mtxtSummary = (TextView) convertView.findViewById(R.id.txt_lst_news_summary);
            mholder.mtxtStamp = (TextView) convertView.findViewById(R.id.txt_lst_news_stamp);
            convertView.setTag(mholder);
        } else {
            mholder = (ViewHolder) convertView.getTag();
        }
        NewsDetil item = getItem(position);
        /**
         * 设置图片
         */
        Picasso picasso = Picasso.with(context);
        RequestCreator requestCreator = picasso.load(item.icon);
        picasso.setIndicatorsEnabled(true);
        requestCreator.placeholder(R.mipmap.defaultpic);
        requestCreator.error(R.mipmap.sorry);
        requestCreator.into(mholder.mimgIcon);

        mholder.mtxtTitle.setText(item.title);
        mholder.mtxtSummary.setText(item.summary);
        mholder.mtxtStamp.setText(item.stamp);
        return convertView;
    }

    class ViewHolder {
        ImageView mimgIcon;
        TextView mtxtTitle;
        TextView mtxtSummary;
        TextView mtxtStamp;

    }

    /**
     * 加载更多
     *
     * @param moreData
     */
    public void appendData(List<NewsDetil> moreData) {
        date.addAll(moreData);
        notifyDataSetChanged();
    }

    /**
     * 加载 刷新
     *
     * @param newData
     */
    public void addNewData(List<NewsDetil> newData) {
        this.date = newData;
        notifyDataSetChanged();
    }
}
