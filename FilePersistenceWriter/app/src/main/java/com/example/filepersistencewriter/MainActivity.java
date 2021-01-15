package com.example.filepersistencewriter;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

  private EditText mEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mEditText = (EditText) findViewById(R.id.edit);
    String text = load();
    if(!TextUtils.isEmpty(text)){
      mEditText.setText(text);
      mEditText.setSelection(text.length());
    }

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    String text = mEditText.getText().toString();
    sava(text);
  }

  private void sava(String string) {
    FileOutputStream out = null;
    BufferedWriter writer = null;
    try {
      out = openFileOutput("input", MODE_APPEND);
      writer = new BufferedWriter(new OutputStreamWriter(out));
      writer.write(string);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private String load() {
    FileInputStream in = null;
    BufferedReader reader = null;
    StringBuilder stringBuilder = new StringBuilder();
    try {
      in = openFileInput("input");
      reader = new BufferedReader(new InputStreamReader(in));
      String line = "";
      while ((line=reader.readLine())!=null){
        stringBuilder.append(line);
      }
    }catch (IOException e){
      e.printStackTrace();
    }finally {
      try {
        if(reader!=null){
          reader.close();
        }
      }catch (IOException e){
        e.printStackTrace();
      }
    }
    return stringBuilder.toString();
  }
}