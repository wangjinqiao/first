package com.example.myapplication.view.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

public class CommentListInfo {
    /**
     * message : OK
     * status : 0
     * data : [{"uid":"123","content":"评论内容","stamp":"2016-12-29 19:29:32","cid":8416,"portrait":"http://118.244.212.82:9092/Images/20161126103809.jpg"},
     */

    public String message;
    public int status;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * uid : 123
         * content : 评论内容
         * stamp : 2016-12-29 19:29:32
         * cid : 8416 评论id
         * portrait : http://118.244.212.82:9092/Images/20161126103809.jpg
         */

        public String uid;
        public String content;
        public String stamp;
        public int cid;
        public String portrait;
    }
}
