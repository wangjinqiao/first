package com.example.myapplication.view.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * Created by Administrator on 2017/1/3 0003.
 * <p>
 * 访问相机辅助类
 */

public class CameraUtil {
    /**
     * 创建相机对象
     *
     * @return
     */
    public static Camera getInstanceCamera() {
        Camera camera = Camera.open();
        return camera;
    }

    /**
     * 判断是否支持摄像头
     *
     * @param context
     * @return
     */
    public static boolean ishasCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }
}
