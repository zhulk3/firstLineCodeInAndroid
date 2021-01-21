package com.example.servicebestpractice;

public interface DownloadListener {
  void onProgress(int progress);

  void onSuccess();

  void onPaused();

  void onFailed();

  void onCanceled();
}
