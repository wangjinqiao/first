package com.example.myapplication.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.view.db.Sqldatebase;
import com.example.myapplication.view.entity.CommentNum;
import com.example.myapplication.view.entity.NewsDetil;
import com.example.myapplication.view.presenter.NewsGetPresenter;
import com.example.myapplication.view.util.Constent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 自定义浏览器
 */
public class MyBrowserActivity extends AppCompatActivity {

    @Bind(R.id.img_titlebar_left)
    ImageView imgTitlebarLeft;
    @Bind(R.id.txt_title)
    TextView txtTitle;
    @Bind(R.id.img_title_right)
    ImageView imgTitleRight;
    @Bind(R.id.txt_title_follow_num)
    TextView txtTitleFollowNum;

    @Bind(R.id.web_content)
    WebView webContent;

    Button btnJoinCollection;
    PopupWindow popupWindow;

    String httpAdress;//网址
    String icon;//icon
    String summary;//summary
    String stamp;//stamp
    String title;//title
    int nid;//新闻的id
    @Bind(R.id.pgb_web)
    ProgressBar pgbWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_browser);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        httpAdress = bundle.getString("httpAdress");//网址 link
        icon = bundle.getString("icon");//icon
        summary = bundle.getString("summary");//summary
        stamp = bundle.getString("stamp");// stamp
        title = bundle.getString("title");// title
        nid = bundle.getInt("nid");//新闻的id

        ButterKnife.bind(this);
        //设置评论数
        setCommentNum();

        setWebView();
        btnJoinCollection = new Button(MyBrowserActivity.this);
        btnJoinCollection.setText("加入收藏");
        btnJoinCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinCollection();
                //隐藏
                popupWindow.dismiss();
            }
        });
    }


    /**
     * 设置网页内容
     */
    public void setWebView() {
        webContent.loadUrl(httpAdress);
        WebSettings settings = webContent.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        webContent.setWebChromeClient(new WebChromeClient() {

            //进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pgbWeb.setProgress(newProgress);
            }
        });

        webContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pgbWeb.setProgress(0);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pgbWeb.setVisibility(View.GONE);
            }
        });
    }


    boolean isclicked = false;//是否点击过

    @OnClick({R.id.img_titlebar_left, R.id.img_title_right, R.id.txt_title_follow_num})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_titlebar_left:
                //返回主界面
                Intent intentBack = new Intent(MyBrowserActivity.this, MainActivity.class);
                startActivity(intentBack);
                break;
            case R.id.img_title_right:
                //加入收藏
                if (!isclicked) {
                    isclicked = !isclicked;
                    //显示

                    popupWindow = new PopupWindow(btnJoinCollection, 100, 60);
                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.showAsDropDown(imgTitleRight);
                } else {
                    isclicked = !isclicked;
                    //隐藏
                    popupWindow.dismiss();
                }
                break;
            case R.id.txt_title_follow_num:
                //跟帖 跳转到评论页面
                Intent intent = new Intent(MyBrowserActivity.this, CommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("nid", nid);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
                break;
        }
    }

    /**
     * 加入收藏 数据库
     */
    public void joinCollection() {
        Sqldatebase sqldatebase = Sqldatebase.getSqldatebase(this);
        //是否已经收藏
        boolean isCollected = sqldatebase.searchNid(nid);
        if (!isCollected) {
            //加入收藏
            sqldatebase.addNews(new NewsDetil(summary, icon, stamp, title, nid, httpAdress, 0));

            Toast.makeText(this, "收藏成功！\n在主界面左面侧滑菜单收藏中查看", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "已经收藏过了，\n在主界面左面侧滑菜单收藏中查看", Toast.LENGTH_SHORT).show();
        }

    }

    //设置评论数
    public void setCommentNum() {
        String commentNumPath = Constent.COMMENT_NUM_PATH + nid;
        new NewsGetPresenter().visitHttpForNews(this, commentNumPath, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                CommentNum commentNum = gson.fromJson(jsonObject.toString(), new TypeToken<CommentNum>() {
                }.getType());
                txtTitleFollowNum.setText("跟帖：" + commentNum.data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MyBrowserActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
