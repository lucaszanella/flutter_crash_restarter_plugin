package com.lucaszanella.flutterplugincrashrestarter_example;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
  @Override
  public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
      new com.lucaszanella.flutterplugincrashrestarter.FlutterExceptionHandler(MainActivity.class, this);
    GeneratedPluginRegistrant.registerWith(flutterEngine);
  }
}
