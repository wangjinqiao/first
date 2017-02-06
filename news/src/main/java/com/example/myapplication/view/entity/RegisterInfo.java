package com.example.myapplication.view.entity;

/**
 * Created by Administrator on 2016/12/28 0028.
 */

public class RegisterInfo {

    /**
     * message : OK
     * status : 0
     * data : {"result":0,"token":"e3ab28e092e00dda3cb0edbcaaef67a4","explain":"注册成功"}
     */

    private String message;
    private int status;
    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * result : 0
         * token : e3ab28e092e00dda3cb0edbcaaef67a4
         * explain : 注册成功
         */

        public int result;
        public String token;
        public String explain;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getExplain() {
            return explain;
        }

        public void setExplain(String explain) {
            this.explain = explain;
        }
    }
}
