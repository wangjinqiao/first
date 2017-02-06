package com.example.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class MyRccAdapter extends RecyclerView.Adapter<MyHolder> {
    int[] imgs;//数据源
    Context context;

    public MyRccAdapter(int[] imgs, Context context) {
        this.imgs = imgs;
        this.context = context;
    }
    View itemView;
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         itemView = LayoutInflater.from(context).inflate(R.layout.item_rcc, null);

        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.mimg= (ImageView) itemView.findViewById(R.id.img_item_rcc);
        holder.mimg.setImageResource(imgs[position]);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, position+"", Toast.LENGTH_SHORT).show();

            }
        });
        itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == imgs ? 0 : imgs.length;
    }
}

class MyHolder extends RecyclerView.ViewHolder {
    ImageView mimg;

    public MyHolder(View itemView) {
        super(itemView);
    }
}