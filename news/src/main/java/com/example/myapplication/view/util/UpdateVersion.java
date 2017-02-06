package com.example.myapplication.view.util;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.view.entity.UpdateInfo;
import com.example.myapplication.view.presenter.NewsGetPresenter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by Administrator on 2017/1/4 0004.
 * 版本更新
 */

public class UpdateVersion {
    Context mContext;
    public static UpdateVersion updateVersion;

    //单例模式
    private UpdateVersion(Context mContext) {
        this.mContext = mContext;
    }

    public static UpdateVersion getUpdateVersion(Context mContext) {

        if (updateVersion == null) {
            synchronized (UpdateVersion.class) {
                if (updateVersion == null) {
                    updateVersion = new UpdateVersion(mContext);
                }
            }
        }
        return updateVersion;
    }

    /**
     * 更新版本
     * 拼接：包名&ver=版本
     */
    UpdateInfo updateInfo;//更新信息

    public void updateVersion() {
        String updatePath = Constent.UPDATE_VERSION_PATH + "com.example.myapplication" + "&ver=" + 1;
        new NewsGetPresenter().visitHttpForNews(mContext, updatePath, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                updateInfo = gson.fromJson(jsonObject.toString(), new TypeToken<UpdateInfo>() {
                }.getType());

                int currVer = getCurrVer();
                if (Integer.parseInt(updateInfo.version) > currVer) {
                    //显示对话框,更新版本
                    showUpdateDig();
                } else {
                    Toast.makeText(mContext, "目前已经是最新版本", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });


    }

    //获取当前应用版本
    public int getCurrVer() {
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    //显示对话框,更新版本
    public void showUpdateDig() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("版本更新");
        builder.setMessage("检测到新版本，是否更新");
        builder.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogWrapper.e("showUpdateDig", "暂不更新");
            }
        });
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogWrapper.e("showUpdateDig", "立即更新");
                //下载apk，安装apk
                downloadAPK();
            }
        }).show();
    }

    //下载apk，安装apk
    public void downloadAPK() {
        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(updateInfo.link));
        request.setVisibleInDownloadsUi(true);
        File file = new File("/storage/sdcard/loadapks");
        if (!file.exists()) {
            file.mkdirs();
        }

        request.setDestinationInExternalFilesDir(mContext,null , "/storage/sdcard/loadapks" + getApkName(updateInfo.link));
        LogWrapper.e("downloadAPK", "下载apk，安装apk"+getApkName(updateInfo.link));
        request.setShowRunningNotification(true);
        downloadManager.enqueue(request);
        //下载完成后安装 // TODO: 2017/1/4 0004
    }

    public String getApkName(String link){
        return link.substring(link.lastIndexOf("/")+1);
    }
}
