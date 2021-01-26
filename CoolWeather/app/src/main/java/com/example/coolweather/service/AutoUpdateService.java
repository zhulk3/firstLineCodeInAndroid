package com.example.coolweather.service;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.coolweather.WeatherActivity;
import com.example.coolweather.gson.Weather;
import com.example.coolweather.util.HttpUtil;
import com.example.coolweather.util.Utility;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {
  public AutoUpdateService() {
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    updateWeatherInfo();
    updateBingPic();
    AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
    int time = 8*60*60*1000;
    long triggerAtTime = SystemClock.elapsedRealtime()+time;
    Intent in = new Intent(this,AutoUpdateService.class);//自启动
    PendingIntent pendingIntent = PendingIntent.getService(this,0,in,0);
    manager.cancel(pendingIntent);
    manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pendingIntent);
    return super.onStartCommand(intent,flags,startId);

  }

  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }
  private void updateBingPic(){
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
            PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
        editor.putString("bing_pic", responseImage);
        editor.apply();
      }
    });
  }

  private void updateWeatherInfo(){
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    String weatherString = preferences.getString("weather",null);
    if(weatherString!=null){
      Weather weather = Utility.handleWeatherResponse(weatherString);
      String weatherId = weather.basic.weatherId;
      String key = "&key=f548b0f38bca48a3a27886e438bf2408";
      String baseUrl = "http://guolin.tech/api/weather?cityid=";
      String url = baseUrl + weatherId + key;
      HttpUtil.sendHttpRequest(url, new Callback() {
        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
          e.printStackTrace();
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
          final String responseText = response.body().string();
          final Weather weather = Utility.handleWeatherResponse(responseText);
          if (weather != null && "ok".equals(weather.status)) {
            SharedPreferences.Editor editor =
                PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
            editor.putString("weather", responseText);
            editor.apply();
          }
        }
      });
    }
  }
}
