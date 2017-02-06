package com.example.myapplication.view.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/28 0028.
 */

public class UserCenterInfo {

    /**
     * message : OK
     * status : 0
     * data : {"uid":"lyz","integration":0,"loginlog":[{"time":"2016-12-21 00:00:00.0","address":"广东","device":0},{"time":"2016-12-21 00:00:00.0","address":"广东","device":0},{"time":"2016-12-23 00:00:00.0","address":"北京","device":0},{"time":"2016-12-23 00:00:00.0","address":"上海","device":0},{"time":"2016-12-23 00:00:00.0","address":"上海","device":0},{"time":"2016-12-25 00:00:00.0","address":"北京","device":0},{"time":"2016-12-25 00:00:00.0","address":"北京","device":0},{"time":"2016-12-26 00:00:00.0","address":"北京","device":0},{"time":"2016-12-27 00:00:00.0","address":"广东","device":0},{"time":"2016-12-28 00:00:00.0","address":"广东","device":0},{"time":"2016-12-28 00:00:00.0","address":"北京","device":0},{"time":"2016-12-28 00:00:00.0","address":"上海","device":0},{"time":"2016-12-28 00:00:00.0","address":"广东","device":0},{"time":"2016-12-28 00:00:00.0","address":"上海","device":0},{"time":"2016-12-28 00:00:00.0","address":"上海","device":0},{"time":"2016-12-28 00:00:00.0","address":"广东","device":0}],"comnum":0,"portrait":"http://118.244.212.82:9092/Images/image.png"}
     */

    public String message;
    public int status;
    public DataBean data;

    public static class DataBean {
        /**
         * uid : lyz 用户名
         * integration : 0
         * loginlog : [{"time":"2016-12-21 00:00:00.0","address":"广东","device":0},{"time":"2016-12-21 00:00:00.0","address":"广东","device":0},{"time":"2016-12-23 00:00:00.0","address":"北京","device":0},{"time":"2016-12-23 00:00:00.0","address":"上海","device":0},{"time":"2016-12-23 00:00:00.0","address":"上海","device":0},{"time":"2016-12-25 00:00:00.0","address":"北京","device":0},{"time":"2016-12-25 00:00:00.0","address":"北京","device":0},{"time":"2016-12-26 00:00:00.0","address":"北京","device":0},{"time":"2016-12-27 00:00:00.0","address":"广东","device":0},{"time":"2016-12-28 00:00:00.0","address":"广东","device":0},{"time":"2016-12-28 00:00:00.0","address":"北京","device":0},{"time":"2016-12-28 00:00:00.0","address":"上海","device":0},{"time":"2016-12-28 00:00:00.0","address":"广东","device":0},{"time":"2016-12-28 00:00:00.0","address":"上海","device":0},{"time":"2016-12-28 00:00:00.0","address":"上海","device":0},{"time":"2016-12-28 00:00:00.0","address":"广东","device":0}]
         * comnum : 0  跟帖
         * portrait : http://118.244.212.82:9092/Images/image.png  用户图标
         */

        public String uid;
        public int integration;//用户积分票总数
        public int comnum;//评论总数
        public String portrait;//用户图标
        public List<LoginlogBean> loginlog;

        public static class LoginlogBean {
            /**
             * time : 2016-12-21 00:00:00.0
             * address : 广东
             * device : 0
             */

            public String time;
            public String address;
            public int device;//设备
        }
    }
}
