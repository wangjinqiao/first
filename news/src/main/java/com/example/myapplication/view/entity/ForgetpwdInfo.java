package com.example.myapplication.view.entity;

/**
 * Created by Administrator on 2016/12/28 0028.
 */

public class ForgetpwdInfo {

    /**
     * message : ERROR
     * status : 0
     * data : {"result":-2,"explain":"该邮箱不存在！"}
     */

    public String message;
    public int status;
    public DataBean data;

    public static class DataBean {
        /**
         * result : -2
         * explain : 该邮箱不存在！
         */

        public int result;
        public String explain;
    }
}
