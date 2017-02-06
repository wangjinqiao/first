package com.example.myapplication.view.entity;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class NewsDetil {
    public String summary;
    public String icon;
    public String stamp;
    public String title;
    public int nid;
    public String link;
    public int type;

    public NewsDetil(String summary, String icon, String stamp, String title, int nid, String link, int type) {
        this.summary = summary;
        this.icon = icon;
        this.stamp = stamp;
        this.title = title;
        this.nid = nid;
        this.link = link;
        this.type = type;
    }
}
