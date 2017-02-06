package com.example.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    TextureMapView textureMapView;
    BaiduMap baiduMap;
    LocationClient locationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mapManage();

    }

    private void initView() {
        textureMapView = (TextureMapView) this.findViewById(R.id.texture_map);
    }

    private void mapManage() {
        //获取地图控制器
        baiduMap = textureMapView.getMap();
        showWindow();
        showinfoWindow();
    }

    private void setmapType(int mapType) {
        //设置地图类型
        baiduMap.setMapType(mapType);
    }

    //添加显示窗口信息
    InfoWindow infoWindow;

    private void showWindow() {
        final Button button = new Button(this);
        button.setText("定位");
        button.setTextColor(Color.GREEN);
        button.setTextSize(40);

        infoWindow = new InfoWindow(button, new LatLng(39.56, 116.40), 0);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //允许定位
                baiduMap.setMyLocationEnabled(true);
                //初始化定位信息
                initLocation();

            }
        });

    }

    //初始化定位信息
    private void initLocation() {
        //定位选项
        LocationClientOption opt=new LocationClientOption();
        //高精度定位模式
        opt.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //设置定位坐标类型
        opt.setCoorType("bd09ll");

        //是否需要地址信息
        opt.setIsNeedAddress(true);
        //是否打开GPS
        opt.setOpenGps(true);
        //
        //
        



        locationClient=new LocationClient(MainActivity.this,opt);


        //注册定位监听事件
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //获取定位数据
                MyLocationData data=new MyLocationData.Builder()
                        .latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude())
                        .build();
                baiduMap.setMyLocationData(data);
                //更新地图状态

                MapStatusUpdate update= MapStatusUpdateFactory.
                        newLatLng(new LatLng(bdLocation.getLongitude(),bdLocation.getAltitude()));
                baiduMap.animateMapStatus(update);
            }
        });
        //开始定位
        locationClient.start();

    }

    //显示窗口
    private void showinfoWindow() {
        baiduMap.showInfoWindow(infoWindow);
    }

    //添加文字覆盖物
    private void addTextlay() {
        TextOptions textOptions = new TextOptions();
        textOptions.text("北京");
        textOptions.position(new LatLng(39.96, 116.40));
        textOptions.fontColor(Color.CYAN);
        textOptions.fontSize(40);
        baiduMap.addOverlay(textOptions);
    }

    //添加标记覆盖物
    private void addmakerlay() {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.attch1));
        markerOptions.alpha(0.5f);
        markerOptions.position(new LatLng(39.9, 116.3));
        markerOptions.title("marker");
        baiduMap.addOverlay(markerOptions);
    }

    //添加折线覆盖物
    private void addPolylinelay() {
        PolylineOptions polylineOptions = new PolylineOptions();
        List<LatLng> points = new ArrayList<>();
        LatLng point1 = new LatLng(39, 116);
        LatLng point2 = new LatLng(39.3, 116);
        LatLng point3 = new LatLng(39, 116.5);
        LatLng point4 = new LatLng(39.3, 116.5);
        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        polylineOptions.points(points);
        polylineOptions.color(Color.parseColor("#12ff23"));
        baiduMap.addOverlay(polylineOptions);
    }

    @Override
    protected void onPause() {
        super.onPause();
        textureMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        textureMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //结束定位
        locationClient.stop();
        //取消定位注册
        locationClient.unRegisterLocationListener(null);
        textureMapView.onDestroy();
    }
}
