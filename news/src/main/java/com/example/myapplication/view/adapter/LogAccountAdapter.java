package com.example.myapplication.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.view.entity.UserCenterInfo;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class LogAccountAdapter extends MyBaseAdapter<UserCenterInfo.DataBean.LoginlogBean> {
    //日志列表适配器
    public LogAccountAdapter(Context context, List<UserCenterInfo.DataBean.LoginlogBean> date) {
        super(context, date);
    }

    @Override
    public View getItemView(int position, View convertView) {

        ViewHolder mholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lst_account_log, null);
            mholder = new ViewHolder(convertView);
            convertView.setTag(mholder);
        } else {
            mholder = (ViewHolder) convertView.getTag();
        }
        UserCenterInfo.DataBean.LoginlogBean itemlog = getItem(position);
        mholder.txtLstAccountLogTime.setText(itemlog.time);
        mholder.txtLstAccountLogCity.setText(itemlog.address);
        mholder.txtLstAccountLogContent.setText(itemlog.device+"");
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.txt_lst_account_log_time)
        TextView txtLstAccountLogTime;
        @Bind(R.id.txt_lst_account_log_city)
        TextView txtLstAccountLogCity;
        @Bind(R.id.txt_lst_account_log_content)
        TextView txtLstAccountLogContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
