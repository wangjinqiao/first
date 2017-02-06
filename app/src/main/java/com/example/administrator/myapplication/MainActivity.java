package com.example.administrator.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.administrator.myapplication.LeftFrag;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import edu.zx.slidingmenu.SlidingMenu;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    String path = "http://192.168.199.232:8080/Login/servlet/LoginServlet?username=111&pwd=bbb";
    String path1 = "http://192.168.199.232:8080/Login/servlet/LoginServlet";
    String pathimage = "http://192.168.199.232:8080/Login/images/f1.jpg";
    ImageView mimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        addSlidingMenu();
        btnClickEvent();
    }

    private void btnClickEvent() {
        //left

        //right

    }

    /**
     * 设置侧滑菜单
     */
    SlidingMenu slidingMenu;

    private void addSlidingMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.sliding_menu);
        FragmentManager supportFragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.slid_menu,new LeftFrag());
        fragmentTransaction.commit();
        slidingMenu.setSecondaryMenu(R.layout.sliding_menu2);
        FragmentTransaction fragmentTransaction1 = supportFragmentManager.beginTransaction();

        fragmentTransaction1.add(R.id.slid_menu2,new RightFrag());
        fragmentTransaction1.commit();

        slidingMenu.setBehindOffset(100);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
    }

    /**
     * 返回键
     */
    @Override
    public void onBackPressed() {
        if (slidingMenu.isMenuShowing()) {
            slidingMenu.showContent();
        }

    }

    /**
     * 初始化视图
     */
    private void initView() {
        mimg = (ImageView) this.findViewById(R.id.img);


    }

    private void initEvent() {
        mimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginToActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 主页面按钮的点击事件
     *
     * @param view
     */
    public void clickShow(View view) {

        StringRequest postRequest = new StringRequest(Request.Method.GET, path, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                Log.e("onResponse", s);
                Gson gson = new Gson();
                TypeToken<List<Person>> t = new TypeToken<List<Person>>() {
                };
                List<Person> pers = gson.fromJson(s, t.getType());
                for (Person per : pers) {
                    Log.e("person", per + "");
                }
                Log.e("toJson", gson.toJson(pers));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        AsyncHttp.requestHttp(this, postRequest);
    }

    private void getImageTest() {
        //获取请求队列
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //新建请求
        JSONObject obj = new JSONObject();
        try {
            obj.put("username", "111");
            obj.put("pwd", "222");
        } catch (JSONException e) {

        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, path, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this, Thread.currentThread().getName() + " \n " + jsonObject.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //图片   Bitmap.Config.ARGB_8888:图片清晰度
        ImageRequest request1 = new ImageRequest(pathimage, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                mimg.setImageBitmap(bitmap);
                //保存图片到SD卡
                File f = Environment.getExternalStorageDirectory();
                File file = new File(f, "f1.jpg");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                } catch (FileNotFoundException e) {

                }
                Toast.makeText(MainActivity.this, Thread.currentThread().getName() + " \n ", Toast.LENGTH_SHORT).show();

            }
        }, 200, 300, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //将请求添加到请求队列
        requestQueue.add(request);
        requestQueue.add(request1);
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 侧滑菜单按钮的点击事件
     */

}
