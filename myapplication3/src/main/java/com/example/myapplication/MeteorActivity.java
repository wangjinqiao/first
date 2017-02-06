package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;



public class MeteorActivity extends Activity implements MeteorView.OnViewStopListener {

    private static final String TAG = MeteorActivity.class.getSimpleName();
    protected RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (RelativeLayout) findViewById(R.id.activity_main);
//        layout.setBackgroundResource(R.mipmap.about_bg);
        initLayout();
        setContentView(layout);
    }

    private void initLayout() {
        for (int i = 0; i < 10; i++) {

            MeteorView view = new MeteorView(this);
            view.setOnViewStopListener(this);
            layout.addView(view);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        layout.removeAllViews();
    }

    @Override
    public void viewStop(View view) {
        layout.removeView(view);
//        Log.e(TAG, "" + layout.getChildCount());
        if (layout.getChildCount() != 0) {
            MeteorView meteorView = new MeteorView(this);
            meteorView.setOnViewStopListener(this);
            layout.addView(meteorView);
        }
    }
}
