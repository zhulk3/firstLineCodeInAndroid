package com.example.sharedpreferencestest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  private Button mButton;
  private Button restoreData;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mButton = (Button)findViewById(R.id.save_data);
    restoreData = (Button)findViewById(R.id.restore_data);
    mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SharedPreferences.Editor editor = getSharedPreferences("data1",MODE_PRIVATE).edit();
//        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString("name","zhulongkai");
        editor.putInt("age",22);
        editor.putFloat("height",170.1f);
        editor.putBoolean("married",false);
        editor.apply();
      }
    });

    restoreData.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        SharedPreferences sharedPreferences = getSharedPreferences("data1",MODE_PRIVATE);
        String name = sharedPreferences.getString("name","");
        int age = sharedPreferences.getInt("age",0);
        float height = sharedPreferences.getFloat("height",0);
        Boolean married = sharedPreferences.getBoolean("married",false);
        Log.d(TAG, name);
        Log.d(TAG, String.valueOf(age));
        Log.d(TAG, String.valueOf(height));
        Log.d(TAG, String.valueOf(married));
      }
    });
  }
}