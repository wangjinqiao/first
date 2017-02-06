package com.example.myapplication.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myapplication.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QQActivity extends AppCompatActivity {

    @Bind(R.id.wbv_qq)
    WebView wbvQq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        String address = bundle.getString("address");
        setWebView(address);
    }

    /**
     * 设置网页内容
     */
    public void setWebView( String address) {
        wbvQq.loadUrl(address);
        WebSettings settings = wbvQq.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        wbvQq.setWebChromeClient(new WebChromeClient() {

            //进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

        wbvQq.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }
}
