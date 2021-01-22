package com.example.lbstest;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends AppCompatActivity {
  private LocationClient mLocationClient;
  private TextView myLocation;
  private MapView mMapView;
  private BaiduMap mBaiduMap;
  boolean mIsFirstShow = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SDKInitializer.initialize(getApplicationContext());
    setContentView(R.layout.activity_main);
    myLocation = findViewById(R.id.my_location);
    mMapView = findViewById(R.id.map_view);
    mBaiduMap = mMapView.getMap();
    mBaiduMap.setMyLocationEnabled(true);
    mLocationClient = new LocationClient(getApplicationContext());
    mLocationClient.registerLocationListener(new MyLocationListener());
    List<String> permissionList = new ArrayList<>();
    if (ContextCompat
        .checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
        PackageManager.PERMISSION_GRANTED) {
      permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
    }
    if (ContextCompat
        .checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) !=
        PackageManager.PERMISSION_GRANTED) {
      permissionList.add(Manifest.permission.READ_PHONE_STATE);
    }
    if (ContextCompat
        .checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
        PackageManager.PERMISSION_GRANTED) {
      permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }
    if (!permissionList.isEmpty()) {
      String[] permissions = permissionList.toArray(new String[permissionList.size()]);
      ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
    } else {
      requestLocation();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    mMapView.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mMapView.onPause();
  }

  private void requestLocation() {
    initLocation();
    mLocationClient.start();
  }

  private void navigateTo(BDLocation bdLocation){
    if(mIsFirstShow){
      LatLng latLng = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
      MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
      mBaiduMap.animateMapStatus(mapStatusUpdate);
      mapStatusUpdate = MapStatusUpdateFactory.zoomTo(16f);
      mBaiduMap.animateMapStatus(mapStatusUpdate);
      mIsFirstShow=false;
    }
    MyLocationData.Builder builder =new MyLocationData.Builder();
    builder.latitude(bdLocation.getLatitude());
    builder.longitude(bdLocation.getLongitude());
    MyLocationData myLocationData = builder.build();
    mBaiduMap.setMyLocationData(myLocationData);
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case 1:
        if (grantResults.length > 0) {
          for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
              Toast.makeText(this, "only grant all permissions can you use this program",
                  Toast.LENGTH_SHORT).show();
              finish();
              return;
            }
          }
          requestLocation();
        } else {
          Toast.makeText(this, "error happen", Toast.LENGTH_SHORT).show();
        }
        break;
      default:
        break;
    }
  }

  private void initLocation() {
    LocationClientOption option = new LocationClientOption();
    option.setScanSpan(5000);
    option.setIsNeedAddress(true);
    option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
    mLocationClient.setLocOption(option);
  }

  public class MyLocationListener implements BDLocationListener {
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
      StringBuilder currentPosition = new StringBuilder();
//      currentPosition.append("经度：").append(bdLocation.getLongitude()).append("\n");
//      currentPosition.append("纬度：").append(bdLocation.getLatitude()).append("\n");
//      currentPosition.append("定位方式：");
//      if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
//        currentPosition.append("GPS\n");
//      } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
//        currentPosition.append("网络\n");
//      }
//      currentPosition.append("国家：").append(bdLocation.getCountry()).append("\n");
//      currentPosition.append("省份：").append(bdLocation.getProvince()).append("\n");
//      currentPosition.append("城市：").append(bdLocation.getCity()).append("\n");
//      currentPosition.append("区：").append(bdLocation.getDistrict()).append("\n");
//      currentPosition.append("街道：").append(bdLocation.getStreet()).append("\n");
//      currentPosition.append("号：").append(bdLocation.getStreetNumber()).append("\n");
      currentPosition.append("地址：").append(bdLocation.getAddrStr());
      myLocation.setText(currentPosition);
      navigateTo(bdLocation);
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mLocationClient.stop();
    mBaiduMap.setMyLocationEnabled(false);
    mMapView.onDestroy();
  }
}