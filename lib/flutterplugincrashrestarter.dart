import 'dart:async';

import 'package:flutter/services.dart';

class Flutterplugincrashrestarter {
  static const MethodChannel _channel =
      const MethodChannel('flutterplugincrashrestarter');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static void crash() async {
    await _channel.invokeMethod('crash');
  }

  static Future<dynamic> getStackTrace() async {
    return await _channel.invokeMethod('getStackTrace');
  }
}
