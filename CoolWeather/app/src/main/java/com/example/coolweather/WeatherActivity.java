package com.example.coolweather;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coolweather.db.Province;
import com.example.coolweather.gson.Forecast;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.service.AutoUpdateService;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
  private TextView mCityTitle;
  private TextView mComfortText;
  private TextView mWashCarText;
  private TextView mSportText;
  private TextView mDegreeText;
  private TextView mWeatherInfo;
  private ScrollView mScrollView;
  private TextView mPm25Text;
  private TextView mAqiText;
  private LinearLayout mForecastLayout;
  private TextView mTitleUpdateTime;
  private ImageView mBackground;
  public SwipeRefreshLayout mSwipeRefreshLayout;
  public DrawerLayout mDrawerLayout;
  private Button navButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
//    if (Build.VERSION.SDK_INT >= 21) {
//      View decorView = getWindow().getDecorView();
//      decorView.setSystemUiVisibility(
//          View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//      getWindow().setStatusBarColor(Color.TRANSPARENT);
//    }
    setContentView(R.layout.activity_weather);
    mCityTitle = findViewById(R.id.title_city);
    mComfortText = findViewById(R.id.comfort_text);
    mWashCarText = findViewById(R.id.wash_car_text);
    mSportText = findViewById(R.id.sport_text);
    mDegreeText = findViewById(R.id.degree);
    mWeatherInfo = findViewById(R.id.weather_info);
    mScrollView = findViewById(R.id.weather_layout);
    mPm25Text = findViewById(R.id.pm25_text);
    mAqiText = findViewById(R.id.aqi_text);
    mForecastLayout = findViewById(R.id.forecast_layout);
    mTitleUpdateTime = findViewById(R.id.update_time);
    mBackground = findViewById(R.id.bing_image_pic);
    mSwipeRefreshLayout = findViewById(R.id.refresh_layout);
    mDrawerLayout= findViewById(R.id.drawer_layout);
    navButton = findViewById(R.id.nav_button);

    mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    String weatherString = preferences.getString("weather", null);
    final String weatherId;
    if (weatherString != null) {
      Weather weather = Utility.handleWeatherResponse(weatherString);
      weatherId = weather.basic.weatherId;
      showWeatherInfo(weather);
    } else {
      weatherId = getIntent().getStringExtra("weather_id");
      mScrollView.setVisibility(View.INVISIBLE);
      requestWeatherInfo(weatherId);
    }
    String bingPic = preferences.getString("bing_pic", null);
    if (bingPic != null) {
      Glide.with(getApplicationContext()).load(bingPic).into(mBackground);
    } else {
      loadBingPic();
    }
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        requestWeatherInfo(weatherId);
      }
    });
    navButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mDrawerLayout.openDrawer(GravityCompat.START);
      }
    });
  }

  public void requestWeatherInfo(String weather_id) {
    String key = "&key=f548b0f38bca48a3a27886e438bf2408";
    String baseUrl = "http://guolin.tech/api/weather?cityid=";
    String url = baseUrl + weather_id + key;
    HttpUtil.sendHttpRequest(url, new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        Toast.makeText(WeatherActivity.this, "获取天气失败", Toast.LENGTH_SHORT).show();
        mSwipeRefreshLayout.setRefreshing(false);
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        final String responseText = response.body().string();
        final Weather weather = Utility.handleWeatherResponse(responseText);
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            if (weather != null && "ok".equals(weather.status)) {
              SharedPreferences.Editor editor =
                  PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
              editor.putString("weather", responseText);
              editor.apply();
              showWeatherInfo(weather);
              Intent intent = new Intent(WeatherActivity.this, AutoUpdateService.class);
              startService(intent);
            } else {
              Toast.makeText(WeatherActivity.this, "获取天气失败", Toast.LENGTH_SHORT).show();
            }
          }
        });
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
    loadBingPic();
  }

  private void showWeatherInfo(Weather weather) {
    String cityName = weather.basic.cityName;
    String updateTime = weather.basic.update.updateTime;
    String degree = weather.now.temperature + "°C";
    String weatherInfo = weather.now.more.info;
    mCityTitle.setText(cityName);
    mTitleUpdateTime.setText(updateTime);
    mDegreeText.setText(degree);
    mWeatherInfo.setText(weatherInfo);

    mForecastLayout.removeAllViews();

    for (Forecast forecast : weather.forecastList) {
      View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, mForecastLayout, false);
      TextView dateText = view.findViewById(R.id.date_text);
      TextView infoText = view.findViewById(R.id.info_text);
      TextView maxText = view.findViewById(R.id.max_text);
      TextView minText = view.findViewById(R.id.min_text);
      dateText.setText(forecast.date);
      infoText.setText(forecast.more.info);
      maxText.setText(forecast.temperature.maxTmp);
      minText.setText(forecast.temperature.minTmp);
      mForecastLayout.addView(view);
    }
    if (weather.aqi != null) {
      mAqiText.setText(weather.aqi.city.aqi);
      mPm25Text.setText(weather.aqi.city.pm25);
    }
    String comfort = "舒适度：" + weather.suggestion.comfortable.info;
    String carWash = "洗车指数" + weather.suggestion.carWash.info;
    String sport = "运动建议" + weather.suggestion.sport.info;
    mComfortText.setText(comfort);
    mWashCarText.setText(carWash);
    mSportText.setText(sport);
    mScrollView.setVisibility(View.VISIBLE);
  }

  private void loadBingPic() {
    String url = "http://guolin.tech/api/bing_pic";
    HttpUtil.sendHttpRequest(url, new Callback() {
      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        e.printStackTrace();
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        final String responseImage = response.body().string();
        SharedPreferences.Editor editor =
            PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
        editor.putString("bing_pic", responseImage);
        editor.apply();
        ;
        runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Glide.with(getApplicationContext()).load(responseImage).into(mBackground);
          }
        });
      }
    });
  }
}