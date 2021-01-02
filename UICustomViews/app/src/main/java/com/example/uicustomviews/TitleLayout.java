package com.example.uicustomviews;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class TitleLayout extends LinearLayout {
  public TitleLayout(Context context,
      @Nullable AttributeSet attrs) {
    super(context, attrs);
    LayoutInflater.from(context).inflate(R.layout.title, this);
    Button backButton = (Button) findViewById(R.id.back);
    Button editTitle = (Button) findViewById(R.id.title_edit);
    backButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ((Activity) getContext()).finish();
      }
    });
    editTitle.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        Toast.makeText(getContext(), "edit title clicked", Toast.LENGTH_SHORT).show();
      }
    });


  }


}
