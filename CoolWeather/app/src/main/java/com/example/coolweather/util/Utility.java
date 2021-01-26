package com.example.coolweather.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.example.coolweather.db.City;
import com.example.coolweather.db.Country;
import com.example.coolweather.db.Province;
import com.example.coolweather.gson.Weather;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class Utility {
  private static final String TAG = "Utility";
  /**
   * 用于处理全国省份数据并进行持久化存储
   *
   * @param response 全国省份数据
   * @return 当处理成功则返回true，否则返回false
   */
  public static boolean handleProvinceData(String response) {
    if (!TextUtils.isEmpty(response)) {
      try {

        JSONArray allProvinces = new JSONArray(response);
        Log.d(TAG, "handleProvinceData: "+response);
        for (int i = 0; i < allProvinces.length(); i++) {
          JSONObject jsonObject = allProvinces.getJSONObject(i);
          Province province = new Province();
          province.setProvinceCode(jsonObject.getInt("id"));
          province.setProvinceName(jsonObject.getString("name"));
          province.save();
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  public static boolean handleCityData(String cityResponse, int provinceCode) {
    if (!TextUtils.isEmpty(cityResponse)) {
      try {
        JSONArray jsonArray = new JSONArray(cityResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject jsonObject = jsonArray.getJSONObject(i);
          City city = new City();
          city.setCityCode(jsonObject.getInt("id"));
          city.setCityName(jsonObject.getString("name"));
          city.setProvinceId(provinceCode);
          city.save();

        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return true;
    }
    return false;
  }

  public static boolean handleCountryData(String countryResponse, int cityCode) {
    if (!TextUtils.isEmpty(countryResponse)) {
      try {
        JSONArray jsonArray = new JSONArray(countryResponse);
        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject jsonObject = jsonArray.getJSONObject(i);
          Country country = new Country();
          country.setCountryCode(jsonObject.getInt("id"));
          country.setCountryName(jsonObject.getString("name"));
          country.setWeatherId(jsonObject.getString("weather_id"));
          country.setCityId(cityCode);
          country.save();
        }
        return true;
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  public static Weather handleWeatherResponse(String response){
    try {
      JSONObject jsonObject = new JSONObject(response);
      JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
      String weatherContent = jsonArray.getJSONObject(0).toString();
      return new Gson().fromJson(weatherContent,Weather.class);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
