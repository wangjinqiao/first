package com.example.myapplication.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
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
import com.example.myapplication.view.entity.ForgetpwdInfo;
import com.example.myapplication.view.entity.RegisterInfo;
import com.example.myapplication.view.presenter.NewsGetPresenter;
import com.example.myapplication.view.util.Constent;
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

public class LoginFragmentForgetpwd3 extends Fragment {
    @Bind(R.id.edt_forgetpwd_frag)
    EditText edtForgetpwdFrag;
    @Bind(R.id.btn_forgetpwd_frag_sure)
    Button btnForgetpwdFragSure;

    MainActivity activity;
    //找回密码得到的信息
    ForgetpwdInfo forgetpwdInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_forgetpwd, container, false);
        ButterKnife.bind(this, view);
        activity = (MainActivity) this.getActivity();
        activity.modifyTitle("忘记密码");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.btn_forgetpwd_frag_sure)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_forgetpwd_frag_sure:
                String email = edtForgetpwdFrag.getText().toString();
                //请输入正确的邮箱格式
                boolean isEmailTrue = Pattern.matches("^[A-Za-z0-9][\\w\\._]*[a-zA-Z0-9]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})", email);
                if (!isEmailTrue) {
                    Toast.makeText(activity, "请输入正确的邮箱格式", Toast.LENGTH_SHORT).show();
                    edtForgetpwdFrag.setText("");
                }
                /**
                 * 访问网络：
                 *  已成功发送到邮箱，跳转到用户登录
                 *  该邮箱不存在
                 */
                if (isEmailTrue) {
                    NewsGetPresenter newsGetPresenter = new NewsGetPresenter();
                    String forgetpwdPath = Constent.FORGET_PWD_PATH + "&email=" + email;
                    newsGetPresenter.visitHttpForNews(activity, forgetpwdPath, new Response.Listener<JSONObject>() {

                        /**
                         * {"data":{"explain":"用户名已存在！","token":null,"result":-2},"message":"OK","status":0}
                         *
                         * 访问网络成功  {"message":"OK","status":0,"data":{"result":0,"token":"e3ab28e092e00dda3cb0edbcaaef67a4","explain":"注册成功"}}
                         */

                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            /**
                             *  解析数据
                             *  {"message":"OK","status":0,"data":{"result":0,"explain":"已成功发送到邮箱"}
                             *  {"message":"ERROR","status":0,"data":{"result":-2,"explain":"该邮箱不存在！"}}
                             *
                             */
                            Gson gson = new Gson();
                            Type type = new TypeToken<ForgetpwdInfo>() {
                            }.getType();
                            forgetpwdInfo = gson.fromJson(jsonObject.toString(), type);
                            //处理数据
                            if (forgetpwdInfo.status == 0) {
                                int result = forgetpwdInfo.data.result;
                                Toast.makeText(activity, forgetpwdInfo.data.explain, Toast.LENGTH_SHORT).show();
                                if (result == 0) {
                                    //找回密码成功，跳转到用户登录

                                    activity.getSupportFragmentManager().
                                            beginTransaction().replace(R.id.activity_main, new LoginFragment1()).commit();
                                } else {
                                    //该邮箱不存在
                                    edtForgetpwdFrag.setText("");
                                }
                            }else {
                                //forgetpwdInfo.status == -1,-2, -3
                                Toast.makeText(activity,forgetpwdInfo.message,Toast.LENGTH_SHORT).show();
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
}
