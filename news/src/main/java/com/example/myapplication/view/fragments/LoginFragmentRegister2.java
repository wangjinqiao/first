package com.example.myapplication.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.view.MyAccountActivity;
import com.example.myapplication.view.entity.NewsAll;
import com.example.myapplication.view.entity.NewsDetil;
import com.example.myapplication.view.entity.RegisterInfo;
import com.example.myapplication.view.presenter.NewsGetPresenter;
import com.example.myapplication.view.util.Constent;
import com.example.myapplication.view.util.LogWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class LoginFragmentRegister2 extends Fragment {

    @Bind(R.id.edt_email_register)
    EditText edtEmailRegister;
    @Bind(R.id.edt_name_register)
    EditText edtNameRegister;
    @Bind(R.id.edt_pwd_register)
    EditText edtPwdRegister;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.ckb_register_agree)
    CheckBox ckbRegisterAgree;

    MainActivity activity;

    //注册得到的信息
    RegisterInfo registerInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_register, container, false);
        ButterKnife.bind(this, view);
        activity = (MainActivity) this.getActivity();
        activity.modifyTitle("用户注册");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_register, R.id.ckb_register_agree})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if (ckbRegisterAgree.isChecked()) {
                    String email = edtEmailRegister.getText().toString();
                    String name = edtNameRegister.getText().toString();
                    String pwd = edtPwdRegister.getText().toString();
                    /**
                     * 输入正确的用户名和密码,邮箱
                     */
                    boolean isEmailTrue = Pattern.matches("[^0&&\\w][\\w]{5,10}\\@(qq|126|136|sina|gmail)(\\.com)", email);
                    if (!isEmailTrue) {
                        Toast.makeText(activity, "邮箱输入错误", Toast.LENGTH_LONG).show();
                        edtEmailRegister.setText("");
                    }
                    boolean isNameTrue = Pattern.matches("(\\w+)", name);
                    if (!isNameTrue) {
                        Toast.makeText(activity, "用户名输入错误", Toast.LENGTH_LONG).show();
                        edtNameRegister.setText("");
                    }
                    boolean isPwdTrue = Pattern.matches("((\\w){6,16})", pwd);
                    if (!isPwdTrue) {
                        Toast.makeText(activity, "密码输入错误", Toast.LENGTH_LONG).show();
                        edtPwdRegister.setText("");
                    }


                    /**
                     * 跳转到登录界面，
                     * 访问网络得到用户令牌 ,拼接 &uid=用户名&email=邮箱&pwd=登陆密码
                     *
                     */
                    if (isEmailTrue && isNameTrue && isPwdTrue && ckbRegisterAgree.isChecked()) {
                        NewsGetPresenter newsGetPresenter = new NewsGetPresenter();
                        final String registerPath = Constent.REGISTER_PATH + "&uid=" + name + "&email=" + email + "&pwd=" + pwd;
                        newsGetPresenter.visitHttpForNews(activity, registerPath, new Response.Listener<JSONObject>() {

                            /**
                             * {"data":{"explain":"用户名已存在！","token":null,"result":-2},"message":"OK","status":0}
                             *
                             * 访问网络成功  {"message":"OK","status":0,"data":{"result":0,"token":"e3ab28e092e00dda3cb0edbcaaef67a4","explain":"注册成功"}}
                             */

                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                /** 解析数据*/
                                Gson gson = new Gson();
                                Type type = new TypeToken<RegisterInfo>() {
                                }.getType();
                                registerInfo = gson.fromJson(jsonObject.toString(), type);

                                String explain = registerInfo.getData().explain;
                                Toast.makeText(activity, explain, Toast.LENGTH_SHORT).show();
                                if (registerInfo.getStatus() == 0) {
                                    int result = registerInfo.getData().result;
                                    if (result == 0) {
                                        //注册成功
                                        String token = registerInfo.getData().getToken();
                                        activity.getSupportFragmentManager().
                                                beginTransaction().replace(R.id.activity_main, new LoginFragment1()).commit();
                                    } else {
                                        //用户名已存在
                                        edtNameRegister.setText("");
                                    }
                                } else {
                                    //registerInfo.status == -1,-2, -3
                                    Toast.makeText(activity, registerInfo.getMessage(), Toast.LENGTH_SHORT).show();
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
                break;
            case R.id.ckb_register_agree:
                break;
        }
    }
}
