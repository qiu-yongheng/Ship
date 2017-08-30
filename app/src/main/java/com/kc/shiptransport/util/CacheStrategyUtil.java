package com.kc.shiptransport.util;

import android.text.TextUtils;

/**
 * @author 邱永恒
 * @time 2017/8/29  10:31
 * @desc ${TODD}
 */

public class CacheStrategyUtil {
    public static String getData(String remote, String cache) {
        if (TextUtils.isEmpty(remote) && !TextUtils.isEmpty(cache)) {
            return cache;
        } else if (TextUtils.isEmpty(remote) && TextUtils.isEmpty(cache)) {
            return "";
        } else if (!TextUtils.isEmpty(remote) && TextUtils.isEmpty(cache)) {
            return remote;
        } else if (!TextUtils.isEmpty(remote) && !TextUtils.isEmpty(cache)) {
            return cache;
        }
        return "";
    }
}
