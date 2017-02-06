package com.example.myapplication.view.entity;

/**
 * Created by Administrator on 2016/12/28 0028.
 */

public class UpdateInfo {

    /**
     * pkgName : com.ys.android
     * link : http://118.244.212.82:9092/Images/test.apk
     * md5 : 9770ad0a9dc59fc51ec2db3079697536
     * version : 1
     */

    public String pkgName;//包名 应用包名（用来二次确认）
    public String link;//下载地址  应用最新版的下载地址
    public String md5;//校验值 用来判断下载安装包有无损坏
    public String version;//应用最新版的本号
}
