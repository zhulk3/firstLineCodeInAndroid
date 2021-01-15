package com.example.litepaltest;

import org.litepal.crud.LitePalSupport;

public class Book extends LitePalSupport {
  private int page;
  private double price;
  private String name;
  private String author;
  private int id;

  public int getPage() {
    return page;
  }

  public double getPrice() {
    return price;
  }

  public String getName() {
    return name;
  }

  public String getAuthor() {
    return author;
  }

  public int getId() {
    return id;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setId(int id) {
    this.id = id;
  }
}
