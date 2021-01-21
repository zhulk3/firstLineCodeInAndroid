package com.example.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {
  private static final String TAG = "MyIntentService";
  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   *
   * @param name Used to name the worker thread, important only for debugging.
   */
  public MyIntentService(String name) {
    super(name);
    Log.d(TAG, "MyIntentService: ");
  }

  public MyIntentService(){
    super("hello");
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    Log.d(TAG, "onHandleIntent: "+Thread.currentThread().getId());
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy: ");
  }
}
