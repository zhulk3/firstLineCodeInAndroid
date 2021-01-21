package com.example.servicetest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private MyService.DownloadBinder mDownloadBinder;

  private ServiceConnection mServiceConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      mDownloadBinder = (MyService.DownloadBinder) service;
      mDownloadBinder.startDownload();
      mDownloadBinder.getProgress();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button startService = findViewById(R.id.start_service);
    Button stopService = findViewById(R.id.stop_service);
    startService.setOnClickListener(this);
    stopService.setOnClickListener(this);
    Button bindService = findViewById(R.id.bind_service);
    Button unBindService = findViewById(R.id.unBind_service);
    bindService.setOnClickListener(this);
    unBindService.setOnClickListener(this);
    Button startIntentService = findViewById(R.id.start_intent_service);
    startIntentService.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.start_service:
        Intent startIntent = new Intent(this, MyService.class);
        startService(startIntent);
        break;
      case R.id.stop_service:
        Intent stopIntent = new Intent(this, MyService.class);
        stopService(stopIntent);
        break;
      case R.id.bind_service:
        Intent bindService = new Intent(this, MyService.class);
        bindService(bindService, mServiceConnection, BIND_AUTO_CREATE);
        break;
      case R.id.unBind_service:
        unbindService(mServiceConnection);
        break;
      case R.id.start_intent_service:
        Intent intentService = new Intent(this, MyIntentService.class);
        startService(intentService);
      default:
        break;
    }
  }
}