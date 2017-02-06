package com.example.myapplication.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LogoActivity extends AppCompatActivity {

    @Bind(R.id.img_logo)
    ImageView imgLogo;
    @Bind(R.id.activity_logo)
    RelativeLayout activityLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        ButterKnife.bind(this);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(activityLogo, "wh", 1, 0);
        objectAnimator.setDuration(2000);
        objectAnimator.setStartDelay(1000);
        objectAnimator.start();
        /**
         * 设置监听事件
         */
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                imgLogo.setAlpha(animatedValue);
                imgLogo.setScaleX(animatedValue);
                imgLogo.setScaleY(animatedValue);
                if (animatedValue == 0) {
                    Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
