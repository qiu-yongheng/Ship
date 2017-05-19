package com.kc.shiptransport.app;

import android.app.Application;

import org.litepal.LitePalApplication;

/**
 * @author qiuyongheng
 * @time 2017/5/16  15:30
 * @desc ${TODD}
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        LitePalApplication.initialize(this);
    }
}
