package com.kc.shiptransport.util;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 邱永恒
 * @time 2017/8/18  10:37
 * @desc 正则表达式
 */

public class PatternUtil {
    // 手机号码
    private static final String PATTERN_PHONE = "(13\\d|14[57]|15[^4,\\D]|17[13678]|18\\d)\\d{8}|170[0589]\\d{7}";
    // 邮箱
    private static final String PATTERN_EMAIL = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
    // 电话号码
    private static final String PATTERN_TELL = "(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}";
    // 数字
    private static final String PATTERN_NUMBER = "[0-9]*";
    // 匹配一个字符
    private static final String PATTERN_CHAR_ONE = "[a-z]{1}";



    /**
     * 正则
     *
     * @param pattern
     * @param data
     * @return
     */
    private static boolean pattern(String pattern, String data) {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(data);
        boolean matches = m.matches();

        Log.d("==", "正则结果: " + matches);
        return matches;
    }

    /**
     * 获取指定字符串出现的次数
     *
     * @param srcText 源字符串
     * @param findText 要查找的字符串
     * @return
     */
    public static int appearNumber(String srcText, String findText) {
        int count = 0;
        Pattern p = Pattern.compile(findText);
        Matcher m = p.matcher(srcText);
        while (m.find()) {
            count++;
        }
        return count;
    }

    /**
     * 判断是否是手机号
     *
     * @param phone
     * @return
     */
    public static boolean patternPhone(String phone) {
        return pattern(PATTERN_PHONE, phone);
    }

    /**
     * 判断是否是电话号码
     *
     * @param tell
     * @return
     */
    public static boolean patternTell(String tell) {
            return pattern(PATTERN_TELL, tell);
    }

    /**
     * 判断是否是邮箱
     * @param email
     * @return
     */
    public static boolean patternEmail(String email) {
        return pattern(PATTERN_EMAIL, email);
    }

    /**
     * 判断是否是数字
     * @param number
     * @return
     */
    public static boolean patternNumber(String number) {
        return pattern(PATTERN_NUMBER, number);
    }

    /**
     * 判断是否在字符范围内
     * @param data
     * @return
     */
    public static boolean patternCharOne(String data) {
        return pattern(PATTERN_CHAR_ONE, data);
    }
}
