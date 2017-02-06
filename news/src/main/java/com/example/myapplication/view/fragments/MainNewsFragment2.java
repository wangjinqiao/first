package com.example.myapplication.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.myapplication.R;
import com.example.myapplication.view.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class MainNewsFragment2 extends Fragment {
    @Bind(R.id.rdbtn_1)
    RadioButton rdbtn1;
    @Bind(R.id.rdbtn_2)
    RadioButton rdbtn2;
    @Bind(R.id.rdbtn_3)
    RadioButton rdbtn3;
    @Bind(R.id.rdbtn_4)
    RadioButton rdbtn4;
    @Bind(R.id.lst_news_sort)
    ListView lstNewsSort;

    /**
     * 数据源
     */
    String[][] groups = {{"社会", "军事"},{"基金", "股票"},{"手机"},{"英超", "NBA"}};



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_2do, container, false);
        ButterKnife.bind(this, view);
        MainActivity activity= (MainActivity) this.getActivity();
        activity.modifyTitle(R.mipmap.back,"分类");
        activity.titleLeftEvent();
        setLst(0);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.rdbtn_1, R.id.rdbtn_2, R.id.rdbtn_3, R.id.rdbtn_4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rdbtn_1:
                setLst(0);
                break;
            case R.id.rdbtn_2:
                setLst(1);
                break;
            case R.id.rdbtn_3:
                setLst(2);
                break;
            case R.id.rdbtn_4:
                setLst(3);
                break;
        }
    }

    /**
     * 设置列表内容
     *
     * @param position
     */
    public void setLst(int position) {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_lst_sort, R.id.txt_lst_sort, groups[position]);
        lstNewsSort.setAdapter(stringArrayAdapter);
    }

}
