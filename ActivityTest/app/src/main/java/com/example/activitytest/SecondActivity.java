package com.example.activitytest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends BaseActivity {

  private static final String TAG = "SecondActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Activitycollector.sActivities.add(this);
    Log.d(TAG, "Task id is "+getTaskId());
    setContentView(R.layout.activity_second);
    Button button2=(Button)findViewById(R.id.button_2);
    button2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(SecondActivity.this,Third_Activity.class);
        startActivity(intent);
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy");
    Activitycollector.removeActivity(this);
  }
}