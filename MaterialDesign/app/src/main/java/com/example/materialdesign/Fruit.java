package com.example.materialdesign;

import android.os.Parcel;
import android.os.Parcelable;

public class Fruit implements Parcelable {
  private String name;
  private int image;

  public Fruit(){}

  public Fruit(String name, int image) {
    this.name = name;
    this.image = image;
  }

  protected Fruit(Parcel in) {
    name = in.readString();
    image = in.readInt();
  }

  public static final Creator<Fruit> CREATOR = new Creator<Fruit>() {
    @Override
    public Fruit createFromParcel(Parcel in) {
      Fruit fruit = new Fruit();
      fruit.name = in.readString();
      fruit.image = in.readInt();
      return fruit;
    }

    @Override
    public Fruit[] newArray(int size) {
      return new Fruit[size];
    }
  };

  public String getName() {
    return name;
  }

  public int getImage() {
    return image;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setImage(int image) {
    this.image = image;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
    dest.writeInt(image);
  }
}
