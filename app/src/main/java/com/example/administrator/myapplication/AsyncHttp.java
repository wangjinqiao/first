package com.example.administrator.myapplication;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/12/20 0020.
 */

public class AsyncHttp {
    public  static void requestHttp(Context context, Request request){
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
