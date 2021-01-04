package com.example.broadcastbestpractice;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
  private EditText mAccount;
  private EditText mPassword;
  private Button mButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    mAccount=(EditText)findViewById(R.id.account);
    mPassword=(EditText)findViewById(R.id.password);
    mButton=(Button)findViewById(R.id.login);
    mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String account = mAccount.getText().toString();
        String password = mPassword.getText().toString();
        if("zhulongkai".equals(account)&&"123456".equals(password)){
          Intent intent = new Intent(LoginActivity.this,MainActivity.class);
          startActivity(intent);
          finish();
        }else {
          Toast.makeText(LoginActivity.this,"Password is error",Toast.LENGTH_SHORT).show();
        }
      }
    });

  }
}