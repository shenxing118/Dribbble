package com.shen.dribbble;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by shen on 2016/7/26.
 */
public class DribbbleApp extends Application {

    private static DribbbleApp dribbbleApp;

    public static DribbbleApp getInstance() {
        return dribbbleApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dribbbleApp = this;
        Fresco.initialize(this);
    }
}
