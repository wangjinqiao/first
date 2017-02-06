package com.example.myapplication.view.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/12/22 0022.
 * 访问网络得到新闻
 */

public class NewsNetModel {
    /**
     *
     * @param context
     * @param path:路径
     * @param mListener
     * @param mErrorListener
     */
    public void newsGetHttp(Context context,String path, Response.Listener<JSONObject> mListener, Response.ErrorListener mErrorListener) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
               path, null, mListener,
                mErrorListener);
        requestQueue.add(request);
    }
}
