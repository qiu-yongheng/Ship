package com.kc.shiptransport.util;

import com.elvishew.xlog.XLog;

/**
 * @author 邱永恒
 * @time 2017/8/29  9:06
 * @desc 日志打印
 */

public class LogUtil {
    public static void d (Object o) {
        XLog.d(o);
    }

    public static void e (Object o) {
        XLog.e(o);
    }
}
