package com.example.broadcastbestpractice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

public class ActivityCollector {
  public static List<Activity>mActivityList= new ArrayList<>();

  public static void addActivity(Activity activity){
    mActivityList.add(activity);
  }

  public static void removeActivity(Activity activity){
    mActivityList.remove(activity);
  }

  public static void finishAll(){
    for(Activity activity:mActivityList){
      if(!activity.isFinishing()){
        activity.finish();
      }
    }
  }
}
