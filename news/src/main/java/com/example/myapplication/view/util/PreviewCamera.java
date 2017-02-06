package com.example.myapplication.view.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Administrator on 2017/1/3 0003.
 * 相机预览类
 */
@SuppressLint("ViewConstructor")
public class PreviewCamera extends SurfaceView implements SurfaceHolder.Callback {

    private Camera camera;
    private SurfaceHolder mholder;

    public PreviewCamera(Context context, Camera camera) {
        this(context, null, camera);
    }

    public PreviewCamera(Context context, AttributeSet attrs, Camera camera) {
        this(context, attrs, 0, camera);
    }

    public PreviewCamera(Context context, AttributeSet attrs, int defStyleAttr, Camera camera) {
        super(context, attrs, defStyleAttr);
        this.camera = camera;
        mholder = this.getHolder();
        mholder.addCallback(this);//添加回调接口
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            //设置预览
            camera.setPreviewDisplay(mholder);
            camera.startPreview();
            camera.setDisplayOrientation(90);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // 如果预览无法更改或旋转，注意此处的事件
        // 确保在缩放或重排时停止预览
        if (mholder.getSurface() == null){
            // 预览surface不存在
            return;
        }
        // 更改时停止预览
        try {
            camera.stopPreview();
        } catch (Exception e){
            // 忽略：试图停止不存在的预览
        }
        // 在此进行缩放、旋转和重新组织格式
        // 以新的设置启动预
        try {
            camera.setPreviewDisplay(mholder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (Exception e){
            LogWrapper.e("TAG", "Error is " + e.getMessage());
        }
    }
}
