package com.kc.shiptransport.util;

import android.util.Log;

import com.elvishew.xlog.XLog;

/**
 * @author 邱永恒
 * @time 2017/8/29  9:06
 * @desc 日志打印
 */

public class LogUtil {
    public static void d(Object o) {
        XLog.d(o);
    }

    public static void e(Object o) {
        XLog.e(o);
    }

    public static void json(String json) {
        XLog.json(json);
    }

    public static void t(String msg) {
        Log.e("ship_tag", msg);
    }
}
