package com.example.activitytest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {
  private static final String TAG = "MainActivity";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Activitycollector.sActivities.add(this);
    Log.d(TAG, "Task id is "+getTaskId());
    setContentView(R.layout.first_layout);
    Button button = (Button)findViewById(R.id.button_1);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//        Intent intent = new Intent("com.example.activitytest.ACTION_START");
//        intent.addCategory("com.example.activitytest.MY_CATEGORY");
//        startActivity(intent);
//        Intent intent= new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("http://www.baidu.com"));
//        startActivity(intent);
//        String date = "hello, ThirdActivity";
//        Intent intent = new Intent(MainActivity.this,Third_Activity.class);
//        intent.putExtra("extra_data",date);
//        startActivity(intent);
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
        startActivity(intent);
      }
    });
    Button makeCall = findViewById(R.id.Dial);
    makeCall.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main,menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()){
      case R.id.add_item:
        Toast.makeText(this,"you clicked add",Toast.LENGTH_SHORT).show();
        break;
      case R.id.remove_item:
        Toast.makeText(this,"you clicked remove",Toast.LENGTH_SHORT).show();
        break;
      default:
        break;
    }
    return true;
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode,resultCode,data);
    TextView answer = (TextView)findViewById(R.id.answer);
    switch (requestCode){
      case 1:
        if(resultCode == RESULT_OK){
          String return_data = data.getStringExtra("data_return");
          Log.d(TAG, "onActivityResult: "+return_data);
          answer.setText(return_data);
        }
        break;
      default:
        break;
    }
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    Log.d(TAG, "onRestart");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Activitycollector.sActivities.remove(this);
  }
}