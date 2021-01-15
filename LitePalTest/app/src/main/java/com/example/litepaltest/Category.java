package com.example.litepaltest;

import org.litepal.crud.LitePalSupport;

public class Category extends LitePalSupport {
  private int id;
  private String categoryName;
  private String categoryCode;

  public int getId() {
    return id;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public String getCategoryCode() {
    return categoryCode;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public void setCategoryCode(String categoryCode) {
    this.categoryCode = categoryCode;
  }
}
