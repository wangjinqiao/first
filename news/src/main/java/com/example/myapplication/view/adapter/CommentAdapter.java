package com.example.myapplication.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.view.entity.CommentListInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/29 0029.
 * 评论列表适配器
 */

public class CommentAdapter extends MyBaseAdapter<CommentListInfo.DataBean> {

    public CommentAdapter(Context context, List<CommentListInfo.DataBean> date) {
        super(context, date);
    }

    @Override
    public View getItemView(int position, View convertView) {
        ViewHolder mholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_xlst_comment, null);
            mholder = new ViewHolder(convertView);
            convertView.setTag(mholder);
        } else {
            mholder = (ViewHolder) convertView.getTag();
        }
        CommentListInfo.DataBean commentListInfo = getItem(position);
         // 设置图像
        Picasso.with(context).load(commentListInfo.portrait).into(mholder.imgUserPhotoComment);
        mholder.txtUserNameComment.setText(commentListInfo.uid);
        mholder.txtTimeComment.setText(commentListInfo.stamp);
        mholder.txtCommentContent.setText(commentListInfo.content);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.img_user_photo_comment)
        ImageView imgUserPhotoComment;
        @Bind(R.id.txt_user_name_comment)
        TextView txtUserNameComment;
        @Bind(R.id.txt_time_comment)
        TextView txtTimeComment;
        @Bind(R.id.txt_comment_content)
        TextView txtCommentContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    /**
     * 加载 刷新
     *
     * @param newData
     */
    public void addNewData(List<CommentListInfo.DataBean> newData) {
        date=newData;
        notifyDataSetChanged();
    }
    /**
     * 加载更多
     *
     * @param moreData
     */
    public void appendData(List<CommentListInfo.DataBean> moreData) {
        date.addAll(moreData);
        notifyDataSetChanged();
    }
}
