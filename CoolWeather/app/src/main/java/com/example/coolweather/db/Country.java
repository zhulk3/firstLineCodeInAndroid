package com.example.coolweather.db;

import org.litepal.crud.LitePalSupport;

public class Country extends LitePalSupport {
  private String countryName;
  private int countryCode;
  private String weatherId;
  private int id;
  private int cityId;

  public void setCityId(int cityId) {
    this.cityId = cityId;
  }

  public int getCityId() {
    return cityId;
  }

  public String getCountryName() {
    return countryName;
  }

  public int getCountryCode() {
    return countryCode;
  }

  public String getWeatherId() {
    return weatherId;
  }

  public int getId() {
    return id;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public void setCountryCode(int countryCode) {
    this.countryCode = countryCode;
  }

  public void setWeatherId(String weatherId) {
    this.weatherId = weatherId;
  }

  public void setId(int id) {
    this.id = id;
  }
}
