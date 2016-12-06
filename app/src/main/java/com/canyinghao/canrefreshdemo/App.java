package com.canyinghao.canrefreshdemo;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;


public class App extends Application implements Thread.UncaughtExceptionHandler{
    private static App sInstance;


    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;


        Thread.setDefaultUncaughtExceptionHandler(this);


    }

    public static App getInstance() {

        if (sInstance == null) {
            sInstance = new App();
        }
        return sInstance;
    }


    public void show(String msg){


        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void uncaughtException(Thread t, Throwable ex) {


        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        pw.flush();

        String stackTrace = sw.toString();


        Log.e("Canyinghao",stackTrace);
    }
}