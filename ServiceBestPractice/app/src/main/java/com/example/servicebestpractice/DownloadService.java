package com.example.servicebestpractice;

import java.io.File;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class DownloadService extends Service {
  private DownloadTask mDownloadTask;
  private String downloadUrl;
  private DownloadBinder mDownloadBinder = new DownloadBinder();
  private static final String NOTIFICATION_ID = "com.servicebestpractice.DownloadService";
  private static final String NOTIFICATION_NAME = "com.servicebestpractice.DownloadService";
  private DownloadListener mListener = new DownloadListener() {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onProgress(int progress) {
      getNotificationManager().notify(1, getNotification("Downloading", progress));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onSuccess() {
      mDownloadTask = null;
      stopForeground(true);
      getNotificationManager().notify(1, getNotification("Download Success", -1));
      Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaused() {
      mDownloadTask = null;
      Toast.makeText(DownloadService.this, "Paused", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onFailed() {
      stopForeground(true);
      getNotificationManager().notify(1, getNotification("Download Failed", -1));
      Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCanceled() {
      mDownloadTask = null;
      stopForeground(true);
      Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
    }
  };

  public DownloadService() {
  }


  @Override
  public IBinder onBind(Intent intent) {
    return mDownloadBinder;
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private Notification getNotification(String title, int progress) {
    Intent intent = new Intent(this, MainActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
    builder.setContentIntent(pendingIntent);
    builder.setContentTitle(title);
    builder.setSmallIcon(R.mipmap.ic_launcher);
    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
    builder.setWhen(System.currentTimeMillis());
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      builder.setChannelId(NOTIFICATION_ID);
    }
    if (progress > 0) {
      builder.setContentText(progress + "%");
      builder.setProgress(100, progress, false);
    }
    return builder.build();
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private NotificationManager getNotificationManager() {
    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    NotificationChannel notificationChannel = null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      notificationChannel = new NotificationChannel(NOTIFICATION_ID,NOTIFICATION_NAME,
          NotificationManager.IMPORTANCE_DEFAULT);
      manager.createNotificationChannel(notificationChannel);
    }
    return manager;
  }

  class DownloadBinder extends Binder {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startDownload(String url) {
      if (mDownloadTask == null) {
        downloadUrl = url;
        mDownloadTask = new DownloadTask(mListener);
        mDownloadTask.execute(downloadUrl);
        startForeground(1, getNotification("Downloading...", 0));
        Toast.makeText(DownloadService.this, "Downloading...", Toast.LENGTH_SHORT).show();
      }
    }

    public void pauseDownload() {
      if (mDownloadTask != null) {
        mDownloadTask.pauseDownload();
      }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cancelDownload() {
      if (mDownloadTask != null) {
        mDownloadTask.cancelDownload();
      } else {
        if (downloadUrl != null) {
          String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
          String directory =
              Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                  .getPath();
          File file = new File(directory+ fileName);
          if (file.exists()) {
            file.delete();
          }
          getNotificationManager().cancel(1);
          stopForeground(true);
          Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
        }
      }
    }

  }

}
