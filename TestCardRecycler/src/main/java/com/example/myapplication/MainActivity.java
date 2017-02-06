package com.example.myapplication;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    int[] imgs = {R.mipmap.bk2, R.mipmap.c3, R.mipmap.c4,
            R.mipmap.e3, R.mipmap.e4, R.mipmap.e7
            , R.mipmap.e41, R.mipmap.f1, R.mipmap.gr1,
            R.mipmap.hy, R.mipmap.i6, R.mipmap.sb1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) this.findViewById(R.id.rcc_main);
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 20;
                outRect.top = 20;
                outRect.left = 20;
                outRect.right = 20;

            }
        });
        MyRccAdapter myRccAdapter = new MyRccAdapter(imgs, this);
        recyclerView.setAdapter(myRccAdapter);
    }
}
