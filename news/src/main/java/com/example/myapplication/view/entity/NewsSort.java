package com.example.myapplication.view.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

public class NewsSort {

    /**
     * data : [{"group":"新闻","gid":1,"subgrp":[{"subgroup":"社会","subid":2},{"subgroup":"军事","subid":1}]},{"group":"财经","gid":2,"subgrp":[{"subgroup":"基金","subid":4},{"subgroup":"股票","subid":3}]},{"group":"科技","gid":3,"subgrp":[{"subgroup":"手机","subid":5}]},{"group":"体育","gid":4,"subgrp":[{"subgroup":"英超","subid":7},{"subgroup":"NBA","subid":8}]}]
     * message : OK
     * status : 0
     */

    public String message;
    public int status;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * group : 新闻
         * gid : 1
         * subgrp : [{"subgroup":"社会","subid":2},{"subgroup":"军事","subid":1}]
         */

        public String group;
        public int gid;
        public List<SubgrpBean> subgrp;

        public static class SubgrpBean {
            /**
             * subgroup : 社会
             * subid : 2
             */

            public String subgroup;
            public int subid;
        }
    }
}
