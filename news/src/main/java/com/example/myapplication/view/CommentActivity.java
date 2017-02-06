package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.view.adapter.CommentAdapter;
import com.example.myapplication.view.db.SharedFile;
import com.example.myapplication.view.entity.CommentListInfo;
import com.example.myapplication.view.entity.NewsAll;
import com.example.myapplication.view.entity.NewsDetil;
import com.example.myapplication.view.entity.SendCommReponse;
import com.example.myapplication.view.presenter.NewsGetPresenter;
import com.example.myapplication.view.util.Constent;
import com.example.myapplication.view.util.LogWrapper;
import com.example.myapplication.view.xlistView.XListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 评论界面
 */
public class CommentActivity extends AppCompatActivity {
    String token;//用户令牌
    int nid;//新闻的id
    @Bind(R.id.img_comment_back)
    ImageView imgCommentBack;
    //评论列表
    @Bind(R.id.xlst_comment)

    XListView xlstComment;
    //编辑评论
    @Bind(R.id.edt_write_comment)
    EditText edtWriteComment;
    @Bind(R.id.img_send_comment)
    ImageView imgSendComment;
    //适配器
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        getToken();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        nid = bundle.getInt("nid");

        //获取并设置评论列表内容
        setCommentList();
        //设置下拉刷新
        setXlistFresh();
    }

    //设置下拉刷新
    public void setXlistFresh() {
        xlstComment.setPullLoadEnable(true);
        // 设置下拉时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH：mm：ss");
        xlstComment.setRefreshTime(sdf.format(System.currentTimeMillis()));

        //下拉刷新事件
        xlstComment.setXListViewListener(new XListView.IXListViewListener() {

            @Override
            public void onRefresh() {
                commentAdapter.addNewData(commentListInfo.data);
                // 数据刷新结束
                xlstComment.stopRefresh();
            }

            @Override
            public void onLoadMore() {
                // 加载更多数据 // TODO: 2016/12/29 0029
//            List<CommentListInfo.DataBean> moredata;
//            commentAdapter.appendData(moredata);
                //刷新列表
                setCommentList();
                LogWrapper.e("刷新列表","onLoadMore");
                // 停止加载
                xlstComment.stopLoadMore();
            }
        });
    }


    @OnClick({R.id.img_comment_back, R.id.img_send_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_comment_back:
                //返回
                this.finish();
                break;
            case R.id.img_send_comment:

                if (token.equals("0")) {
                    Toast.makeText(CommentActivity.this, "请先登录才能发表评论", Toast.LENGTH_SHORT).show();
                    break;
                }
                String comment = edtWriteComment.getText().toString().trim();
                if (comment.matches("\\s*")) {
                    Toast.makeText(CommentActivity.this, "没有评论内容", Toast.LENGTH_SHORT).show();
                } else {
                    //发表评论
                    sendComment(comment);

                }
                break;
        }
    }

    //得到用户令牌
    public void getToken() {
        //共享文件
        token = SharedFile.sharedPreferences.getString(Constent.TOKEN_KEY, "0");
    }

    //发表评论  拼接：&nid=新闻编号&token=用户令牌&imei=手机标识符&ctx=评论内容
    public void sendComment(String comment) {
        String commPath = Constent.SEND_COMMENT_PATH + "&nid=" + nid + "&token=" + token + "&imei=1&ctx=" + comment;
        new NewsGetPresenter().visitHttpForNews(this, commPath, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                SendCommReponse sendCommReponse = gson.fromJson(jsonObject.toString(), new TypeToken<SendCommReponse>() {
                }.getType());
                if (sendCommReponse != null && sendCommReponse.status == 0) {
                    Toast.makeText(CommentActivity.this, sendCommReponse.data.explain, Toast.LENGTH_SHORT).show();
                    edtWriteComment.setText("");
                    //刷新列表
                    setCommentList();
                } else {
                    //sendCommReponse.status = -1,-2, -3
                    Toast.makeText(CommentActivity.this, "状态异常 状态码 =" + sendCommReponse.status, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(CommentActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //获取评论列表内容
    CommentListInfo commentListInfo;

    public void setCommentList() {
        /**
         * 拼接：&nid=新闻id&type=1&stamp=yyyyMMdd&cid=评论id&dir=0&cnt=20
         */
        final String commentListPath = Constent.COMMENT_LIST_PATH + "&nid=" + nid +
                "&type=1&stamp=20151212&cid=10&dir=1&cnt=20";

        new NewsGetPresenter().visitHttpForNews(this, commentListPath, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogWrapper.e("评论列表  " + commentListPath, jsonObject.toString());
                Gson gson = new Gson();
                commentListInfo = gson.fromJson(jsonObject.toString(), new TypeToken<CommentListInfo>() {
                }.getType());
                if (commentListInfo != null && commentListInfo.status == 0) {
                    //设置适配器
                    setAdapterForXlst();

                } else {
                    //status = -1,-2, -3
                    Toast.makeText(CommentActivity.this, "状态异常 状态码 =" + commentListInfo.status, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(CommentActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapterForXlst() {
        //设置适配器
        commentAdapter = new CommentAdapter(CommentActivity.this, commentListInfo.data);
        xlstComment.setAdapter(commentAdapter);
    }
}
