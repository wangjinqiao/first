package com.example.myapplication.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;


import com.example.myapplication.view.adapter.LogAccountAdapter;
import com.example.myapplication.view.adapter.PhotoAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.example.myapplication.view.entity.UserCenterInfo;
import com.example.myapplication.view.presenter.NewsGetPresenter;
import com.example.myapplication.view.util.Constent;
import com.example.myapplication.view.util.LogWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

import static java.lang.System.load;

/**
 * 用户中心
 */
public class MyAccountActivity extends AppCompatActivity {
    /**
     * 绑定控件
     */
    @Bind(R.id.img_account_back_title)
    ImageView imgAccountBackTitle;
    @Bind(R.id.txt_account_title)
    TextView txtAccountTitle;
    //图像
    @Bind(R.id.img_account_photo)
    ImageView imgAccountPhoto;
    //用户名，积分
    @Bind(R.id.txt_account_username)
    TextView txtAccountUsername;
    @Bind(R.id.txt_account_score)
    TextView txtAccountScore;
    //退出
    @Bind(R.id.btn_account_exit)
    Button btnAccountExit;
    //跟帖总数
    @Bind(R.id.txt_account_num_follow)
    TextView txtAccountNumFollow;
    //日志
    @Bind(R.id.lst_logs_account)
    ListView lstLogsAccount;

    String userPath;//用户中心路径
    String uploadPhotoPath;//上传图片路径

    SharedPreferences sharedPreferences;
    //用户信息
    UserCenterInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);
        //得到路径
        getToken();
        //访问网络得到用户数据
        visitHttp(userPath, responseForUser);
    }

    //访问网络得到数据
    public void visitHttp(String path, Response.Listener<JSONObject> response) {
        NewsGetPresenter newsGetPresenter = new NewsGetPresenter();
        newsGetPresenter.visitHttpForNews(MyAccountActivity.this, path, response, new Response.ErrorListener() {
            //访问网络失败
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MyAccountActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 成功响应 用户中心数据
     */
    Response.Listener<JSONObject> responseForUser = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject jsonObject) {

            /** 解析数据*/
            Gson gson = new Gson();
            Type type = new TypeToken<UserCenterInfo>() {
            }.getType();
            userInfo = gson.fromJson(jsonObject.toString(), type);

            if (userInfo.status == 0) {
                //设置内容
                setContents();
                setLogs();
            } else {

            }
        }
    };


    @OnClick({R.id.img_account_back_title, R.id.img_account_photo, R.id.btn_account_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_account_back_title:
                /**
                 * 返回主界面
                 */
                Intent intent = new Intent(MyAccountActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.img_account_photo:
                //设置图像,显示菜单目录
                openOptionsMenu();
//                Intent intent1 = new Intent(this, SelectPicActivity.class);
//                startActivity(intent1);
                break;
            case R.id.btn_account_exit:
                /**
                 * 退出登录，清除用户令牌
                 */
                sharedPreferences.edit().remove(Constent.TOKEN_KEY).commit();
                Intent intentExit = new Intent(MyAccountActivity.this, MainActivity.class);
                startActivity(intentExit);
        }
    }


    /**
     * 调用相机拍照
     */
    private void takePhoto() {
        /**
         * 跳转到拍照页面
         */
//        Intent intent = new Intent(this, CameraActivity.class);
        Intent intent = new Intent(this, SelectPicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("item", 0);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, 0);
    }

    /**
     * 从图库中选择 todo
     */
    private void choosePhotoFromLib() {
        //获取本地的图片
        Intent intent = new Intent(this, SelectPicActivity.class);
//        Intent intent = new Intent(this, CameraActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("item", 1);
        intent.putExtra("bundle", bundle);
        startActivityForResult(intent, 1);
    }

    //得到用户令牌
    public void getToken() {
        //共享文件
        sharedPreferences = MyAccountActivity.this.getSharedPreferences(Constent.NAME_SHSRED_PREFERENCE, 0);
        String token = sharedPreferences.getString(Constent.TOKEN_KEY, "未获取");

        //  拼接 :用户令牌
        userPath = Constent.MY_ACCOUNT_PATH + token;
        // 拼接 ：token=用户令牌& portrait =头像
        uploadPhotoPath = Constent.UPLOAD_IMG_PATH + "token=" + token + "&portrait=" + "";

    }

    /**
     * 设置用户名，积分，跟帖数,图像
     */
    public void setContents() {
        txtAccountUsername.setText(userInfo.data.uid);
        txtAccountScore.setText("积分：" + userInfo.data.integration);
        txtAccountNumFollow.setText("跟帖数统计：" + userInfo.data.comnum);

        //图像
        Picasso picasso = Picasso.with(MyAccountActivity.this);
        picasso.setIndicatorsEnabled(false);
        picasso.load(userInfo.data.portrait).into(imgAccountPhoto);

    }

    /**
     * 设置登录日志
     */
    public void setLogs() {

        LogAccountAdapter logAccountAdapter = new LogAccountAdapter(MyAccountActivity.this, userInfo.data.loginlog);
        lstLogsAccount.setAdapter(logAccountAdapter);

    }


    /**
     * 显示menu菜单
     */
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("设置图像");
        menu.add("拍照");
        menu.add("图库");
        MenuItem item = menu.findItem(0);
        MenuItem item1 = menu.findItem(1);
        MenuItem item2 = menu.findItem(2);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                takePhoto();
                return false;
            }
        });
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                choosePhotoFromLib();
                return false;
            }
        });
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                choosePhotoFromLib();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
