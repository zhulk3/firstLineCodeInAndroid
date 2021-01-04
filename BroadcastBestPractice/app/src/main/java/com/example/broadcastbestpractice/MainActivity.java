package com.example.broadcastbestpractice;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button forceOffline = (Button) findViewById(R.id.force_offline);
    forceOffline.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.d(TAG, "onClick: ");
        Intent intent = new Intent("com.example.broadcastbestpractice.FORCE_OFFLINE");
        sendBroadcast(intent);
      }
    });

  }
}