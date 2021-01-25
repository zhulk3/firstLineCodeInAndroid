package com.example.materialdesign;

import android.util.Log;

/*
通过改变level的值来控制日志打印，当level=1时，所有打印日志的方法都会生效，当level=5时，只有方法Log.e()生效，如果不需要输出日志，将level的值设定为NOTHING即可。
 */
public class LogUtil {
  public static final int VERBOSE = 1;
  public static final int DEBUG = 2;
  public static final int INFO = 3;
  public static final int WARNING = 4;
  public static final int ERROR = 5;
  public static final int NOTHING = 6;
  public static int level = VERBOSE;

  public static void v(String tag, String msg) {
    if (level <= VERBOSE) {
      Log.v(tag, msg);
    }
  }

  public static void d(String tag, String msg) {
    if (level <= DEBUG) {
      Log.d(tag, msg);
    }
  }

  public static void i(String tag, String msg) {
    if (level <= INFO) {
      Log.i(tag, msg);
    }
  }

  public static void w(String tag, String msg) {
    if (level <= WARNING) {
      Log.w(tag, msg);
    }
  }

  public static void e(String tag, String msg) {
    if (level <= ERROR) {
      Log.e(tag, msg);
    }
  }


}
