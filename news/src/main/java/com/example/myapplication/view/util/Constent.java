package com.example.myapplication.view.util;

import android.os.Environment;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public interface Constent {
    //共享文件的名称
    String NAME_SHSRED_PREFERENCE = "NewsPreference";
    //是否第一次打开应用
    String IS_FIRST_KEY = "isFirstOpen";

    //获取新闻的路径
    String PATH_BASE = "http://118.244.212.82:9092/newsClient";
    String PATH_GET_WAR = PATH_BASE + "/news_list?ver=1&subid=1&dir=1&nid=1&stamp=20140321&cnt=20";
    String PATH_GET_WORLD = PATH_BASE + "/news_list?ver=1&subid=2&dir=1&nid=1&stamp=20140321&cnt=20";
    String PATH_GET_3 = PATH_BASE + "/news_list?ver=1&subid=3&dir=1&nid=1&stamp=20140321&cnt=20";
    String PATH_GET_4 = PATH_BASE + "/news_list?ver=1&subid=4&dir=1&nid=1&stamp=20140321&cnt=20";
    String PATH_GET_5 = PATH_BASE + "/news_list?ver=1&subid=5&dir=1&nid=1&stamp=20140321&cnt=20";
    String PATH_GET_7 = PATH_BASE + "/news_list?ver=1&subid=7&dir=1&nid=1&stamp=20140321&cnt=20";
    String PATH_GET_8 = PATH_BASE + "/news_list?ver=1&subid=8&dir=1&nid=1&stamp=20140321&cnt=20";


    //    用户令牌key
    String TOKEN_KEY = "token";
    /**
     * 用户中心： user_home?ver=版本号&imei=手机标识符&token =用户令牌
     * <p>手机标识符:1
     * 头像上传:  user_image?token=用户令牌& portrait =头像
     * <p>
     * 登录：   user_login?ver=版本号&uid=用户名&pwd=密码&device=0
     * <p>
     * 注册：   user_register?ver=版本号&uid=用户名&email=邮箱&pwd=登陆密码
     * <p>
     * 找回密码： user_forgetpass?ver=版本号&email=邮箱
     * 上传图片：user_image?token=用户令牌& portrait =头像
     * 更新版本：update?imei=唯一识别号&pkg=包名&ver=版本
     */
    //注册路径
    String REGISTER_PATH = "http://118.244.212.82:9092/newsClient/user_register?ver=1";
    //登录路径
    String LOGIN_PATH = "http://118.244.212.82:9092/newsClient/user_login?ver=1";
    //找回密码路径
    String FORGET_PWD_PATH = "http://118.244.212.82:9092/newsClient/user_forgetpass?ver=1";
    //用户中心路径
    String MY_ACCOUNT_PATH = "http://118.244.212.82:9092/newsClient/user_home?ver=1&imei=1&token=";
    //上传图片的路径
    String UPLOAD_IMG_PATH = "http://118.244.212.82:9092/newsClient/user_image?";
    //    更新版本
    String UPDATE_VERSION_PATH = "http://118.244.212.82:9092/newsClient/update?imei=1&pkg=";


    /**
     * 评论的数量：cmt_num?ver=版本号& nid=新闻编号
     * 显示评论：cmt_list ?ver=版本号&nid=新闻id&type=1&stamp=yyyyMMdd&cid=评论id&dir=0&cnt=20
     * 发表评论：cmt_commit?ver=版本号&nid=新闻编号&token=用户令牌&imei=手机标识符&ctx=评论内容
     */
    String COMMENT_NUM_PATH = PATH_BASE + "/cmt_num?ver=1&nid=";
    String COMMENT_LIST_PATH = PATH_BASE + "/cmt_list?ver=1";
    String SEND_COMMENT_PATH = PATH_BASE + "/cmt_commit?ver=1";

    //数据库名
    String DB_FILENAME = "collection.db";
    //数据库表名
    String TABLE_NAME = "collectionNids";

    //数据库名版本
    int DB_VERSION = 1;
    //列名
    String TABLE_COLUMN_NID = "nid";
    String TABLE_COLUMN_ICON = "icon";
    String TABLE_COLUMN_SUMMARY = "summary";
    String TABLE_COLUMN_TITLE = "title";
    String TABLE_COLUMN_TIME = "time";
    String TABLE_COLUMN_LINK = "link";

    /**
     * 拍照
     * user_image?token=用户令牌& portrait =头像
     */
    //拍照存放路径
    String PHOTO_SAVE_DIR_PATH = Environment.getExternalStorageDirectory().getPath() + "/news/camera/";
    int CAMERA_REQUEST_CODE=10;//相机的请求码
    int PHOTOLIB_REQUEST_CODE=20;//相册的请求码
    String UPLOAD_PHOTO_PATH=PATH_BASE+"/user_image?token=";
}
