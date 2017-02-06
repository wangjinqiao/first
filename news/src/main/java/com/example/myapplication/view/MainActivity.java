package com.example.myapplication.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.zx.slidingmenu.SlidingMenu;

import com.example.myapplication.view.fragments.LeftMenuFragment;
import com.example.myapplication.view.fragments.MainNewsFragment1;
import com.example.myapplication.view.fragments.RightMenuFragmentlogin1;
import com.example.myapplication.view.util.LogWrapper;

public class MainActivity extends BaseActivity {
    FragmentManager fragmentManager;

    @Bind(R.id.img_titlebar_left)
    ImageView imgTitlebarLeft;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.img_titlebar_right)
    ImageView imgTitlebarRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        fragmentManager = this.getSupportFragmentManager();
        initContent();
        initSlidMenu();
    }

    /**
     * 设置内容
     */
    private void initContent() {
        fragmentManager.beginTransaction().add(R.id.activity_main, new MainNewsFragment1()).commit();
        modifyTitle(R.mipmap.ic_title_home_default, "资讯");
    }

    /**
     * 设置侧滑
     */
    SlidingMenu slidingMenu;

    private void initSlidMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.slidingmenu_left);
        fragmentManager.beginTransaction().add(R.id.container_left_menu, new LeftMenuFragment()).commit();
        slidingMenu.setSecondaryMenu(R.layout.slidingmenu_right);
        fragmentManager.beginTransaction().add(R.id.container_right_menu, new RightMenuFragmentlogin1()).commit();
        slidingMenu.setBehindOffset(100);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
    }


    /**
     * 设置标题的点击事件
     *
     * @param view
     */

    @OnClick({R.id.img_titlebar_left, R.id.img_titlebar_right})
    public void onClick(View view) {
        switch (view.getId()) {
//            显示侧滑菜单
            case R.id.img_titlebar_left:
                slidingMenu.showMenu();
                break;
            case R.id.img_titlebar_right:
                slidingMenu.showSecondaryMenu();
                break;
        }
    }

    /**
     * 显示主界面
     */
    public void showMainContent() {
        slidingMenu.showContent();
    }

    /**
     * 修改标题
     */
    public void modifyTitle(String title) {
        txtTitle.setText(title);
    }

    /**
     * 修改标题，以及左边图片
     */
    public void modifyTitle(int resId, String title) {
        txtTitle.setText(title);
        imgTitlebarLeft.setImageResource(resId);
    }

    //标题 左边点击事件
    public void titleLeftEvent() {

        imgTitlebarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTitle.getText().equals("资讯")) {
                    slidingMenu.showMenu();
                } else {
//                    设置为返回键
                    fragmentManager.beginTransaction().replace(R.id.activity_main, new MainNewsFragment1()).commit();
                    modifyTitle(R.mipmap.ic_title_home_default, "资讯");
                }
            }
        });
    }

    //两次点击返回键的间隔时间
    private long exitTime = 0;

    /**
     * 监听返回键
     */
    @Override
    public void onBackPressed() {
        if (slidingMenu.isMenuShowing()) {
            slidingMenu.showContent();
        } else {
            //实现再按一次退出
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                LogWrapper.e("退出", "--------------------");
                System.exit(0);
            }
        }

    }


}
