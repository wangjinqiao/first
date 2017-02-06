package com.example.myapplication.view.presenter;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONObject;

import com.example.myapplication.view.model.NewsNetModel;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class NewsGetPresenter {
    /**
     *
     * @param context
     * @param path
     * @param mListener
     * @param mErrorListener
     */
    public void visitHttpForNews(Context context,String path,Response.Listener<JSONObject> mListener,Response.ErrorListener mErrorListener) {
        Log.e("NewsGetPresenter","visitHttpForNews");
        NewsNetModel newsNetModel = new NewsNetModel();
        newsNetModel.newsGetHttp(context,path,mListener,mErrorListener);
    }
}
