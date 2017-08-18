package com.kc.shiptransport.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 邱永恒
 * @time 2017/8/18  10:37
 * @desc 正则表达式
 */

public class PatternUtil {
    public static final String PATTERN_PHONE = "(13\\d|14[57]|15[^4,\\D]|17[13678]|18\\d)\\d{8}|170[0589]\\d{7}";
    public static final String PATTERN_EMAIL = "#^[a-z_0-9.-]{1,64}@([a-z0-9-]{1,200}.){1,5}[a-z]{1,6}$#i";
    public static final String PATTERN_TELL = "^[0][1-9]{2,3}-[0-9]{5,10}$";
    public static final String PATTERN_TELL_NO_NUM = "^[1-9]{1}[0-9]{5,8}$";

    /**
     * 正则
     *
     * @param pattern
     * @param data
     * @return
     */
    public static boolean pattern(String pattern, String data) {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(data);
        return m.matches();
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
        if (tell.length() > 9) {
            // 有区号
            return pattern(PATTERN_TELL, tell);
        }
        return pattern(PATTERN_TELL_NO_NUM, tell);
    }

    public static boolean patternEmail(String email) {
        return pattern(PATTERN_EMAIL, email);
    }
}
