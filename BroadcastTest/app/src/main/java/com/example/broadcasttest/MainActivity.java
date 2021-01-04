package com.example.broadcasttest;

import static android.net.wifi.p2p.WifiP2pDevice.CONNECTED;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  private IntentFilter intentFilter;
//  private NetworkChangeReceiver mNetworkChangeReceiver;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
//    intentFilter = new IntentFilter();
//    intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//    mNetworkChangeReceiver = new NetworkChangeReceiver();
//    registerReceiver(mNetworkChangeReceiver, intentFilter);
    Button button = (Button)findViewById(R.id.button);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent("com.example.broadcasttest.MY_BROADCAST");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        sendBroadcast(intent);
        Log.d(TAG, "onClick: ");
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
//    unregisterReceiver(mNetworkChangeReceiver);
  }

//  class NetworkChangeReceiver extends BroadcastReceiver {
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//      ConnectivityManager connectivityManager =
//          (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//      NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//      if(networkInfo!=null&&networkInfo.isAvailable()){
//        Toast.makeText(context,"Network is Available",Toast.LENGTH_SHORT).show();
//      }else {
//        Toast.makeText(context,"Network is unAvailable",Toast.LENGTH_SHORT).show();
//      }
//    }
//  }

}