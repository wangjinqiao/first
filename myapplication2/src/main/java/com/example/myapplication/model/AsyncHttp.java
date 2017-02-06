package com.example.myapplication.model;

import android.os.AsyncTask;
import android.util.Log;

import com.example.myapplication.util.Constents;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class AsyncHttp {
    OnResponseListner onResponseListner;
    String method;
    String data;

    /**
     * @param method
     * @param path
     * @param json
     * @param onResponseListner
     */
    public void invitHttp(String method, String path, String json, OnResponseListner onResponseListner) {
        this.onResponseListner = onResponseListner;
        this.data = json;
        Log.e("TAG", "invitHttp: "+data);
        this.method = method;
        new HttpTask().execute(path);
    }

    public class HttpTask extends AsyncTask<String, Void, String> {
        /**
         * 访问网络
         *
         * @param params 路径
         * @return
         */
        @Override
        protected String doInBackground(String... params) {
            StringBuffer sb = new StringBuffer();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (method == Constents.METHOD_POST) {
                    conn.setRequestMethod(method);
                    OutputStream os = conn.getOutputStream();
                    os.write(data.getBytes());
                    os.close();
                }
                if (conn.getResponseCode() == 200) {
                    InputStream is = conn.getInputStream();
                    byte[] bs = new byte[1024];
                    int len = 0;

                    while ((len = is.read(bs)) != -1) {
                        sb.append(new String(bs, 0, len));
                    }

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() == 0) {
                onResponseListner.onResponseFail("error net");
            } else {
                onResponseListner.onResponse(s);
            }
        }
    }
}
