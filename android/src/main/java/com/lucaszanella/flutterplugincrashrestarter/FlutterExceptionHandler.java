package com.lucaszanella.flutterplugincrashrestarter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class FlutterExceptionHandler<A> implements Thread.UncaughtExceptionHandler {
    private static String TAG = "FlutterExceptionHandler";
    private Class<A> type;
    private Activity activity;
    public FlutterExceptionHandler(Class<A> type, Activity a) {
        this.activity = a;
        this.type = type;
        Thread.setDefaultUncaughtExceptionHandler(this);
        Intent intent = activity.getIntent();
        if (intent.getBooleanExtra("crash", false)) {
            String message = intent.getStringExtra("message");
            String cause = intent.getStringExtra("cause");
            String stackTrace = intent.getStringExtra("stackTrace");
            /*
            Log.d(TAG, "App restarted after crash");
            Log.d(TAG, "message: " + message);
            Log.d(TAG, "cause: " + cause);
            Log.d(TAG, "stackTrace: " + stackTrace);
            */
            Toast.makeText(activity, "message: " + message, Toast.LENGTH_SHORT).show();
            Toast.makeText(activity, "cause: " + cause, Toast.LENGTH_SHORT).show();
            Toast.makeText(activity, "stackTrace: " + stackTrace, Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        String stackStraceString = "";
        StackTraceElement[] s = ex.getStackTrace();
        for (int i=0; i<s.length; i++) {
            stackStraceString += s[i] + "\n";
        }
        Intent intent = new Intent(activity, type);
        intent.putExtra("crash", true);
        intent.putExtra("message", ex.getMessage());
        intent.putExtra("cause", ex.toString());
        intent.putExtra("stackTrace", stackStraceString);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity.getBaseContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager mgr = (AlarmManager) activity.getBaseContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
        activity.finish();
        System.exit(2);
    }

    //Simulates a crash
    public static void crashMe() {
        Log.d(TAG, "gonna crash");
        throw new NullPointerException();
    }
}