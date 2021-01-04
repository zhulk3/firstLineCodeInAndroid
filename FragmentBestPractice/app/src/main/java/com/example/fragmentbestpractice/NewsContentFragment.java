package com.example.fragmentbestpractice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsContentFragment extends Fragment {
  private View view;
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.news_content_flag,container,false);
    return view;
  }

  public void refresh(String title, String content){
    View visibilityView = view.findViewById(R.id.visibility_layout);
    visibilityView.setVisibility(View.VISIBLE);
    TextView titleView = (TextView)visibilityView.findViewById(R.id.news_title);
    TextView contentView = (TextView)visibilityView.findViewById(R.id.news_content);
    titleView.setText(title);
    contentView.setText(content);
  }
}
