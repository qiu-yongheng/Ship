package com.kc.shiptransport.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author 邱永恒
 * @time 2016/8/11  18:47
 * @desc ${TODD}
 */
public class SharePreferenceUtil {
    public static final String name = "config";

    public static void saveString (Context context, String title, String content) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        //创建编辑器
        SharedPreferences.Editor edit = sp.edit();
        //添加数据
        edit.putString(title, content);


        //不是马上提交
        edit.apply();
    }

    public static String getString (Context context, String title, String def) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getString(title, def);
    }




    public static void saveInt (Context context, String title, int content) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        //创建编辑器
        SharedPreferences.Editor edit = sp.edit();
        //添加数据
        edit.putInt(title, content);

        //不是马上提交
        edit.apply();
    }

    public static int getInt (Context context, String title) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getInt(title, 0);
    }




    public static void saveLong (Context context, String title, long content) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        //创建编辑器
        SharedPreferences.Editor edit = sp.edit();
        //添加数据
        edit.putLong(title, content);

        //不是马上提交
        edit.apply();
    }

    public static long getLong (Context context, String title) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getLong(title, 0L);
    }

    /**
     * SP中写入boolean类型value
     *
     * @param key   键
     * @param value 值
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        //创建编辑器
        SharedPreferences.Editor edit = sp.edit();
        //添加数据
        edit.putBoolean(key, value);

        //不是马上提交
        edit.apply();
    }

    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code false}
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }
}
