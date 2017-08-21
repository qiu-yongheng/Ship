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
    private static final String PATTERN_PHONE = "(13\\d|14[57]|15[^4,\\D]|17[13678]|18\\d)\\d{8}|170[0589]\\d{7}";
    private static final String PATTERN_EMAIL = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
    private static final String PATTERN_TELL = "(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{7,14}";

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

    public static boolean patternEmail(String email) {
        return pattern(PATTERN_EMAIL, email);
    }
}
