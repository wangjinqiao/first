package com.example.myapplication.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class PhotoAdapter extends BaseAdapter {
    String[] items = {"调用相机拍照", "从图库中选择"};
    int[] icons = {R.mipmap.photo_take, R.mipmap.photo_sel};
    Context mContext;

    public PhotoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_myaccount_photo, null);
            mholder = new ViewHolder(convertView);
            convertView.setTag(mholder);
        } else {
            mholder = (ViewHolder) convertView.getTag();
        }
        mholder.imgAccountSettingPhoto.setImageResource(icons[position]);
        mholder.txtAccountSettingPhoto.setText(items[position]);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.img_account_setting_photo)
        ImageView imgAccountSettingPhoto;
        @Bind(R.id.txt_account_setting_photo)
        TextView txtAccountSettingPhoto;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
