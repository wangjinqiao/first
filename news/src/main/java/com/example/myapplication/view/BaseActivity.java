package com.example.myapplication.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.R;

import com.example.myapplication.view.util.MethodsInMainActivity;

public class BaseActivity extends AppCompatActivity implements MethodsInMainActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

    }




    public ProgressDialog progressDialog;

    public void showDialog() {
        progressDialog = ProgressDialog.show(this, "加载中", "......");
    }

    public void cancelDialog() {
        progressDialog.cancel();
    }
}
