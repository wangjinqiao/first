package com.example.myapplication.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.myapplication.R;

/**
 * Created by Administrator on 2017/1/16 0016.
 * 自定义编辑框
 */

public class MySearchView extends EditText {
    //图标
    protected Drawable mLeftIcon = this.getResources().getDrawable(R.mipmap.search_bar_icon_normal);
    protected Drawable mRightIcon = this.getResources().getDrawable(R.mipmap.emotionstore_progresscancelbtn);

    public MySearchView(Context context) {
        this(context, null, 0);
    }

    public MySearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        //设置图标
        setCompoundDrawablesWithIntrinsicBounds(mLeftIcon, null, null, null);
        //文字改变监听事件
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setCompoundDrawablesWithIntrinsicBounds(mLeftIcon, null, s.toString().length() > 0 ? mRightIcon : null, null);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    //触摸事件


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN://设置按下事件  清空
                if (x > getWidth() - mRightIcon.getIntrinsicWidth() && x < getWidth()) {
                    setText("");
                }


                break;
            default:
                break;

        }


        return super.onTouchEvent(event);
    }
}
