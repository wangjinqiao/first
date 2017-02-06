package com.example.myapplication.presenter;

import android.content.Context;

import com.example.myapplication.model.VisitHttpTask;

/**
 * Created by Administrator on 2016/12/21 0021.
 * 联系业务逻辑与视图
 */

public class ToVisitHttp {
    public ToVisitHttp() {

    }

    public void toHttpNet(Context context) {
        //访问网络
        VisitHttpTask visitHttpTask= new VisitHttpTask();
        visitHttpTask.visitHttpVollery(context);
    }


}
