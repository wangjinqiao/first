package com.example.myapplication.view;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.presenter.ToVisitHttp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static android.R.attr.bitmap;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {
    protected EditText medtname;
    protected EditText medtpwd;
    protected ImageView mimg;
    protected Button mbtn;
    /**
     *
     */
   public Response.Listener<Bitmap> listener= new Response.Listener<Bitmap>() {
        @Override
        public void onResponse(Bitmap bitmap) {
            if (null!=bitmap){
                mimg.setImageBitmap(bitmap);
                File f=Environment.getExternalStorageDirectory();
                File file=new File(f,"f2.jpg");
                try {

                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }else{
                Toast.makeText(MainActivity.this,"onResponse = null",Toast.LENGTH_LONG).show();
            }

        }
    };
    public   Response.ErrorListener errorListener=  new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(MainActivity.this,volleyError.toString(),Toast.LENGTH_LONG).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initEvent() {
        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //访问网络
                ToVisitHttp toVisitHttp = new ToVisitHttp();
                toVisitHttp.toHttpNet(MainActivity.this);
            }
        });
    }

    private void initView() {
        medtname= (EditText) this.findViewById(R.id.edt_name);
        medtname= (EditText) this.findViewById(R.id.edt_pwd);
        mbtn= (Button) this.findViewById(R.id.btn);
        mimg= (ImageView) this.findViewById(R.id.img);
    }
}
