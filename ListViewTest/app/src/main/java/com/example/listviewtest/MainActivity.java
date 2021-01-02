package com.example.listviewtest;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//  private String[] data = {"Apple","Banana","WaterMelon","Pear","Grape","Pineapple","Strawberry","Cheery","Mango",
//      "Apple","Banana","WaterMelon","Pear","Grape","Pineapple","Strawberry","Cheery","Mango"};

  private List<Fruit> mFruitList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initData();
    FruitAdapter adapter=new FruitAdapter(MainActivity.this,R.layout.fruit_item,mFruitList);
    ListView listView = (ListView)findViewById(R.id.list_view);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fruit fruit = mFruitList.get(position);
        Toast.makeText(MainActivity.this,fruit.getName(),Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void initData(){
    for(int i=0;i<6;i++){
      Fruit apple = new Fruit("Apple",R.drawable.apple_pic);
      mFruitList.add(apple);
      Fruit banana = new Fruit("Banana",R.drawable.banana_pic);
      mFruitList.add(banana);
      Fruit watermelon = new Fruit("WaterMelon",R.drawable.watermelon_pic);
      mFruitList.add(watermelon);
      Fruit pear = new Fruit("Pear",R.drawable.pear_pic);
      mFruitList.add(pear);
      Fruit grape = new Fruit("Grape",R.drawable.grape_pic);
      mFruitList.add(grape);
      Fruit pineapple = new Fruit("Pineapple",R.drawable.pineapple_pic);
      mFruitList.add(pineapple);
      Fruit strawberry = new Fruit("Strawberry",R.drawable.strawberry_pic);
      mFruitList.add(strawberry);
      Fruit cherry = new Fruit("Cherry",R.drawable.cherry_pic);
      mFruitList.add(pineapple);
      Fruit mango = new Fruit("mango",R.drawable.mango_pic);
      mFruitList.add(mango);
    }

  }
}