package com.example.myapplication.view.entity;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

public class SendCommReponse {

    /**
     * data : {"explain":"发布成功！","result":0}
     * message : OK
     * status : 0
     */

    public DataBean data;
    public String message;
    public int status;

    public static class DataBean {
        /**
         * explain : 发布成功！
         * result : 0
         */

        public String explain;
        public int result;
    }
}
