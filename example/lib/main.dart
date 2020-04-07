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
  int _secondsToCrash = 30;
  String _cause = "";
  String _stackTrace = "";
  String _message = "";
  bool _didCrash = false;
  @override
  void initState() {
    super.initState();
    getStackTrace();
    countdown();
  }

  void decrease() async {
    if (_secondsToCrash==0) {
      Flutterplugincrashrestarter.crash();
    }
    setState(() {
      _secondsToCrash -= 1;
    });
  }

  void countdown() async {
    decrease();
    var timer = Timer(Duration(seconds: 1), () => countdown());
  }

  void getStackTrace() async {
    dynamic x = await Flutterplugincrashrestarter.getStackTrace();
    if (x==false) {
      //Did not recover from a crash
    } else if(x is Map) {
      print("stack trace received");
      setState(() {
        _cause = x["cause"];
        _message = x["message"];
        _stackTrace = x["stackTrace"];
        _didCrash = x["didCrash"];
      });
    }
  }


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Crash Restarter Example'),
        ),
        body: Container(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
            Text('Seconds to crash: $_secondsToCrash\n'),
            Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: (_didCrash) ?
                <Widget>[Text('Cause: $_cause\n'),
                         Text('Message: $_message\n'),
                         Text('StackTrace: $_stackTrace\n')] :
                <Widget>[])
          ],
          )
        ),
      ),
    );
  }
}
