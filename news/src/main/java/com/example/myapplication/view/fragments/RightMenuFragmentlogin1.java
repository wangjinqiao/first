package com.example.myapplication.view.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.view.MyAccountActivity;
import com.example.myapplication.view.QQActivity;
import com.example.myapplication.view.entity.UpdateInfo;
import com.example.myapplication.view.entity.UserCenterInfo;
import com.example.myapplication.view.presenter.NewsGetPresenter;
import com.example.myapplication.view.sharesdk.onekeyshare.OnekeyShare;
import com.example.myapplication.view.util.Constent;
import com.example.myapplication.view.util.LogWrapper;
import com.example.myapplication.view.util.UpdateVersion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class RightMenuFragmentlogin1 extends Fragment {
    @Bind(R.id.img_rihgt_fragment_login)
    ImageView imgRihgtFragmentLogin;
    @Bind(R.id.txt_rihgt_fragment_login)
    TextView txtRihgtFragmentLogin;
    MainActivity activity;
    FragmentManager fragmentManager;

    SharedPreferences sharedPreferences;

    String token;
    //用户信息
    UserCenterInfo userInfo;
    @Bind(R.id.right_menu_update)
    TextView rightMenuUpdate;
    @Bind(R.id.img_weixin)
    ImageView imgWeixin;
    @Bind(R.id.img_qq)
    ImageView imgQq;
    @Bind(R.id.img_circle)
    ImageView imgCircle;
    @Bind(R.id.img_blog)
    ImageView imgBlog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_right_menu_login1, container, false);
        ButterKnife.bind(this, view);
        activity = (MainActivity) this.getActivity();
        fragmentManager = this.getActivity().getSupportFragmentManager();
        //判断用户令牌是否存在
        getToken();
        if (token != "0") {
            visitHttp();

        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.img_rihgt_fragment_login, R.id.txt_rihgt_fragment_login,
            R.id.right_menu_update, R.id.img_weixin, R.id.img_qq, R.id.img_circle, R.id.img_blog})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.img_rihgt_fragment_login:
            case R.id.txt_rihgt_fragment_login:
                if (token != "0") {
                    //进入用户中心
                    Intent intent = new Intent(activity, MyAccountActivity.class);
                    startActivity(intent);
                } else {
                    //登录
                    fragmentManager.beginTransaction().
                            replace(R.id.activity_main, new LoginFragment1()).commit();
                    activity.showMainContent();
                }
                break;
            case R.id.right_menu_update:
                //更新
                UpdateVersion updateVersion = UpdateVersion.getUpdateVersion(activity);
                updateVersion.updateVersion();
                break;
            case R.id.img_weixin:
                //微信
                sharaToWechat(Wechat.NAME);
//                share();
                break;
            case R.id.img_qq:
                //qq
                sharaToqq(QQ.NAME);
//                share();
                break;
            case R.id.img_circle:
                //朋友圈
                sharaTofriend(WechatMoments.NAME);
//                share();
                break;
            case R.id.img_blog:
                //微博
                sharaTosina(SinaWeibo.NAME);
                break;
        }
    }

    //访问网络得到数据  拼接 :用户令牌
    private void visitHttp() {
        NewsGetPresenter newsGetPresenter = new NewsGetPresenter();
        String userPath = Constent.MY_ACCOUNT_PATH + token;

        newsGetPresenter.visitHttpForNews(activity, userPath, new Response.Listener<JSONObject>() {
            /**
             *成功响应
             */
            @Override
            public void onResponse(JSONObject jsonObject) {

                /** 解析数据*/
                Gson gson = new Gson();
                Type type = new TypeToken<UserCenterInfo>() {
                }.getType();
                userInfo = gson.fromJson(jsonObject.toString(), type);
                if (userInfo.status == 0) {
                    //设置内容
                    modifylogin();
                } else {
                    Toast.makeText(activity, "连接异常：" + userInfo.status, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            //访问网络失败
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(activity, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 更改图像，用户名
     */
    public void modifylogin() {
        //图像
        Picasso picasso = Picasso.with(activity);
        picasso.setIndicatorsEnabled(false);
        picasso.load(userInfo.data.portrait).into(imgRihgtFragmentLogin);
        LogWrapper.e("更改图像，用户名   ", userInfo.data.uid);
        txtRihgtFragmentLogin.setText(userInfo.data.uid);
    }

    //得到用户令牌
    public void getToken() {
        //共享文件
        sharedPreferences = activity.getSharedPreferences(Constent.NAME_SHSRED_PREFERENCE, 0);
        token = sharedPreferences.getString(Constent.TOKEN_KEY, "0");
    }


    /**
     * 分享到微信
     */
    public void sharaToWechat(String platName) {
        Toast.makeText(activity, "分享到微信", Toast.LENGTH_SHORT).show();
        Wechat.ShareParams shareParams = new Wechat.ShareParams();
        shareParams.setText("分享的内容");

        Platform platform = ShareSDK.getPlatform(platName);
        //分享
//        platform.share(shareParams);

        //授权，登录
        platform.authorize();
        platform.showUser(null);

        //监听事件
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(activity, "取消分享", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * 分享到qq
     */
    public void sharaToqq(String platName) {
        Toast.makeText(activity, "分享到qq", Toast.LENGTH_SHORT).show();
        QQ.ShareParams shareParams = new QQ.ShareParams();
        shareParams.setText("分享的内容");//必须调用
//        shareParams.setImagePath("/storage/sdcard/f1.jpg");//本地图片
//        shareParams.setImagePath("/storage/sdcard/f2.jpg");//本地图片
//        shareParams.setImageUrl("http://118.244.212.82:9092/Images/image.png");//网页图片
//        shareParams.setUrl("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=0&rsv_idx=1&tn=93288332_hao_pg&wd=sharesdk&rsv_pq=bddccf220000db48&rsv_t=265cdwDGNoCxrWMOVPIYPp9uwDn040BoCvKdNLLpBglOWUCJ8o7laOhafUiLR%2FLDYYPiukBi&rqlang=cn&rsv_enter=1&rsv_sug3=5&rsv_sug1=2&rsv_sug7=100");
        Platform platform = ShareSDK.getPlatform(platName);
        //分享
        platform.share(shareParams);

        //授权，登录
//        platform.authorize();
//        platform.showUser(null);

        //监听事件
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(activity, "取消分享", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * 分享到朋友圈
     */
    public void sharaTofriend(String platName) {
        Toast.makeText(activity, "分享到朋友圈", Toast.LENGTH_SHORT).show();
        WechatMoments.ShareParams shareParams = new WechatMoments.ShareParams();
        shareParams.setText("分享的内容");

        Platform platform = ShareSDK.getPlatform(platName);
        //分享
//        platform.share(shareParams);

        //授权，登录
        platform.authorize();
        platform.showUser(null);

        //监听事件
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(activity, "取消分享", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * 分享到微博
     */
    public void sharaTosina(String platName) {
        Toast.makeText(activity, "分享到微博", Toast.LENGTH_SHORT).show();

        SinaWeibo.ShareParams shareParams = new SinaWeibo.ShareParams();
        shareParams.setText("分享的内容");

        Platform platform = ShareSDK.getPlatform(platName);
        //分享
//        platform.share(shareParams);

        //授权，登录
        platform.authorize();
        platform.showUser(null);

        //监听事件
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel(Platform platform, int i) {
                Toast.makeText(activity, "取消分享", Toast.LENGTH_SHORT).show();

            }
        });

    }

    //一键分享
    public void share() {
        OnekeyShare onekeyShare = new OnekeyShare();
        onekeyShare.setText("需要分享的内容");
        onekeyShare.show(activity);
    }
}
