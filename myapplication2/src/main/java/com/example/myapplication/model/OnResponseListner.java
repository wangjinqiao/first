package com.example.myapplication.model;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public interface OnResponseListner {
    void onResponse(String response);

    void onResponseFail(String error);

}
