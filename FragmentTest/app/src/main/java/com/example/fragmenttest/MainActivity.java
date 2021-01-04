package com.example.fragmenttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Button button = (Button) findViewById(R.id.button);
    button.setOnClickListener(this);
//    replaceFragment(new RightFragment());
//    RightFragment rightFragment = (RightFragment)getSupportFragmentManager().findFragmentById(R.id.left_fragment);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.button:
//        replaceFragment(new AnotherRightFragment());
        break;
      default:
        break;
    }

  }

//  private void replaceFragment(Fragment fragment) {
//    FragmentManager fragmentManager = getSupportFragmentManager();
//    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//    fragmentTransaction.replace(R.id.right_layout, fragment);
//    fragmentTransaction.addToBackStack(null);
//    fragmentTransaction.commit();
//  }
}