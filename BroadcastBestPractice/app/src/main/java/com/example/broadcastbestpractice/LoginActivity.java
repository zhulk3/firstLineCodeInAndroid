package com.example.broadcastbestpractice;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
  private EditText mAccount;
  private EditText mPassword;
  private Button mButton;
  private SharedPreferences.Editor mEditor;
  private SharedPreferences mSharedPreferences;
  private Boolean isRememberPassword;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    mAccount = (EditText) findViewById(R.id.account);
    mPassword = (EditText) findViewById(R.id.password);
    mButton = (Button) findViewById(R.id.login);
    final CheckBox checkBox = (CheckBox) findViewById(R.id.remember_password);
    mEditor = getSharedPreferences("AccountAndPassword", MODE_PRIVATE).edit();
    mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String account = mAccount.getText().toString();
        String password = mPassword.getText().toString();
        if ("zhulongkai".equals(account) && "123456".equals(password)) {
          if (checkBox.isChecked()) {
            mEditor.putString("account", account);
            mEditor.putString("password", password);
            mEditor.putBoolean("isRememberPassword", true);
          } else {
            mEditor.clear();
          }
          mEditor.apply();
          Intent intent = new Intent(LoginActivity.this, MainActivity.class);
          startActivity(intent);
          finish();
        } else {
          Toast.makeText(LoginActivity.this, "Password is error", Toast.LENGTH_SHORT).show();
        }
      }
    });

    mSharedPreferences = getSharedPreferences("AccountAndPassword", MODE_PRIVATE);
    isRememberPassword = mSharedPreferences.getBoolean("isRememberPassword", false);
    if (isRememberPassword) {
      String account = mSharedPreferences.getString("account", "");
      String password = mSharedPreferences.getString("password", "");
      mAccount.setText(account);
      mPassword.setText(password);
      checkBox.setChecked(true);
    }

  }
}