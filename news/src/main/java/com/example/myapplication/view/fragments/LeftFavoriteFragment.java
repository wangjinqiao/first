package com.example.myapplication.view.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.myapplication.R;
import com.example.myapplication.view.MainActivity;
import com.example.myapplication.view.MyBrowserActivity;
import com.example.myapplication.view.adapter.NewsAdapter;
import com.example.myapplication.view.db.Sqldatebase;
import com.example.myapplication.view.entity.NewsDetil;
import com.example.myapplication.view.util.LogWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/23 0023.
 */

public class LeftFavoriteFragment extends Fragment {
    @Bind(R.id.lst_collection_fragment)
    ListView lstCollectionFragment;

    MainActivity activity;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_favorite, container, false);
        activity = (MainActivity) this.getActivity();
        ButterKnife.bind(this, view);

        setNewsList();
        initEvent();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 设置新闻列表
     */
    List<NewsDetil> nwses;
    Sqldatebase sqldatebase;
    public void setNewsList() {
        //得到新闻内容
         sqldatebase = Sqldatebase.getSqldatebase(activity);
        nwses = sqldatebase.searchNid();
        //设置适配器

        lstCollectionFragment.setAdapter(new NewsAdapter(activity, nwses));
    }


    //设置子条目点击事件
    public void initEvent() {
        lstCollectionFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogWrapper.e(" 新闻列表子条目点击事件", "position=" + position);
                NewsDetil newsDetil = nwses.get(position);
                String httpAdress = newsDetil.link;
                //跳转
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
        lstCollectionFragment.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //取消收藏
              final  NewsDetil newsDetil = nwses.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setItems(new String[]{"取消收藏"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                sqldatebase.deleteNews(newsDetil.nid);
                                setNewsList();
                                break;
                        }
                    }
                });
                builder.show();
                return true;
            }
        });


    }
}
