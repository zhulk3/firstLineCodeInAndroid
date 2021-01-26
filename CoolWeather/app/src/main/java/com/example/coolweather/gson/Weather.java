package com.example.coolweather.gson;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Weather {
  public String status;
  public Basic basic;
  public Aqi aqi;
  public Now now;
  public Suggestion suggestion;
  @SerializedName("daily_forecast")
  public List<Forecast> forecastList;
}
