package com.example.activitytest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Third_Activity extends BaseActivity {
  private static final String TAG = "Third_Activity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Activitycollector.sActivities.add(this);
    Log.d(TAG, "Task id is "+getTaskId());
    setContentView(R.layout.activity_third_);
    Intent intent = getIntent();
    String data = intent.getStringExtra("extra_data");
    TextView textView = (TextView)findViewById(R.id.text);
    textView.setText(data);
    Button button = (Button)findViewById(R.id.button_3);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Activitycollector.removeAllActivities();
      }
    });

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Activitycollector.removeActivity(this);
  }
}