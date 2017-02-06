package com.example.myapplication.view;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.view.util.CameraUtil;
import com.example.myapplication.view.util.Constent;
import com.example.myapplication.view.util.LogWrapper;
import com.example.myapplication.view.util.PreviewCamera;

import java.io.File;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogWrapper.e("absolutePathPhoto=onActivityResult", absolutePathPhoto+"   ====");
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        int item = bundle.getInt("item");
        switch (item) {
            case 0:
                /** 硬件检查  **/
                if (CameraUtil.ishasCamera(this) == false) {
                    Toast.makeText(this, "很抱歉，您的设备可能不支持摄像头功能！", Toast.LENGTH_SHORT).show();
                    return;
                }

                takePhoto(); //拍照
                break;
            case 1://图库
                inPhotoLib();
                break;
        }
//        setContentView(R.layout.activity_camera);

    }

    //图库
    private void inPhotoLib() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent1.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent1, Constent.PHOTOLIB_REQUEST_CODE);
        finish();
    }

    //拍照
    String photoPath;//相片保存具体路径
    Uri uriPhotoByTake;//将文件地址转为Uir格式

    public void takePhoto() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {//已安装
            photoPath = Constent.PHOTO_SAVE_DIR_PATH + System.currentTimeMillis() + ".png";

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//ACTION_IMAGE_CAPTURE:拍摄，录制
            File fileDir = new File(Constent.PHOTO_SAVE_DIR_PATH);
            if (!fileDir.exists()) {
                //如果文件夹不存在，则创建文件夹
                fileDir.mkdirs();
            }
            File photoFile = new File(photoPath);//创建照片文件
            uriPhotoByTake = Uri.fromFile(photoFile);//将文件地址转为Uir格式
            //设置系统相机拍摄完照片后照片存放路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhotoByTake);
            startActivityForResult(intent, Constent.CAMERA_REQUEST_CODE);
            finish();

        } else {//其他状态
            Toast.makeText(getApplicationContext(), "请确认已经插入sd卡", Toast.LENGTH_SHORT).show();
        }
    }

    // 拿到图片的绝对地址
    String absolutePathPhoto;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constent.PHOTOLIB_REQUEST_CODE) {//图库
                try {
                    // 拿到图片的绝对地址,将其设置给用户图像 // TODO: 2017/1/3 0003
                    Uri uriPhoto = data.getData();
                    absolutePathPhoto = getAbsolutePath(uriPhoto);
                    LogWrapper.e("absolutePathPhoto=onActivityResult", absolutePathPhoto+"   ====");


                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == Constent.CAMERA_REQUEST_CODE) {//相机
                // 拿到图片的绝对地址,将其设置给用户图像 // TODO: 2017/1/3 0003
                absolutePathPhoto = getAbsolutePath(uriPhotoByTake);
                LogWrapper.e("absolutePathPhoto=onActivityResult", absolutePathPhoto+"   ====");
            }
        }
    }

    //拿到图片的绝对地址 // TODO: 2017/1/3 0003
    public String getAbsolutePath(final Uri uri) {
        if (null == uri) return null;

        final String scheme = uri.getScheme();

        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = this.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

   
}
