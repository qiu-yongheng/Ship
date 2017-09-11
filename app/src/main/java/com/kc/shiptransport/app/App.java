package com.kc.shiptransport.app;

import android.app.Application;
import android.content.Context;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.umeng.analytics.MobclickAgent;

import org.litepal.LitePalApplication;

/**
 * @author qiuyongheng
 * @time 2017/5/16  15:30
 * @desc ${TODD}
 */

public class App extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        LitePalApplication.initialize(this);

        // 集成测试
        MobclickAgent.setDebugMode( true );

        // 错误统计 (默认开启)
        MobclickAgent.setCatchUncaughtExceptions(true);

        // 初始化XLog
        LogConfiguration config = new LogConfiguration.Builder()
                .tag("SHIP_TAG") // TAG
                .t() // 运行打印线程信息
                .b() // 允许答应日志边框
                .st(3) // 允许打印深度为3的调用栈信息
                .build();
        XLog.init(LogLevel.ALL, config);
    }

    public static Context getAppContext () {
        return context;
    }
}
