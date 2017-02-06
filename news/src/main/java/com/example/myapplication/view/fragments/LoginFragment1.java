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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.view.MyAccountActivity;
import com.example.myapplication.view.entity.LoginInfo;
import com.example.myapplication.view.entity.RegisterInfo;
import com.example.myapplication.view.presenter.NewsGetPresenter;
import com.example.myapplication.view.util.Constent;
import com.example.myapplication.view.util.LogWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class LoginFragment1 extends Fragment {


    @Bind(R.id.edt_name_login)
    EditText edtNameLogin;
    @Bind(R.id.edt_pwd_login)
    EditText edtPwdLogin;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.btn_forget_pwd)
    Button btnForgetPwd;
    @Bind(R.id.btn_login)
    Button btnLogin;

    /**
     * fragmentManager管理fragment
     */
    FragmentManager fragmentManager;
    MainActivity activity;
    //登录得到的信息
    LoginInfo loginInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_login, container, false);
        ButterKnife.bind(this, view);
        activity = (MainActivity) this.getActivity();
        activity.modifyTitle("用户登录");
        fragmentManager = this.getActivity().getSupportFragmentManager();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_register, R.id.btn_forget_pwd, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                showRegister();
                break;
            case R.id.btn_forget_pwd:
                showForgetpwd();
                break;
            case R.id.btn_login:
                /**
                 * 输入正确的用户名和密码todo
                 */
                String name = edtNameLogin.getText().toString();
                String pwd = edtPwdLogin.getText().toString();
                boolean isNameTrue = Pattern.matches("(\\w+)", name);
                if (!isNameTrue) {
                    Toast.makeText(activity, "用户名输入错误", Toast.LENGTH_LONG).show();
                    edtNameLogin.setText("");
                }
                boolean isPwdTrue = Pattern.matches("((\\w){6,16})", pwd);
                if (!isPwdTrue) {
                    Toast.makeText(activity, "密码输入错误", Toast.LENGTH_LONG).show();
                    edtPwdLogin.setText("");
                }
                // 路径  &uid=用户名&pwd=密码&device=0

                /**
                 * 跳转到我的用户界面
                 */
                if (isNameTrue && isPwdTrue) {

                    NewsGetPresenter newsGetPresenter = new NewsGetPresenter();
                    String loginPath = Constent.LOGIN_PATH + "&uid=" + name + "&pwd=" + pwd + "&device=0";

                    newsGetPresenter.visitHttpForNews(activity, loginPath, new Response.Listener<JSONObject>() {

                        /**
                         *{"message":"OK","status":0,"data":{"result":0,"token":"b1308402bcf01eda92d25f4581b3eeb9","explain":"登录成功"}}
                         *{"message":"用户名或密码错误！","status":-1}
                         * {"message":"参数有误！","status":-1}
                         */

                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            /** 解析数据*/
                            Gson gson = new Gson();
                            Type type = new TypeToken<LoginInfo>() {
                            }.getType();
                            loginInfo = gson.fromJson(jsonObject.toString(), type);


                            if (loginInfo.status == 0) {
                                //登录成功,将令牌token存入sharedpreference
                                String token = loginInfo.data.token;
                                //共享文件
                                SharedPreferences sharedPreferences = activity.getSharedPreferences(Constent.NAME_SHSRED_PREFERENCE, 0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Constent.TOKEN_KEY, token);
                                editor.commit();

                                Toast.makeText(activity, loginInfo.data.explain, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(activity, MyAccountActivity.class);
                                startActivity(intent);
                            } else {
                                //loginInfo.status == -1
                                Toast.makeText(activity, loginInfo.message, Toast.LENGTH_SHORT).show();
                                edtNameLogin.setText("");
                                edtPwdLogin.setText("");
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


                break;
        }
    }

    /**
     * 添加注册fragment
     */
    public void showRegister() {
        fragmentManager.beginTransaction().replace(R.id.activity_main, new LoginFragmentRegister2()).commit();
    }

    /**
     * 添加忘记密码fragment
     */
    public void showForgetpwd() {
        fragmentManager.beginTransaction().replace(R.id.activity_main, new LoginFragmentForgetpwd3()).commit();
    }
}
