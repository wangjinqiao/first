package com.example.myapplication.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.util.Content;
import com.example.myapplication.view.MainActivity;

/**
 * Created by Administrator on 2016/12/21 0021.
 * 访问网络
 */

public class VisitHttpTask {
    public VisitHttpTask() {


    }

    public void visitHttpVollery(Context context) {
        //访问网络
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ImageRequest imageRequest = new ImageRequest(Content.PATH_IMG2,
                ((MainActivity) context).listener, 200, 300,
                Bitmap.Config.ARGB_8888, ((MainActivity) context).errorListener);
        requestQueue.add(imageRequest);
    }
}
