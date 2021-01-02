package com.example.activitylifecycletest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

//  public void onSaveInstanceState(Bundle savedInstanceState) {
//    // Save the user's current game state
//    savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
//    savedInstanceState.putInt(STATE_LEVEL, mCurrentLevel);
//
//    // Always call the superclass so it can save the view hierarchy state
//    super.onSaveInstanceState(savedInstanceState);
//  }


  @Override
  public void onSaveInstanceState(@NonNull Bundle outState,
      @NonNull PersistableBundle outPersistentState) {
    super.onSaveInstanceState(outState, outPersistentState);
    String tempString = "something you just typed";
    outState.putString("data_key",tempString);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if(savedInstanceState!=null){
      Log.d(TAG, "onCreate: "+savedInstanceState.getString("data_key"));
    }
    Log.d(TAG, "onCreate");
    setContentView(R.layout.activity_main);
    Button dialog = (Button)findViewById(R.id.start_dialog_activity);
    Button normal = (Button)findViewById(R.id.start_normal_activity);
    dialog.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,DialogActivity.class);
        startActivity(intent);
      }
    });
    normal.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,NormalActivity.class);
        startActivity(intent);
      }
    });
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "onResume");
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    Log.d(TAG, "onRestart");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "onPause");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.d(TAG, "onStop");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy");
  }

  @Override
  protected void onStart() {
    super.onStart();
    Log.d(TAG, "onStart");
  }


}