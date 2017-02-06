package com.example.myapplication.view;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.model.AsyncHttp;
import com.example.myapplication.model.OnResponseListner;
import com.example.myapplication.util.Constents;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2016/12/21 0021.
 * com.example.myapplication.com.example.myapplication.view
 */

public class MainView implements View.OnClickListener {
    public Activity activity;
    protected EditText medtname;
    protected EditText medtpwd;
    protected Button mbtnLogin;
    protected AsyncHttp asyncHttp;

    public MainView(Activity activity) {
        this.activity = activity;
        medtname = (EditText) activity.findViewById(R.id.edt_main_name);
        medtpwd = (EditText) activity.findViewById(R.id.edt_main_pwd);
        mbtnLogin = (Button) activity.findViewById(R.id.btn_main_login);
        mbtnLogin.setOnClickListener(this);

    }

    public void getAsyncHttp(AsyncHttp asyncHttp) {
        this.asyncHttp = asyncHttp;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_login:
                mbtnLogin.setText("已点击");
                JSONObject json=new JSONObject();
                try {
                    json.put("username",medtname.getText().toString());
                    json.put("pwd",medtpwd.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                asyncHttp.invitHttp(Constents.METHOD_POST,Constents.PATH_BASE,json.toString(), new OnResponseListner() {
                    @Override
                    public void onResponse(String response) {


                        Toast.makeText(activity, "response:" + response, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponseFail(String error) {

                        Toast.makeText(activity, "error:" + error, Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }

    }
}
