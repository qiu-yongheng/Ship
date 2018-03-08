package com.kc.shiptransport.util;

/**
 * @author 邱永恒
 * @time 2017/6/11 20:59
 * @desc ${TODO}
 */
public class BaseUrl {
    /**
     * 正式服
     */
//    public static final String Url = "http://www.cchk3.com/AppService/";

    /**
     * 测试服
     */
    public static final String Url = "https://cchk3.kingwi.org/AppService/";

    /**
     * 供砂
     */
    public static final String EndPoint = Url +  "cchk3WebService.asmx";

    /**
     * 今日计划API
     */
    public static final String getToday = Url + "Analysis.asmx";

    /**
     * HSE
     */
    public static final String HSE = Url + "SafeManage.asmx";
}
