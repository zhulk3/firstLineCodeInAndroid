package com.example.coolweather.gson;

import com.google.gson.annotations.SerializedName;

public class Forecast {
  public String date;

  @SerializedName("cond")
  public More more;

  @SerializedName("tmp")
  public Temperature temperature;

  public class More {
    @SerializedName("txt_d")
    public String info;
  }

  public class Temperature {
    @SerializedName("max")
    public String maxTmp;

    @SerializedName("min")
    public String minTmp;
  }
}
