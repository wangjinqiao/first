package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.model.AsyncHttp;
import com.example.myapplication.view.MainView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainView view = new MainView(this);
        AsyncHttp asyncHttp = new AsyncHttp();
        view.getAsyncHttp(asyncHttp);
    }
}
