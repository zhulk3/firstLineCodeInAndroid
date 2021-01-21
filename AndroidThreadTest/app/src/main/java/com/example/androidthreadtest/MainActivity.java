package com.example.androidthreadtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  public static final int UPDATE_TEXT = 1;

  private static final String TAG = "MainActivity";

  public Handler mHandler = new Handler() {
    @Override
    public void handleMessage(@NonNull Message msg) {
      switch (msg.what) {
        case UPDATE_TEXT:
          mTextView.setText("zhu");
      }
    }
  };

  private TextView mTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button changeText = findViewById(R.id.change_text);
    mTextView = findViewById(R.id.text_view);
    changeText.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.change_text:
//        new Thread(new Runnable() {
//          @Override
//          public void run() {
//            try {
//              Thread.sleep(10000);
//            } catch (InterruptedException e) {
//              e.printStackTrace();
//            }
//            mTextView.setText("longkai");
//          }
//        }).start();
        Message message = new Message();
        message.what = UPDATE_TEXT;
        mHandler.sendMessage(message);
        break;
      default:
        break;
    }
  }
}