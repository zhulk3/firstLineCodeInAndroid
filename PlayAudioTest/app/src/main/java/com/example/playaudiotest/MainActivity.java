package com.example.playaudiotest;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private MediaPlayer mMediaPlayer = new MediaPlayer();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button playButton = findViewById(R.id.play);
    Button pauseButton = findViewById(R.id.pause);
    Button stopButton = findViewById(R.id.stop);

    playButton.setOnClickListener(this);
    pauseButton.setOnClickListener(this);
    stopButton.setOnClickListener(this);

    if (ContextCompat
        .checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
        PackageManager.PERMISSION_GRANTED) {
      ActivityCompat
          .requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
    } else {
      initMediaPlayer();
    }
  }

  private void initMediaPlayer() {
    try {
      File file = new File(Environment.getExternalStorageDirectory(), "storage/emulated/0/at/music.mp3");
      mMediaPlayer.setDataSource(file.getPath());
      mMediaPlayer.prepare();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    switch (requestCode) {
      case 1:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          initMediaPlayer();
        } else {
          Toast.makeText(this, "You Denied", Toast.LENGTH_SHORT).show();
          finish();
        }
        break;
      default:
    }
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.play:
        if (!mMediaPlayer.isPlaying()) {
          mMediaPlayer.start();
        }
        break;
      case R.id.pause:
        if (mMediaPlayer.isPlaying()) {
          mMediaPlayer.pause();
        }
        break;
      case R.id.stop:
        if (mMediaPlayer.isPlaying()) {
          mMediaPlayer.stop();
        }
        break;
      default:
    }

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if(mMediaPlayer!=null){
      mMediaPlayer.stop();
      mMediaPlayer.release();
    }
  }
}