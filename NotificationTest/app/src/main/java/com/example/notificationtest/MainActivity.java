package com.example.notificationtest;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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
    Button sendNotification = (Button) findViewById(R.id.send_notification);
    sendNotification.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,0,intent,0);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          NotificationChannel notificationChannel =
              new NotificationChannel("default", "默认通知", NotificationManager.IMPORTANCE_LOW);
          notificationChannel.enableLights(true);
          notificationChannel.enableVibration(true);
//          notificationChannel.setSound(new Uri.fromFile(new File("/system/media/audio/ringtones/Basic_tone.ogg")));
          manager.createNotificationChannel(notificationChannel);
          Log.d(TAG, "onClick: " + Build.VERSION.SDK_INT);
        }

        Notification notification =
            new NotificationCompat.Builder(MainActivity.this, "default").setContentTitle("Music")
                .setContentText("play a soft music")
                .setWhen(System.currentTimeMillis()+3000)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{0,1000,1000,1000})
                .setLights(Color.GREEN,1000,1000)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build();
        manager.notify(1, notification);
        Log.d(TAG, "onClick: " + "play a soft music");
      }
    });
  }
}