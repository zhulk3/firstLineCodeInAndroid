package com.example.recyclerviewtest;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  private List<Fruit> mFruitList = new ArrayList<Fruit>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initData();
    FruitAdapter adapter = new FruitAdapter(mFruitList);
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//    LinearLayoutManager layoutManager= new LinearLayoutManager(this);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
  }

  private void initData() {
    for (int i = 0; i < 6; i++) {
      Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
      mFruitList.add(apple);
      Fruit banana = new Fruit("Banana", R.drawable.banana_pic);
      mFruitList.add(banana);
      Fruit watermelon = new Fruit("WaterMelon", R.drawable.watermelon_pic);
      mFruitList.add(watermelon);
      Fruit pear = new Fruit("Pear", R.drawable.pear_pic);
      mFruitList.add(pear);
      Fruit grape = new Fruit("Grape", R.drawable.grape_pic);
      mFruitList.add(grape);
      Fruit pineapple = new Fruit("Pineapple", R.drawable.pineapple_pic);
      mFruitList.add(pineapple);
      Fruit strawberry = new Fruit("Strawberry", R.drawable.strawberry_pic);
      mFruitList.add(strawberry);
      Fruit cherry = new Fruit("Cherry", R.drawable.cherry_pic);
      mFruitList.add(pineapple);
      Fruit mango = new Fruit("mango", R.drawable.mango_pic);
      mFruitList.add(mango);
    }
  }
}