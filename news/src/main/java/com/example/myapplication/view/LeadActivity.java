package com.example.myapplication.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.view.adapter.LeadAdapter;
import com.example.myapplication.view.db.SharedFile;
import com.example.myapplication.view.util.Constent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LeadActivity extends AppCompatActivity {

    @Bind(R.id.vpg_lead)
    ViewPager vpgLead;
    @Bind(R.id.txt_lead)
    TextView txtLead;
    //共享文件
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Bind(R.id.rdbtn_dian1)
    RadioButton rdbtnDian1;
    @Bind(R.id.rdbtn_dian2)
    RadioButton rdbtnDian2;
    @Bind(R.id.rdbtn_dian3)
    RadioButton rdbtnDian3;
    @Bind(R.id.rdbtn_dian4)
    RadioButton rdbtnDian4;
    @Bind(R.id.radio_group_dian)
    RadioGroup radioGroupDian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断是否第一次进入
        if (isFirst()) {
            //加载引导界面
            joinLead();
        } else {
            Intent intent = new Intent(LeadActivity.this, LogoActivity.class);
            startActivity(intent);
            finish();
        }


    }

    //加载引导界面
    protected void joinLead() {
        editor.putBoolean(Constent.IS_FIRST_KEY, false);
        editor.commit();
        setContentView(R.layout.activity_lead);
        ButterKnife.bind(this);
        initVpg();
        initEvent();
    }

    /**
     * 设置适配器
     */
    private void initVpg() {
        LeadAdapter leadAdapter = new LeadAdapter(this);
        vpgLead.setAdapter(leadAdapter);
    }

    public void initEvent() {
        //单选组选择事件
        radioGroupDian.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdbtn_dian1:
                        vpgLead.setCurrentItem(0);
                        break;
                    case R.id.rdbtn_dian2:
                        vpgLead.setCurrentItem(1);
                        break;
                    case R.id.rdbtn_dian3:
                        vpgLead.setCurrentItem(2);
                        break;
                    case R.id.rdbtn_dian4:
                        vpgLead.setCurrentItem(3);
                        break;
                }
            }
        });
        /**
         * ViewPager的监听事件
         */
        vpgLead.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    //设置跳过按钮可见
                    txtLead.setVisibility(View.VISIBLE);
                } else {
                    //设置跳过按钮不可见
                    txtLead.setVisibility(View.GONE);
                }
                switch (position) {
                    case 0:
                        rdbtnDian1.setChecked(true);
                        break;
                    case 1:
                        rdbtnDian2.setChecked(true);
                        break;
                    case 2:
                        rdbtnDian3.setChecked(true);
                        break;
                    case 3:
                        rdbtnDian4.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 跳过引导页面
     */

    @OnClick(R.id.txt_lead)
    public void onClick() {
        Intent intent = new Intent(LeadActivity.this, LogoActivity.class);
        startActivity(intent);
        finish();
    }

    //判断是否第一次进入
    public boolean isFirst() {
        SharedFile.initSharedPre(this);
        sharedPreferences = this.getSharedPreferences(Constent.NAME_SHSRED_PREFERENCE, 0);
        editor = sharedPreferences.edit();
        boolean isfirst = sharedPreferences.getBoolean(Constent.IS_FIRST_KEY, true);
        return isfirst;
    }
}
