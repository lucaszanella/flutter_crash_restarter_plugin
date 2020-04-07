package com.lucaszanella.flutterplugincrashrestarter_example;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    private static String TAG = "MainActivity";
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thread = new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(60);
                    //crashMe();
                } catch (Exception exception) {

                }
            }
        });
        thread.start();
    }

    public void crashMe() {
        Log.d(TAG, "gonna crash");
        runOnUiThread(new Runnable() {
            public void run() {
                throw new NullPointerException();
            }
        });

    }
  @Override
  public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
      new com.lucaszanella.flutterplugincrashrestarter.FlutterExceptionHandler(MainActivity.class, this);
      GeneratedPluginRegistrant.registerWith(flutterEngine);
  }
}
