import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutterplugincrashrestarter/flutterplugincrashrestarter.dart';
import 'package:webview_flutter/webview_flutter.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  int _secondsToCrash = 30;
  @override
  void initState() {
    super.initState();
    //initPlatformState();
    doSomething();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await Flutterplugincrashrestarter.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  void decrease() async {
    if (_secondsToCrash==0) {
      Flutterplugincrashrestarter.crash();
    }
    setState(() {
      _secondsToCrash -= 1;
    });
  }

  void doSomething() async {
    decrease();
    var timer = Timer(Duration(seconds: 1), () => doSomething());
  }



  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Crash Restarter Example'),
        ),
        body: Center(
          child: Text('Seconds to crash: $_secondsToCrash\n'),
        ),
      ),
    );
  }
}

class MyApp2 extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: WebView(
        initialUrl : 'https://www.google.com/',
      ),
    );
  }
}
