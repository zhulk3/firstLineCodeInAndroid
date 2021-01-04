package com.example.broadcastbestpractice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;

public class BaseActivity extends Activity {
  private static final String TAG = "BaseActivity";

  private ForceOfflineReceiver mForceOfflineReceiver;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityCollector.addActivity(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("com.example.broadcastbestpractice.FORCE_OFFLINE");
    mForceOfflineReceiver = new ForceOfflineReceiver();
    registerReceiver(mForceOfflineReceiver,intentFilter);
    Log.d(TAG, "onResume");
  }

  @Override
  protected void onPause() {
    super.onPause();
    if(mForceOfflineReceiver!=null){
      unregisterReceiver(mForceOfflineReceiver);
      mForceOfflineReceiver=null;
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ActivityCollector.removeActivity(this);
  }

  class ForceOfflineReceiver extends BroadcastReceiver{
    private static final String TAG = "ForceOfflineReceiver";
    @Override
    public void onReceive(final Context context, Intent intent) {
      Log.d(TAG, "onReceive");
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("Warning");
      builder.setMessage("You are force to be offline, please try to login again");
      builder.setCancelable(false);
      builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          ActivityCollector.finishAll();
          Intent intent1 = new Intent(context,LoginActivity.class);
          startActivity(intent1);
        }
      }).show();
    }
  }
}
