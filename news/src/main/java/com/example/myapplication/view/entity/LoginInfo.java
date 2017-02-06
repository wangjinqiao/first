package com.example.myapplication.view.entity;

/**
 * Created by Administrator on 2016/12/28 0028.
 */

public class LoginInfo {

    /**
     * message : OK
     * status : 0
     * data : {"result":0,"token":"b1308402bcf01eda92d25f4581b3eeb9","explain":"登录成功"}
     */

    public String message;
    public int status;
    public DataBean data;

    public LoginInfo(String message, int status, DataBean data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public static class DataBean {
        /**
         * result : 0
         * token : b1308402bcf01eda92d25f4581b3eeb9
         * explain : 登录成功
         */

        public int result;
        public String token;
        public String explain;

        public DataBean(int result, String token, String explain) {
            this.result = result;
            this.token = token;
            this.explain = explain;
        }
    }
}
