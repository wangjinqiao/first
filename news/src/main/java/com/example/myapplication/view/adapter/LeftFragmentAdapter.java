package com.example.myapplication.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.myapplication.view.entity.TypeLeftFragmentConstant;

/**
 * Created by Administrator on 2016/12/23 0023.
 */

public class LeftFragmentAdapter extends MyBaseAdapter<TypeLeftFragmentConstant> {
    public LeftFragmentAdapter(Context context, List<TypeLeftFragmentConstant> date) {
        super(context, date);
    }

    @Override
    public View getItemView(int position, View convertView) {
        ViewHolder mholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lst_left_fragment, null);
            mholder = new ViewHolder(convertView);
            convertView.setTag(mholder);

        } else {
            mholder = (ViewHolder) convertView.getTag();
        }
        TypeLeftFragmentConstant item = getItem(position);
        mholder.imgLstIconLeftFragment.setImageResource(item.icon);
        mholder.txtLstTypeLeftFragment.setText(item.Type);
        mholder.txtLstEnglishLeftFragment.setText(item.English);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.img_lst_icon_left_fragment)
        ImageView imgLstIconLeftFragment;
        @Bind(R.id.txt_lst_type_left_fragment)
        TextView txtLstTypeLeftFragment;
        @Bind(R.id.txt_lst_english_left_fragment)
        TextView txtLstEnglishLeftFragment;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
