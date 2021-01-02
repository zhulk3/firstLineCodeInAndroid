package com.example.uiwidgettest;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private static final String TAG = "MainActivity";
  private EditText mEditText;
  private ImageView mImageView;
  private ProgressBar mProgressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button button = (Button) findViewById(R.id.button);
    mEditText = (EditText) findViewById(R.id.edit_view);
    button.setOnClickListener(this);
    Button replaceImage = (Button) findViewById(R.id.replace_image);
    replaceImage.setOnClickListener(this);
    mImageView = (ImageView) findViewById(R.id.imag1);
    Button changeStyle = (Button) findViewById(R.id.change_progressBar_style);
    changeStyle.setOnClickListener(this);
    mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    Button showDialog = (Button)findViewById(R.id.showDialog);
    showDialog.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.button:
        String inputString;
        if (mEditText != null) {
          inputString = mEditText.getText().toString();
          if (inputString != null) {
            Toast.makeText(MainActivity.this, inputString, Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(MainActivity.this, "button clicked", Toast.LENGTH_SHORT).show();
          }
        }
        break;
      case R.id.replace_image:
        mImageView.setImageResource(R.drawable.ima4);
        break;
      case R.id.change_progressBar_style:
        if (mProgressBar.getVisibility() == View.GONE) {
          mProgressBar.setVisibility(View.VISIBLE);
        } else {
          mProgressBar.setVisibility(View.GONE);
        }
        break;
        case R.id.showDialog:
          AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
          dialog.setTitle("DialogTest");
          dialog.setMessage("hello,zhulonkai");
          dialog.setCancelable(true);
          dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              finish();
            }
          });
          dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              Toast.makeText(MainActivity.this,"You canceled.",Toast.LENGTH_SHORT).show();
            }
          });
          dialog.show();
          break;
      default:
        break;
    }
  }
}