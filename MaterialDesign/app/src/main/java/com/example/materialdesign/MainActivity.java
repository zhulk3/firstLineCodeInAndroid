package com.example.materialdesign;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.Manifest;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
  private DrawerLayout mDrawerLayout;
  private List<Fruit> mFruitList = new ArrayList<>();
  private FruitAdapter mFruitAdapter;
  private SwipeRefreshLayout mSwipeRefreshLayout;
  private Fruit[] mFruits = {new Fruit("Apple", R.drawable.apple),
      new Fruit("Banana", R.drawable.banana), new Fruit("Cherry", R.drawable.cherry),
      new Fruit("Grape", R.drawable.mango), new Fruit("Orange", R.drawable.orange),
      new Fruit("Pear", R.drawable.pear), new Fruit("PineApple", R.drawable.pineapple),
      new Fruit("Strawberry", R.drawable.strawberry),
      new Fruit("Watermelon", R.drawable.watermelon)};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolBar);
    setSupportActionBar(toolbar);
    mDrawerLayout = findViewById(R.id.drawable_layout);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }
    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setCheckedItem(R.id.nav_call);
    navigationView.setNavigationItemSelectedListener(
        new NavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            mDrawerLayout.closeDrawers();
            return true;
          }
        });
    FloatingActionButton floatingActionButton = findViewById(R.id.fab);
    floatingActionButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Snackbar.make(v, "data deleted", Snackbar.LENGTH_SHORT).setAction("Undo",
            new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                Toast.makeText(MainActivity.this, "You canceled", Toast.LENGTH_SHORT).show();
              }
            }).show();
      }
    });

    initFruit();
    RecyclerView recyclerView = findViewById(R.id.recycler_view);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
    recyclerView.setLayoutManager(gridLayoutManager);
    mFruitAdapter = new FruitAdapter(mFruitList);
    recyclerView.setAdapter(mFruitAdapter);
    mSwipeRefreshLayout = findViewById(R.id.refresh_layout);
    mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        refreshFruits();
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.toolbar, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.back_up:
        Toast.makeText(this, "You clicked BackUp option", Toast.LENGTH_SHORT).show();
        break;
      case R.id.delete:
        Toast.makeText(this, "You clicked Delete option", Toast.LENGTH_SHORT).show();
        break;
      case R.id.settings:
        Toast.makeText(this, "You clicked Settings option", Toast.LENGTH_SHORT).show();
        break;
      case android.R.id.home:
        mDrawerLayout.openDrawer(GravityCompat.START);
        break;
      default:
    }
    return true;
  }

  private void initFruit(){
    mFruitList.clear();
    for (int i =0; i<50;i++){
      Random random = new Random();
      int index = random.nextInt(mFruits.length);
      mFruitList.add(mFruits[index]);
    }
  }

  private void refreshFruits(){
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(2000);
        }catch (Exception e){
          e.printStackTrace();
        }
        initFruit();
        mFruitAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
      }
    });
  }
}