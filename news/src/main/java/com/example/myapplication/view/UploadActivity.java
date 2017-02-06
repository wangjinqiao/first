package com.example.myapplication.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.view.db.SharedFile;
import com.example.myapplication.view.util.Constent;
import com.example.myapplication.view.util.LogWrapper;
import com.example.myapplication.view.util.UploadUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 说明：主要用于选择文件和上传文件操作
 * 选择图片和上传界面，包括上传完成和异常的回调监听
 */
public class UploadActivity extends AppCompatActivity implements UploadUtil.OnUploadProcessListener {
    private static final String TAG = "uploadImage";

    /**
     * 上传文件
     */
    protected static final int TO_UPLOAD_FILE = 1;
    /**
     * 上传文件响应
     */
    protected static final int UPLOAD_FILE_DONE = 2; //
    /**
     * 选择文件
     */
    public static final int TO_SELECT_PHOTO = 3;
    /**
     * 上传初始化
     */
    private static final int UPLOAD_INIT_PROCESS = 4;
    /**
     * 上传中
     */
    private static final int UPLOAD_IN_PROCESS = 5;
    /***
     * user_image?token=用户令牌& portrait =头像
     *
     * 拼接：用户令牌& portrait =头像
     */
    private static String requestURL = Constent.UPLOAD_PHOTO_PATH;
    @Bind(R.id.btn_selectImage)
    Button btnSelectImage;
    @Bind(R.id.btn_uploadImage)
    Button btnUploadImage;
    @Bind(R.id.img_show_photo)
    ImageView imgShowPhoto;
    @Bind(R.id.txt_uploadImageResult)
    TextView txtUploadImageResult;
    @Bind(R.id.pgb_upload)
    ProgressBar pgbUpload;


    private String picPath = "";
    private ProgressDialog progressDialog;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);
        getToken();
        initView();
    }
    /***
     * user_image?token=用户令牌& portrait =头像
     *
     * 拼接：用户令牌& portrait =头像
     */
private void getToken(){
    String token =  SharedFile.sharedPreferences.getString(Constent.TOKEN_KEY, "未获取");

    // 拼接 ：token=用户令牌& portrait =头像
    requestURL = Constent.UPLOAD_PHOTO_PATH + "token=" + token + "&portrait=" + picPath;

    LogWrapper.e("地址",requestURL);
}
    /**
     * 初始化数据
     */
    private void initView() {
        progressDialog = new ProgressDialog(this);
    }

    @OnClick({R.id.btn_selectImage, R.id.btn_uploadImage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_selectImage:
                Intent intent = new Intent(this, SelectPicActivity.class);
                startActivityForResult(intent, TO_SELECT_PHOTO);
                break;
            case R.id.btn_uploadImage:
                if (picPath != null) {
                    handler.sendEmptyMessage(TO_UPLOAD_FILE);
                } else {
                    Toast.makeText(this, "上传的文件路径出错", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == TO_SELECT_PHOTO) {
            picPath = data.getStringExtra(SelectPicActivity.KEY_PHOTO_PATH);
            Log.i(TAG, "最终选择的图片=" + picPath);
            Bitmap bm = BitmapFactory.decodeFile(picPath);
            imgShowPhoto.setImageBitmap(bm);
        }
        getToken();
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 上传服务器响应回调
     */
    @Override
    public void onUploadDone(int responseCode, String message) {
        progressDialog.dismiss();
        Message msg = Message.obtain();
        msg.what = UPLOAD_FILE_DONE;
        msg.arg1 = responseCode;
        msg.obj = message;
        handler.sendMessage(msg);
    }

    private void toUploadFile() {
        txtUploadImageResult.setText("正在上传中...");
        progressDialog.setMessage("正在上传文件...");
        progressDialog.show();
        String fileKey = "pic";
        UploadUtil uploadUtil = UploadUtil.getInstance();
        ;
        uploadUtil.setOnUploadProcessListener(this); //设置监听器监听上传状态

        Map<String, String> params = new HashMap<String, String>();
        params.put("orderId", "11111");
        uploadUtil.uploadFile(picPath, fileKey, requestURL, params);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TO_UPLOAD_FILE:
                    toUploadFile();
                    break;

                case UPLOAD_INIT_PROCESS:
                    pgbUpload.setMax(msg.arg1);
                    break;
                case UPLOAD_IN_PROCESS:
                    pgbUpload.setProgress(msg.arg1);
                    break;
                case UPLOAD_FILE_DONE:
                    String result = "响应码：" + msg.arg1 + "\n响应信息：" + msg.obj + "\n耗时：" + UploadUtil.getRequestTime() + "秒";
                    txtUploadImageResult.setText(result);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

    @Override
    public void onUploadProcess(int uploadSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_IN_PROCESS;
        msg.arg1 = uploadSize;
        handler.sendMessage(msg);
    }

    @Override
    public void initUpload(int fileSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_INIT_PROCESS;
        msg.arg1 = fileSize;
        handler.sendMessage(msg);
    }


}
