# Flutter Crash Restarter Plugin

A flutter plugin that restarts your Android app after a crash and provides you with the stack trace

## Getting Started

Add this code inside the MainActivity class:

```
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new com.lucaszanella.flutterplugincrashrestarter.FlutterExceptionHandler<MainActivity>(MainActivity.class, this);
    }
```

You can see how to get the stack trace on the example app in main.dart, or simply do:

```
    import 'package:flutterplugincrashrestarter/flutterplugincrashrestarter.dart';
    //...
    void getStackTrace() async {
        dynamic x = await Flutterplugincrashrestarter.getStackTrace();
        if (x==false) {
          //Did not recover from a crash, normal startup
        } else if(x is Map) {
          print("stack trace received");
          if (x["didCrash"]) {
            print("cause" + x["cause"]);
            print("message: " + x["message"]);
            print("stack trace: " + x["stackTrace"]);
          }
        }
    }
```

You can also simulate a crash:

```
    Flutterplugincrashrestarter.crash();
```