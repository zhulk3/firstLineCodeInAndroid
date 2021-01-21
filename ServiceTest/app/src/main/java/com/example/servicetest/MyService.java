package com.example.servicetest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {
  private static final String TAG = "MyService";
  private DownloadBinder mBinder = new DownloadBinder();

  class DownloadBinder extends Binder {
    private static final String TAG = "DownloadBinder";

    public void startDownload() {
      Log.d(TAG, "startDownload: ");
    }

    public int getProgress() {
      Log.d(TAG, "getProgress: ");
      return 0;
    }

  }

  public MyService() {
  }

  @Override
  public IBinder onBind(Intent intent) {
    return mBinder;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Log.d(TAG, "onCreate: ");
    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    if (Build.VERSION.SDK_INT >= 26) {
      NotificationChannel notificationChannel =
          new NotificationChannel("default", "默认通知", NotificationManager.IMPORTANCE_LOW);
      manager.createNotificationChannel(notificationChannel);
    }

    Intent intent = new Intent(this, MainActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
    Notification notification = new NotificationCompat.Builder(this, "default")
        .setContentTitle("Title")
        .setContentText("hello,world")
        .setSmallIcon(R.mipmap.ic_launcher)
        .setWhen(System.currentTimeMillis())
        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
        .setContentIntent(pendingIntent)
        .build();
    startForeground(1, notification);

  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(TAG, "onStartCommand: ");
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy: ");
  }
}
