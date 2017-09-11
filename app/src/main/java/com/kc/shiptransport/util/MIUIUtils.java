package com.kc.shiptransport.util;

import android.os.Build;

import java.io.IOException;

/**
 * @author 邱永恒
 * @time 2017/9/11  9:42
 * @desc 判断是否是小米手机
 */

public class MIUIUtils {
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    public static String getMobileVersion() {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            BuildProperties prop = BuildProperties.newInstance();
            stringBuffer.append(Build.MANUFACTURER + "  " + Build.MODEL + "  " + prop.getProperty(KEY_MIUI_VERSION_NAME, null));
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}

