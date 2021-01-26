package com.example.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
  /**
   *
   * @param url 请求地址
   * @param callback 处理请求返回的回调
   */
  public static void sendHttpRequest(String url, okhttp3.Callback callback) {
    OkHttpClient okHttpClient = new OkHttpClient();
    Request request = new Request.Builder().url(url).build();
    okHttpClient.newCall(request).enqueue(callback);
  }
}
