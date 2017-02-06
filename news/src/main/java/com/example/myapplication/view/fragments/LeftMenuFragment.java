package com.example.myapplication.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

import com.example.myapplication.view.adapter.LeftFragmentAdapter;
import com.example.myapplication.view.entity.TypeLeftFragmentConstant;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class LeftMenuFragment extends Fragment {
    @butterknife.Bind(R.id.lst_in_leftmenu_frafment)
    ListView lstInLeftmenuFrafment;
    /**
     * 数据源
     */
    int[] icons = {R.mipmap.biz_navigation_tab_news,
            R.mipmap.biz_navigation_tab_read,
            R.mipmap.biz_navigation_tab_local_news,
            R.mipmap.biz_navigation_tab_ties,
            R.mipmap.biz_navigation_tab_pics,
    };
    String[] types = {"新闻", "收藏", "本地", "跟帖", "图片"};
    String[] typeEnglishs = {"NEWS", "FAVORITE", "LOCAL", "COMMENT", "PHOTO"};
    List<TypeLeftFragmentConstant> datas = new ArrayList<>();
    MainActivity activity;

    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lst_for_leftmenu, container, false);
        butterknife.ButterKnife.bind(this, view);
        activity = (MainActivity) LeftMenuFragment.this.getActivity();
        fragmentManager = this.getActivity().getSupportFragmentManager();
        getData();
        LeftFragmentAdapter adapter = new LeftFragmentAdapter(getActivity(), datas);
        lstInLeftmenuFrafment.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /**
         * 子条目的点击事件
         */
        lstInLeftmenuFrafment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0://新闻
                        fragmentManager.beginTransaction().
                                replace(R.id.activity_main, new MainNewsFragment1()).commit();
                        activity.showMainContent();
                        break;
                    case 1://收藏
                        fragmentManager.beginTransaction().
                                replace(R.id.activity_main, new LeftFavoriteFragment()).commit();
                        activity.showMainContent();
                        activity.modifyTitle("收藏新闻");
                        break;
                    case 2://本地
                        Toast.makeText(LeftMenuFragment.this.getActivity(), "LOCAL", Toast.LENGTH_LONG).show();
                        break;
                    case 3://跟帖
                        Toast.makeText(LeftMenuFragment.this.getActivity(), "COMMENT", Toast.LENGTH_LONG).show();
                        break;
                    case 4://图片
                        Toast.makeText(LeftMenuFragment.this.getActivity(), "PHOTO", Toast.LENGTH_LONG).show();
                        break;

                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        butterknife.ButterKnife.unbind(this);
    }


    /**
     * 获取数据
     */
    public void getData() {
        for (int i = 0; i < icons.length; i++) {
            datas.add(new TypeLeftFragmentConstant(icons[i], types[i], typeEnglishs[i]));
        }
    }
}
