package com.example.myapplication.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class LeadAdapter extends PagerAdapter {
    /**
     * 数据源
     */
    protected int[] icons = {R.mipmap.welcome, R.mipmap.wy, R.mipmap.bd, R.mipmap.small};
    protected List<ImageView> viewIcons = new ArrayList<>();

    /**
     * 构造方法：将图片转为视图
     */
    public LeadAdapter(Context context) {
        for (int i = 0; i < 4; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(icons[i]);
            viewIcons.add(imageView);

        }
    }

    /**
     * 子条目的个数
     *
     * @return
     */
    @Override
    public int getCount() {
        return viewIcons.size();
    }

    /**
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化子条目
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewIcons.get(position));
        return viewIcons.get(position);
    }

    /**
     * 销毁子条目
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewIcons.get(position));
    }
}
