package com.example.servicebestpractice;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private DownloadService.DownloadBinder mDownloadService;

  ServiceConnection mServiceConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      mDownloadService = (DownloadService.DownloadBinder) service;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button startDownload = findViewById(R.id.start_download);
    Button stopDownload = findViewById(R.id.pause_download);
    Button cancelDownload = findViewById(R.id.cancel_download);
    startDownload.setOnClickListener(this);
    stopDownload.setOnClickListener(this);
    cancelDownload.setOnClickListener(this);
    Intent intent = new Intent(this, DownloadService.class);
    bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    if (ContextCompat
        .checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
        PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(MainActivity.this,
          new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    }
  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.start_download:
        String url =
            "https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
        mDownloadService.startDownload(url);
        break;
      case R.id.pause_download:
        mDownloadService.pauseDownload();
        break;
      case R.id.cancel_download:
        mDownloadService.cancelDownload();
        break;
      default:
        break;
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case 1:
        if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
          Toast.makeText(this, "You denied", Toast.LENGTH_SHORT).show();
          finish();
        }
        break;
      default:
        break;
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    unbindService(mServiceConnection);
  }
}