package com.proximity.myapplication.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MainApplication extends Application {
    private static final String TAG = "MainApplication";
    public static final String BUNDLE_NOTIF_ADDITIONAL_DATA = "additionalData";
    public static final String BUNDLE_NOTIF_BODY = "body";

    Activity currentActivity;


    public static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public void clearReferences() {
        Activity currActivity = getCurrentActivity();
        if (this.equals(currActivity))
            setCurrentActivity(null);
    }


}


