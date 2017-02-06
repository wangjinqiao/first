package com.example.myapplication.view.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.view.MyBrowserActivity;
import com.example.myapplication.view.util.LogWrapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

import com.example.myapplication.view.adapter.NewsAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.example.myapplication.view.entity.NewsAll;
import com.example.myapplication.view.entity.NewsDetil;
import com.example.myapplication.view.presenter.NewsGetPresenter;
import com.example.myapplication.view.util.Constent;
import com.example.myapplication.view.xlistView.XListView;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class MainNewsFragment1 extends Fragment {
    @Bind(R.id.xlst_news_main)
    XListView mxlstNewsMain;

    MainActivity activity;//视图
    FragmentManager fragmentManager;
    /**
     * 数据源
     */
    public NewsAll<List<NewsDetil>> newsAll;

    @Bind(R.id.rdbtn_1)
    RadioButton rdbtn1;
    @Bind(R.id.rdbtn_2)
    RadioButton rdbtn2;
    @Bind(R.id.btn_right_main_more)
    Button btnRightMainMore;
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;

    @Bind(R.id.img_loading)
    ImageView imgLoading;

    /***
     * 接收从网络获取的新闻
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_news, container, false);
        ButterKnife.bind(this, view);

        activity = (MainActivity) this.getActivity();
        setAnimLoading();
        //获取新闻分类
//        getNewsSort();
        activity.modifyTitle("资讯");
        fragmentManager = activity.getSupportFragmentManager();
        return view;
    }

    //设置动画
    private void setAnimLoading() {
        mxlstNewsMain.setVisibility(View.GONE);
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.anim_loading_news);
        animation.setDuration(500);
        animation.setRepeatCount(10);
        imgLoading.startAnimation(animation);
    }

    /**
     * 设置下拉刷新
     */
    NewsAdapter adapter;

    private void initXlistView() {
        mxlstNewsMain.setPullLoadEnable(true);
        // 设置下拉时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");
        mxlstNewsMain.setRefreshTime(sdf.format(System.currentTimeMillis()));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getNewsData(Constent.PATH_GET_WAR);
        initXlistView();
        //单选组的事件
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rdbtn_1://军事
                        setAnimLoading();
                        getNewsData(Constent.PATH_GET_WAR);

                        break;
                    case R.id.rdbtn_2://社会
                        setAnimLoading();
                        getNewsData(Constent.PATH_GET_WORLD);

                        break;
                    case R.id.rdbtn_3://股票
                        setAnimLoading();
                        getNewsData(Constent.PATH_GET_3);

                        break;
                    case R.id.rdbtn_4://基金
                        setAnimLoading();
                        getNewsData(Constent.PATH_GET_4);

                        break;
                    case R.id.rdbtn_5://手机
                        setAnimLoading();
                        getNewsData(Constent.PATH_GET_5);

                        break;
                    case R.id.rdbtn_7://体育
                        setAnimLoading();
                        getNewsData(Constent.PATH_GET_7);

                        break;
                    case R.id.rdbtn_8://NBA
                        setAnimLoading();
                        getNewsData(Constent.PATH_GET_8);

                        break;
                }

            }
        });
        //下拉刷新事件
        mxlstNewsMain.setXListViewListener(new XListView.IXListViewListener() {

            /**
             * 上拉刷新
             */
            @Override
            public void onRefresh() {

                adapter.addNewData(newsAll.data);

                // 数据刷新结束
                mxlstNewsMain.stopRefresh();
            }

            /**
             * 下拉加载
             */
            @Override
            public void onLoadMore() {
                // 加载更多数据 // TODO: 2016/12/29 0029  
                //  com.example.myapplication.com.example.myapplication.view.adapter.appendData(moreData);
                // 停止加载
                mxlstNewsMain.stopLoadMore();
            }
        });
        // 新闻列表子条目点击事件todo
        mxlstNewsMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position - 1;
                LogWrapper.e(" 新闻列表子条目点击事件", "position=" + position);
                NewsDetil newsDetil = newsAll.data.get(position);
                String httpAdress = newsDetil.link;
                //跳转
//                Intent intentToHttp = new Intent(Intent.ACTION_VIEW);
//                intentToHttp.setData(Uri.parse(httpAdress));
//                startActivity(intentToHttp);
                //自定义浏览器 String summary, String icon, String stamp, String title, int nid, String link,
                Intent intentToHttp = new Intent(activity, MyBrowserActivity.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("httpAdress", httpAdress);//网址 link
                bundle.putCharSequence("icon", newsDetil.icon);//icon
                bundle.putCharSequence("summary", newsDetil.summary);//summary
                bundle.putCharSequence("stamp", newsDetil.stamp);//stamp
                bundle.putCharSequence("title", newsDetil.title);//title
                bundle.putInt("nid", newsDetil.nid); //新闻的id

                intentToHttp.putExtra("bundle", bundle);
                startActivity(intentToHttp);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 访问网络
     */
    private void getNewsData(String path) {

        NewsGetPresenter newsGetPresenter = new NewsGetPresenter();
        newsGetPresenter.visitHttpForNews(activity, path, mListener, mErrorListener);

    }

    public Response.Listener<JSONObject> mListener = new Response.Listener<JSONObject>()

    {
        @Override
        public void onResponse(JSONObject jsonObject) {
            /** 解析数据*/
            Gson gson = new Gson();
            Type type = new TypeToken<NewsAll<List<NewsDetil>>>() {
            }.getType();
            newsAll = gson.fromJson(jsonObject.toString(), type);
            /**设置适配器*/
            if (newsAll.status == 0 && newsAll.message.equals("OK")) {

                adapter = new NewsAdapter(activity, newsAll.data);
                mxlstNewsMain.setAdapter(adapter);
                mxlstNewsMain.setVisibility(View.VISIBLE);
                imgLoading.clearAnimation();
                imgLoading.setVisibility(View.GONE);
            }
        }
    };


    public Response.ErrorListener mErrorListener = new Response.ErrorListener()

    {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(activity, "网络出错", Toast.LENGTH_LONG).show();
            imgLoading.clearAnimation();
            imgLoading.setVisibility(View.GONE);
        }
    };

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick(R.id.btn_right_main_more)
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_right_main_more:
                //分类
                fragmentManager.beginTransaction()
                        .replace(R.id.activity_main, new MainNewsFragment2()).commit();
                activity.modifyTitle("分类");
                break;
        }
    }

    //获取新闻分类 news_sort?ver=版本号&imei=手机标识符
    public void getNewsSort() {
        String sortPath = Constent.PATH_BASE + "/news_sort?ver=1&imei=1";
        new NewsGetPresenter().visitHttpForNews(activity, sortPath, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogWrapper.e("新闻分类", jsonObject.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(activity, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
