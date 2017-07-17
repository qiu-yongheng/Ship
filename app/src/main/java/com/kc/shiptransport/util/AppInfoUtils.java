package com.kc.shiptransport.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * @author 邱永恒
 * @time 2016/7/23  13:51
 * @desc 获取APP版本信息
 */
public class AppInfoUtils {
    public static String getVersionName(Context context) {
        //获取管理器
        PackageManager pm = context.getPackageManager();
        //获取版本信息
        String versionName = null;
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            //获取版本名称
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
    public static int getVersionCode(Context context) {
        //获取管理器
        PackageManager pm = context.getPackageManager();
        //获取版本信息
        int versionCode = 0;
        try {
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            //获取版本号
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 安装APP
     * @param context
     * @param apkPath
     */
    public static void installAuto(Context context, String apkPath) {
        Intent localIntent = new Intent(Intent.ACTION_VIEW);
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        /**
         * Android7.0+禁止应用对外暴露file://uri，改为content://uri；具体参考FileProvider
         */
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context, "com.kc.fileprovider", new File(apkPath));
            localIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(new File(apkPath));
        }
        localIntent.setDataAndType(uri, "application/vnd.android.package-archive"); //打开apk文件
        context.startActivity(localIntent);
    }
}
